package com.penta.aiwmsbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.penta.aiwmsbackend.exception.custom.DuplicateStageNameInBoardException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.Stage;
import com.penta.aiwmsbackend.model.repo.BoardRepo;
import com.penta.aiwmsbackend.model.repo.StageRepo;
import com.penta.aiwmsbackend.model.service.BoardService;
import com.penta.aiwmsbackend.model.service.StageService;

@SpringBootTest
public class StageServiceTest {
    
    @Mock
    private StageRepo stageRepo;
    @Mock
    private BoardRepo boardRepo;

    @InjectMocks
    private StageService stageService; 
    @InjectMocks
    private BoardService boardService;

    private static Stage stage;
    private static Board board;

    private static List<Stage> stages;

    @BeforeAll
    public static void doBeforeTests(){
        Stage stage1 = new Stage();
        Board board1 = new Board();
        board1.setId(1);
        stage1.setId(1);
        stage1.setStageName("ToDo");
        stage1.setDefaultStatus(false);
        stage1.setBoard(board1);
        
        Stage stage2 = new Stage();
        Board board2 = new Board();
        board2.setId(2);
        stage2.setId(2);
        stage2.setStageName("Doing");
        stage2.setDefaultStatus(false);
        stage2.setBoard(board2);

        stage = stage1;
        // stage = stage2;
        board=board1;

        stages = new ArrayList<>();
        Collections.addAll( stages , stage1 , stage2 );
    }
    
    @Test
    public void createStageTest() throws DuplicateStageNameInBoardException{
        when (this.stageRepo.findStageByBoardId(1)).thenReturn(new ArrayList<Stage>());
        // when (this.stageService.isDuplicateStage(stage)).thenReturn(false);
        when(this.stageRepo.save(stage)).thenReturn(stage);
        this.stageService.createCustomStage(stage);
        verify(this.stageRepo , times (1)).save(stage);
    }


    @Test
    public void getStagesTest(){
        when( this.stageRepo.findAll() ).thenReturn( stages );
        assertEquals( this.stageService.getStages().size() , stages.size() );
        verify( this.stageRepo , times(1) ).findAll();
    }

    @Test
    public void getStageWithBoardIdTest(){
        when( this.stageRepo.findAll() ).thenReturn( stages );
        assertEquals( this.stageService.getStages().size() , stages.size() );
        verify( this.stageRepo , times(1) ).findAll();
    }

    @Test
    public void updateStageTest() throws DuplicateStageNameInBoardException, InvalidBoardIdException{
        Optional<Stage> updateStage = Optional.of( stage );
        when(this.boardRepo.findById(1)).thenReturn(Optional.of(board));
        when(this.stageRepo.findStageByBoardId(1)).thenReturn(new ArrayList<>());
        when(this.stageRepo.findById(1)).thenReturn( updateStage );
        when(this.stageRepo.save(stage)).thenReturn(stage);
        Stage newStage = this.stageService.updateCustomStage(stage);
        verify(this.stageRepo , times(1) ).findById(1);
        verify(this.stageRepo, times(1)).save(newStage);
    }

    @Test
    public void duplicateTest(){
        when (this.stageRepo.findStageByBoardId(1)).thenReturn(stages);
        boolean isDuplicate =this.stageService.isDuplicateStage(stage);
        assertTrue(isDuplicate);
        verify( this.stageRepo , times(1) ).findStageByBoardId(1);

    }

    @Test
    public void updateDuplicateTest(){
        when (this.stageRepo.findStageByBoardId(1)).thenReturn(stages);
        this.stageService.isDuplicateUpdateStage(stage);
        verify( this.stageRepo , times(1) ).findStageByBoardId(1);
    }

}
