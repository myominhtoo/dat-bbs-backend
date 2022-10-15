package com.penta.aiwmsbackend.controller.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.penta.aiwmsbackend.model.service.TaskCardService;

@RestController
@RequestMapping( value = "/api" )
public class TaskCardController {
    
    private TaskCardService taskCardService;

    @Autowired
    public TaskCardController( TaskCardService taskCardService ){
        this.taskCardService = taskCardService;
    }


}
