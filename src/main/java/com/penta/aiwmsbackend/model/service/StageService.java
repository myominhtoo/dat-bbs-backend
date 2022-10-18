package com.penta.aiwmsbackend.model.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.exception.custom.DuplicateStageNameInBoardException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.repo.BoardRepo;
import com.penta.aiwmsbackend.model.entity.Stage;
import com.penta.aiwmsbackend.model.repo.StageRepo;

@Service("stageService")
public class StageService {
    
    private StageRepo stageRepo;
    private BoardRepo boardRepo;

    @Autowired
    public StageService( StageRepo stageRepo , BoardRepo boardRepo ){
        this.stageRepo = stageRepo;
        this.boardRepo = boardRepo;
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

    public Stage createCustomStage( Stage stage ) throws DuplicateStageNameInBoardException{
        
        if( this.isDuplicateStage( stage )){
            throw new  DuplicateStageNameInBoardException("Error");
        }
        return this.stageRepo.save( stage );
    }

    private boolean isDuplicateStage( Stage stage ){
        boolean isDuplicate =  this.stageRepo.findStageByBoardId( stage.getBoard().getId())
                               .stream()
                               .filter( stg -> {
                                 return stage.getStageName().equalsIgnoreCase( stg.getStageName()  );
                               }).collect(Collectors.toList()).size() > 0 ;

        return isDuplicate;
    }

    public boolean updateCustomStage ( Stage stage ) throws DuplicateStageNameInBoardException, InvalidBoardIdException{
        boolean updateStageStatus = false;

        Optional<Board> boardStatus = boardRepo.findById(stage.getBoard().getId());
        if (boardStatus.isEmpty()) {
            throw new InvalidBoardIdException("Invalid Board !!");
        } 
        if( this.isDuplicateStageId( stage )){
            
            throw new  DuplicateStageNameInBoardException("Error");
        }

        Optional<Stage> updateStage = stageRepo.findById(stage.getId());
        Stage UpdateStage = updateStage.get();
        UpdateStage.setStageName(stage.getStageName());
        
        if( this.stageRepo.save(stage) != null ) {
            updateStageStatus = true;
        } 
        return updateStageStatus; 
        
    }

    private boolean isDuplicateStageId ( Stage stage ){
        boolean isDuplicateStageId = this.stageRepo.findStageByBoardId( stage.getBoard().getId()).stream()
                                     .filter( stg -> {
                                        return stage.getId() != stg.getId() && stg.getStageName().equalsIgnoreCase(stg.getStageName());
                                     }).collect(Collectors.toList()).size() > 0 ;
        return isDuplicateStageId;
    }

}
