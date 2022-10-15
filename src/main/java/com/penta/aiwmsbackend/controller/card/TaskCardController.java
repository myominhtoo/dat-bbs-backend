package com.penta.aiwmsbackend.controller.card;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/create-task")
    public ResponseEntity<HttpResponse> CreateTaskCard(@RequestBody TaskCard task)
            throws InvalidBoardIdException, DuplicateTaskCardNameException {
        boolean createTaskCardStatus = taskCardService.CreateTask(task);

        HttpResponse httpResponse = new HttpResponse(
                new Date(),
                createTaskCardStatus ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                createTaskCardStatus ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
                createTaskCardStatus ? "Successfully Created!" : "Failed to create!",
                createTaskCardStatus ? "Ok" : "Unknown error occured!",
                createTaskCardStatus ? true : false);
        return new ResponseEntity<HttpResponse>(httpResponse, httpResponse.getHttpStatus());
    }

}
