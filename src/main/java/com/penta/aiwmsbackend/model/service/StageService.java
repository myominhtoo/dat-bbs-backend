package com.penta.aiwmsbackend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.entity.Stage;
import com.penta.aiwmsbackend.model.repo.StageRepo;

@Service("stageService")
public class StageService {
    
    private StageRepo stageRepo;

    @Autowired
    public StageService( StageRepo stageRepo ){
        this.stageRepo = stageRepo;
    }

    public List<Stage> getStages(){
        return this.stageRepo.findAll();
    }

}
