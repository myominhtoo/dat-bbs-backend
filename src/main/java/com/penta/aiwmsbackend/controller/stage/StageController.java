package com.penta.aiwmsbackend.controller.stage;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping( value = "/api" ,  produces = { MediaType.APPLICATION_JSON_VALUE} )
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

    @DeleteMapping( value = "/delete-stage")
    public  ResponseEntity<HttpResponse<Boolean>> deleteStage (@RequestParam( name = "id") Integer id){   
      boolean status = false;
      try{
        this.stageService.deleteStage(id);
        status = true;
      }catch( Exception e ){
        status = false;
      }
      HttpResponse<Boolean> httpResponse = new HttpResponse<>(
        LocalDate.now(),
        status ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
        status ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
        status ? "Successfully Deleted!" : "Failed to delete!",
        status ? "OK" : "error",
        status,
        status
      );
      return new ResponseEntity<HttpResponse<Boolean>>( httpResponse,  httpResponse.getHttpStatus() );
    }
    
}
