package com.penta.aiwmsbackend.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.jayway.jsonpath.Option;
import com.penta.aiwmsbackend.exception.custom.DuplicateTaskCardNameException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.Stage;
import com.penta.aiwmsbackend.model.entity.TaskCard;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.BoardRepo;
import com.penta.aiwmsbackend.model.repo.TaskCardRepo;
import com.penta.aiwmsbackend.model.service.TaskCardService;

@SpringBootTest
public class TaskCardServiceTest {
    
    @Mock
    private TaskCardRepo taskCardRepo;

    @Mock
    private BoardRepo boardRepo;

    @InjectMocks
    private TaskCardService taskCardService;

    private static TaskCard taskCard;

    private static Board board;

    private static List<TaskCard> taskCards;

    @BeforeAll 
    public static void runBeforeAll(){
        board = new Board();
        board.setId(1);
        board.setBoardName("test board");
        board.setCode(123);
        board.setImage(null);
        board.setDeleteStatus(false);
        board.setUser( new User() );


        Stage stage = new Stage();
        stage.setId(1);
        stage.setStageName("stage1");
        stage.setBoard(board);

        taskCard = new TaskCard();
        taskCard.setId(1);
        taskCard.setBoard(board);
        taskCard.setTaskName("test task");
        taskCard.setStage(stage);

        TaskCard taskCard2 = new TaskCard();
        taskCard2.setId(2);
        taskCard2.setBoard(board);
        taskCard2.setTaskName("test task");
        taskCard2.setStage(stage);

        taskCards = new ArrayList<>();

        Collections.addAll( taskCards  , taskCard , taskCard2 );

    }

    @Test
    public void createTaskWithInvalidBoardTest() throws InvalidBoardIdException, DuplicateTaskCardNameException{
        when(this.boardRepo.findById(1)).thenReturn(Optional.of(board));
        when(this.taskCardRepo.findTaskCardsByBoardId( 1 )).thenReturn(new ArrayList<TaskCard>()); 
        
        this.taskCardService.createTask(taskCard);

        verify( this.taskCardRepo , times(1)).save(taskCard);
    }

    @Test
    public void showAllTaskCardsTest() throws InvalidBoardIdException{
        when(this.boardRepo.findById(1)).thenReturn(Optional.of(board));
        when(this.taskCardRepo.findTaskCardsByBoardId(1)).thenReturn(taskCards);
        List<TaskCard> foundTasks =  this.taskCardService.showAllTaskCard(1);
        assertEquals( foundTasks.size() , taskCards.size());
    }

    @Test
    public void updateTaskCardTest() throws InvalidBoardIdException, DuplicateTaskCardNameException{
        when(this.boardRepo.findById(1)).thenReturn(Optional.of(board));
        when(this.taskCardRepo.findTaskCardsByBoardId( 1 )).thenReturn(new ArrayList<TaskCard>());

        when(this.taskCardRepo.save(taskCard)).thenReturn(taskCard);
        TaskCard updatedTask = this.taskCardService.updateTaskCard(taskCard);

        verify( this.taskCardRepo , times(1)).save(taskCard);
        assertEquals( taskCard.getId(), updatedTask.getId());
    }
}
