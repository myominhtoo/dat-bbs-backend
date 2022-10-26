package com.penta.aiwmsbackend.controller.stage;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.penta.aiwmsbackend.exception.custom.DuplicateStageNameInBoardException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Stage;
import com.penta.aiwmsbackend.model.service.StageService;

@RestController
@RequestMapping( value = "/api" )
@CrossOrigin( originPatterns = "*" )
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
    public ResponseEntity<HttpResponse<Stage>> createStage( @RequestBody Stage stage ) throws DuplicateStageNameInBoardException{
        
        Stage savedStage = this.stageService.createCustomStage( stage );

        HttpResponse<Stage> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            savedStage != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
            savedStage != null ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
            savedStage != null ? "Successfully Created!" : "Error in creating stage!",
            savedStage != null ? "OK" : "Somethin went wrong!",
            savedStage != null ,
           savedStage
        );

        return new ResponseEntity<>( httpResponse , httpResponse.getHttpStatus() );
    }
    
    @PutMapping( value = "/update-stage" )
    public ResponseEntity<HttpResponse<Stage>> updateStage ( @RequestBody Stage stage  ) throws DuplicateStageNameInBoardException, InvalidBoardIdException{
        Stage updateStageStatus = this.stageService.updateCustomStage(stage);
        HttpResponse<Stage> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            updateStageStatus != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
            updateStageStatus != null ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
            updateStageStatus != null ? "Successfully Updated! " : "Failed to update Stage!",
            updateStageStatus != null ? "OK" : "Something went wrong!!!",
            updateStageStatus != null,
            updateStageStatus
        );
        return new ResponseEntity<HttpResponse<Stage>>( httpResponse , httpResponse.getHttpStatus());
    }
}
