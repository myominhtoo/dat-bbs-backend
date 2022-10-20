package com.penta.aiwmsbackend.controller.card;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.penta.aiwmsbackend.exception.custom.DuplicateTaskCardNameException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.TaskCard;
import com.penta.aiwmsbackend.model.service.TaskCardService;

@RestController
@RequestMapping(value = "/api")
public class TaskCardController {

    private TaskCardService taskCardService;

    @Autowired
    public TaskCardController(TaskCardService taskCardService) {
        this.taskCardService = taskCardService;
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
                createTaskCardStatus );
        return new ResponseEntity<HttpResponse<TaskCard>>(httpResponse, httpResponse.getHttpStatus());
    }

    @PutMapping(value = "/update-task")
    public ResponseEntity<HttpResponse<TaskCard>> UpdateTaskCard(@RequestBody TaskCard task)
    
            throws InvalidBoardIdException, DuplicateTaskCardNameException {
        TaskCard updateTaskCardStatus = taskCardService.updateTaskCard(task);

        HttpResponse<TaskCard> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                updateTaskCardStatus != null  ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                updateTaskCardStatus != null ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
                updateTaskCardStatus != null ? "Successfully Updated!" : "Failed to Update!",
                updateTaskCardStatus != null ? "Ok" : "Unknown error occured!",
                updateTaskCardStatus != null ,
                updateTaskCardStatus );
        return new ResponseEntity<HttpResponse<TaskCard>>(httpResponse, httpResponse.getHttpStatus());
    }

    @GetMapping(value = "/boards/{id}/task-cards")
    public ResponseEntity<List<TaskCard>> showBoardDetails(@PathVariable("id") int id) throws InvalidBoardIdException {
        List<TaskCard> showAllTaskCard = taskCardService.showAllTaskCard(id);
        return ResponseEntity.ok().body(showAllTaskCard);
    }

}
