package com.penta.aiwmsbackend.controller.card;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.penta.aiwmsbackend.jasperReport.TaskCardReportService;
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

    @Autowired
    public TaskCardController(TaskCardService taskCardService, TaskCardReportService taskCardReportService) {
        this.taskCardService = taskCardService;
        this.taskCardReportService = taskCardReportService;
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
    public void generateReport(@PathVariable("boardId") Integer boardId, @RequestParam("format") String format)
            throws JRException, IOException {

        taskCardReportService.getReportTaskCard(boardId);

        String flag = taskCardReportService.exportTaskReport(format);

        Map<String, String> responsetoangular = new HashMap<>();
        responsetoangular.put("flag", flag);

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

        // return taskCardService.updateTaskCardForDelete(t1);

        return this.taskCardService.updateTaskCardForDelete(t);

    }

}
