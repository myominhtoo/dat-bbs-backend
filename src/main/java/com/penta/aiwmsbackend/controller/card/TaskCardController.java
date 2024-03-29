package com.penta.aiwmsbackend.controller.card;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.penta.aiwmsbackend.exception.custom.DuplicateTaskCardNameException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.exception.custom.InvalidTaskCardIdException;
import com.penta.aiwmsbackend.jasperReport.AssignedTasksReportService;
import com.penta.aiwmsbackend.jasperReport.TaskCardReportService;
import com.penta.aiwmsbackend.jasperReport.archiveTasksReportService;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.TaskCard;
import com.penta.aiwmsbackend.model.service.TaskCardService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping(value = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin(originPatterns = "*")
public class TaskCardController {

    private TaskCardService taskCardService;
    private TaskCardReportService taskCardReportService;
    private archiveTasksReportService archiveTasksService;
    private AssignedTasksReportService assignedTasksService;


    @Autowired
    public TaskCardController(TaskCardService taskCardService, TaskCardReportService taskCardReportService,
            archiveTasksReportService archiveTasksService,
            AssignedTasksReportService assignedTasksReportService) {
        this.taskCardService = taskCardService;
        this.taskCardReportService = taskCardReportService;
        this.archiveTasksService = archiveTasksService;
        this.assignedTasksService = assignedTasksReportService;
    }

    @PostMapping(value = "/create-task")
    public ResponseEntity<HttpResponse<TaskCard>> CreateTaskCard(@RequestBody TaskCard task)
            throws InvalidBoardIdException, DuplicateTaskCardNameException {
        TaskCard createTaskCardStatus = taskCardService.createTask(task);

        HttpResponse<TaskCard> httpResponse = new HttpResponse<>( 
                LocalDate.now(),
                createTaskCardStatus != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                createTaskCardStatus != null ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
                createTaskCardStatus != null ? "Successfully Created!" : "Failed to create!",
                createTaskCardStatus != null ? "Ok" : "Unknown error occured!",
                createTaskCardStatus != null,
                createTaskCardStatus);
        return new ResponseEntity<HttpResponse<TaskCard>>(httpResponse, httpResponse.getHttpStatus());
    }

    @PutMapping(value = "/update-task")
    public ResponseEntity<HttpResponse<TaskCard>> UpdateTaskCard(@RequestBody TaskCard task)

            throws InvalidBoardIdException, DuplicateTaskCardNameException {
        TaskCard updateTaskCardStatus = taskCardService.updateTaskCard(task);

        HttpResponse<TaskCard> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                updateTaskCardStatus != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                updateTaskCardStatus != null ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
                updateTaskCardStatus != null ? "Successfully Updated!" : "Failed to Update!",
                updateTaskCardStatus != null ? "Ok" : "Unknown error occured!",
                updateTaskCardStatus != null,
                updateTaskCardStatus);
        return new ResponseEntity<HttpResponse<TaskCard>>(httpResponse, httpResponse.getHttpStatus());
    }

    @GetMapping(value = "/boards/{id}/task-cards")
    public ResponseEntity<List<TaskCard>> showBoardDetails(@PathVariable("id") int id) throws InvalidBoardIdException {
        List<TaskCard> showAllTaskCard = taskCardService.showAllTaskCard(id);
        return ResponseEntity.ok().body(showAllTaskCard);
    }

    @PutMapping(value = "/tasks/{taskId}/assign-task")
    public ResponseEntity<HttpResponse<TaskCard>> assignTaskToMembers(@PathVariable("taskId") Integer taskId,
            @RequestBody TaskCard taskCard) throws InvalidTaskCardIdException {
        TaskCard assignTaskStatus = this.taskCardService.assignTasksToMembers(taskCard);
        HttpResponse<TaskCard> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                assignTaskStatus != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                assignTaskStatus != null ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
                assignTaskStatus != null ? "Successfully Assigned!" : "Something went wrong!",
                assignTaskStatus != null ? "OK" : "Error!",
                assignTaskStatus != null,
                assignTaskStatus);
        return new ResponseEntity<HttpResponse<TaskCard>>(httpResponse, httpResponse.getHttpStatus());
    }

    @GetMapping(value = "/users/{userId}/task-cards")
    public ResponseEntity<List<TaskCard>> showMyTask(@PathVariable("userId") int userId) {
        List<TaskCard> myTask = taskCardService.showMyTasks(userId);
        return ResponseEntity.ok().body(myTask);
    }

    @GetMapping(value = "/boards/{boardId}/reportTask")
    public ResponseEntity<InputStreamResource> generateReport(@PathVariable("boardId") Integer boardId,
            @RequestParam("format") String format)
            throws JRException, IOException {
        String path = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
                .replace("target\\test-classes", "")
                + "src\\main\\resources\\static\\Exported-Reports";
        taskCardReportService.exportTaskReport(format, boardId);

        String exportFile = taskCardReportService.exportTaskReport(format, boardId);
        File downloadFile = new File(path + exportFile);// pathname
        InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadFile));
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "filename=" + downloadFile.getName());

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(downloadFile.length())
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .body(resource);

    }

    @PutMapping(value = "/boards/{baordId}/task-cards")
    public TaskCard updateDeleteStatuTaskCardById(
            @RequestParam("id") Integer id) {

        TaskCard taskCard = taskCardService.updateDeleteStatusTaskCard(id);

        TaskCard t1 = new TaskCard();

        t1.setId(taskCard.getId());
        t1.setDescription(taskCard.getDescription());
        t1.setStartedDate(taskCard.getStartedDate());
        t1.setEndedDate(taskCard.getEndedDate());
        t1.setTaskName(taskCard.getTaskName());
        t1.setBoard(taskCard.getBoard());
        t1.setUsers(taskCard.getUsers());
        t1.setMarkColor(taskCard.getMarkColor());
        t1.setStage(taskCard.getStage());

        t1.setDeleteStatus(true);

        return taskCardService.updateTaskCardForDelete(t1);

    }

    @GetMapping(value = "/boards/{boardId}/archive-tasks")
    public List<TaskCard> showDeleteTaskCards(@PathVariable("boardId") Integer boardId) {
        return this.taskCardService.showDeleteStatusTaskCard(boardId);
    }

    @PutMapping(value = "/boards/{boardId}/restore-tasks")
    public TaskCard restoreTaskCard(@PathVariable("boardId") String boardId, @RequestParam("id") Integer id) {

        TaskCard taskCard = taskCardService.restoreTaskCard(id);

        TaskCard t = new TaskCard();

        t.setId(taskCard.getId());
        t.setDescription(taskCard.getDescription());
        t.setStartedDate(taskCard.getStartedDate());
        t.setEndedDate(taskCard.getEndedDate());
        t.setTaskName(taskCard.getTaskName());
        t.setBoard(taskCard.getBoard());
        t.setUsers(taskCard.getUsers());
        t.setMarkColor(taskCard.getMarkColor());
        t.setStage(taskCard.getStage());

        t.setDeleteStatus(false);


        return this.taskCardService.updateTaskCardForDelete(t);

    }

    @GetMapping(value = "/boards/{boardId}/reportArchiveTask")
    public ResponseEntity<InputStreamResource> generateArchiveReport(@PathVariable("boardId") Integer boardId,
            @RequestParam("format") String format)
            throws JRException, IOException {
        String path = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
                .replace("target\\test-classes", "")
                + "src\\main\\resources\\static\\Exported-Reports";
        archiveTasksService.exportTaskReport(format, boardId);
        String exportedFileName = archiveTasksService.exportTaskReport(format, boardId);
        File downloadFile = new File(path + exportedFileName);// pathname
        InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadFile));
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "filename=" + downloadFile.getName());

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(downloadFile.length())
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .body(resource);
    }

    @GetMapping(value = "/users/{id}/reportAssignedTasks")
    public ResponseEntity<InputStreamResource> generateAssignedReport(@PathVariable("id") Integer id,
            @RequestParam("format") String format)
            throws JRException, IOException {
        String path = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
                .replace("target\\test-classes", "")
                + "src\\main\\resources\\static\\Exported-Reports";
        String exportFile = assignedTasksService.exportAssingedTaskReport(format, id);
        File downloadFile = new File(path + exportFile);// pathname
        InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadFile));
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "filename=" + downloadFile.getName());

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(downloadFile.length())
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .body(resource);
    }

    // @DeleteMapping(value = "/boards/{boardId}/delete-taskcard")
    // public ResponseEntity<HttpResponse<Boolean>>
    // deleteTaskCard(@RequestParam("id") Integer id) {

    // boolean deleteStatus = false;

    // this.commentService.deleteTaskCardByComment(id);

    // this.activityService.deleteActivityForTaskCard(id);

    // try {
    // this.taskCardService.deleteTaskCard(id);
    // deleteStatus = true;
    // } catch (Exception e) {
    // e.printStackTrace();
    // deleteStatus = false;
    // }

    // HttpResponse<Boolean> httpResponse = new HttpResponse<>(
    // LocalDate.now(),
    // deleteStatus ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
    // deleteStatus ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
    // deleteStatus ? "Successfully Deleted!" : "Error",
    // deleteStatus ? "Ok" : "error",
    // deleteStatus,
    // deleteStatus);

    // return new ResponseEntity<>(httpResponse, httpResponse.getHttpStatus());
    // }
}
