package com.penta.aiwmsbackend.model.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.exception.custom.DuplicateStageNameInBoardException;
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

    public List<Stage> getStageWithBoardId( Integer boardId ){
        return this.stageRepo.findAll().stream()
               .filter( stage -> {
                 return ( stage.getBoard() == null || stage.getBoard().getId() == boardId );
               }).collect(Collectors.toList());
    }

    public boolean createCustomStage( Stage stage ) throws DuplicateStageNameInBoardException{
        boolean createStatus = false;
        
        if( this.isDuplicateStage( stage )){
            throw new  DuplicateStageNameInBoardException("Error");
        }

        if( this.stageRepo.save(stage) != null ) {
            createStatus = true;
        } 

        return createStatus;
    }

    private boolean isDuplicateStage( Stage stage ){
        boolean isDuplicate =  this.stageRepo.findStageByBoardId( stage.getBoard().getId())
                               .stream()
                               .filter( stg -> {
                                 return stage.getStageName().equalsIgnoreCase( stg.getStageName()  );
                               }).collect(Collectors.toList()).size() > 0 ;

        return isDuplicate;
    }

}
