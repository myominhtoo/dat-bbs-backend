package com.penta.aiwmsbackend.controller.card;

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
    public ResponseEntity<HttpResponse<Boolean>> CreateTaskCard(@RequestBody TaskCard task)
            throws InvalidBoardIdException, DuplicateTaskCardNameException {
        boolean createTaskCardStatus = taskCardService.createTask(task);

        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
                new Date(),
                createTaskCardStatus ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                createTaskCardStatus ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
                createTaskCardStatus ? "Successfully Created!" : "Failed to create!",
                createTaskCardStatus ? "Ok" : "Unknown error occured!",
                createTaskCardStatus ? true : false,
                true);
        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, httpResponse.getHttpStatus());
    }

    @PutMapping(value = "/update-task")
    public ResponseEntity<HttpResponse<Boolean>> UpdateTaskCard(@RequestBody TaskCard task)
            throws InvalidBoardIdException, DuplicateTaskCardNameException {
        boolean createTaskCardStatus = taskCardService.updateTaskCard(task);

        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
                new Date(),
                createTaskCardStatus ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                createTaskCardStatus ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
                createTaskCardStatus ? "Successfully Updated!" : "Failed to Update!",
                createTaskCardStatus ? "Ok" : "Unknown error occured!",
                createTaskCardStatus ? true : false,
                true);
        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, httpResponse.getHttpStatus());
    }

    @GetMapping(value = "/boards/{id}/task-cards")
    public ResponseEntity<List<TaskCard>> showBoardDetails(@PathVariable("id") int id) throws InvalidBoardIdException {
        List<TaskCard> showAllTaskCard = taskCardService.showAllTaskCard(id);
        return ResponseEntity.ok().body(showAllTaskCard);
    }

}
