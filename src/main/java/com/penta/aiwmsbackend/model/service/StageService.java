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
    public StageService(StageRepo stageRepo, BoardRepo boardRepo) {
        this.stageRepo = stageRepo;
        this.boardRepo = boardRepo;
    }

    public List<Stage> getStages() {
        return this.stageRepo.findAll();
    }

    public List<Stage> getStageWithBoardId(Integer boardId) {
        return this.stageRepo.findAll().stream()
                .filter(stage -> {
                    return (stage.getBoard() == null || stage.getBoard().getId() == boardId);
                }).collect(Collectors.toList());
    }

    public Stage createCustomStage(Stage stage) throws DuplicateStageNameInBoardException {

        if (this.isDuplicateStage(stage) || (stage.getStageName().toLowerCase().equalsIgnoreCase("to do") ||
                stage.getStageName().equalsIgnoreCase("doing") || stage.getStageName().equalsIgnoreCase("done"))) {
            throw new DuplicateStageNameInBoardException("Error");
        }
        return this.stageRepo.save(stage);
    }

    private boolean isDuplicateStage(Stage stage) {
        boolean isDuplicate = this.stageRepo.findStageByBoardId(stage.getBoard().getId())
                .stream()
                .filter(stg -> {
                    return stage.getStageName().equalsIgnoreCase(stg.getStageName());
                }).collect(Collectors.toList()).size() > 0;

        return isDuplicate;
    }

    public Stage updateCustomStage(Stage stage) throws DuplicateStageNameInBoardException, InvalidBoardIdException {        
        Optional<Board> boardStatus = boardRepo.findById(stage.getBoard().getId());
        if (boardStatus.isEmpty()) {
            throw new InvalidBoardIdException("Invalid Board !!");
        }
        if (this.isDuplicateUpdateStage(stage) || (stage.getStageName().toLowerCase().equalsIgnoreCase("to do") ||
                stage.getStageName().equalsIgnoreCase("doing") || stage.getStageName().equalsIgnoreCase("done"))) {

            throw new DuplicateStageNameInBoardException("Error");
        }

        Optional<Stage> optionalStage = stageRepo.findById(stage.getId());
        Stage updateStage = optionalStage.get();
        updateStage.setStageName(stage.getStageName());

       return this.stageRepo.save( updateStage );
    }

    private boolean isDuplicateUpdateStage(Stage stage) {
        boolean isDuplicateStageId = this.stageRepo.findStageByBoardId(stage.getBoard().getId()).stream()
                .filter(stg -> {
                    return stg.getStageName().equalsIgnoreCase(stage.getStageName()) && stage.getId() != stg.getId();
                }).collect(Collectors.toList()).size() > 0;
        return isDuplicateStageId;
    }

}
