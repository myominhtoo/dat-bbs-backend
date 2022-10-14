package com.penta.aiwmsbackend.controller.stage;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.penta.aiwmsbackend.exception.custom.DuplicateStageNameInBoardException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
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
    public List<Stage> getStages( 
        @RequestParam( name = "boardId" , required = false ) Integer boardId
    ){
       return boardId == null ? this.stageService.getStages() : this.stageService.getStageWithBoardId(boardId);
    }

    @PostMapping( value = "/create-stage" )
    public ResponseEntity<HttpResponse> createStage( @RequestBody Stage stage ) throws DuplicateStageNameInBoardException{
        boolean createStatus = this.stageService.createCustomStage( stage );

        HttpResponse httpResponse = new HttpResponse(
            new Date(),
            createStatus ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
            createStatus ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
            createStatus ? "Successfully Created!" : "Error in creating stage!",
            createStatus ? "OK" : "Somethin went wrong!",
            createStatus
        );

        return new ResponseEntity<>( httpResponse , httpResponse.getHttpStatus() );
    }
    

}
