package com.penta.aiwmsbackend.controller.stage;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.penta.aiwmsbackend.model.entity.Stage;
import com.penta.aiwmsbackend.model.service.StageService;

@RestController
@RequestMapping( value = "/api" )
public class StageController {
    
    private StageService stageService;

    public StageController( StageService stageService ){
        this.stageService = stageService;
    }

    @GetMapping( value = "/stages" )
    public List<Stage> getStages(){
        return this.stageService.getStages();
    }
    

}
