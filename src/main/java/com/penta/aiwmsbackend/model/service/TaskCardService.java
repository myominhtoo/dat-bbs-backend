package com.penta.aiwmsbackend.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.repo.TaskCardRepo;

@Service("cardService")
public class TaskCardService {
    
    private TaskCardRepo taskCardRepo;

    @Autowired
    public TaskCardService( TaskCardRepo taskCardRepo ){
        this.taskCardRepo = taskCardRepo;
    }

}
