package com.penta.aiwmsbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.jfree.data.gantt.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.jayway.jsonpath.Option;
import com.penta.aiwmsbackend.exception.custom.DuplicateTaskCardNameException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.Stage;
import com.penta.aiwmsbackend.model.entity.TaskCard;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.BoardRepo;
import com.penta.aiwmsbackend.model.repo.TaskCardRepo;
import com.penta.aiwmsbackend.model.repo.UserRepo;
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

    private static List<TaskCard> taskCards = new ArrayList<>();

    private static List<User> userlist = new ArrayList<>();

    private static User user;
    private static User user1;

    @BeforeAll
    public static void runBeforeAll() {

        user = new User();
        user.setId(1);
        user.setUsername("qq");

        user1 = new User();
        user1.setId(2);
        user1.setUsername("dd");

        userlist.add(user);
        userlist.add(user1);

        board = new Board();
        board.setId(1);
        board.setBoardName("test board");
        board.setCode(123);
        board.setImage(null);
        board.setDeleteStatus(false);
        board.setUser(new User());

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

        Collections.addAll(taskCards, taskCard, taskCard2);

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

    @Test
    public void assignTasksToMembersTest()throws Exception{
        when(this.taskCardRepo.findById(1)).thenReturn(Optional.of(taskCard));
        taskCard.setUsers(userlist);
        when(this.taskCardRepo.save(taskCard)).thenReturn(taskCard);
        this.taskCardService.assignTasksToMembers(taskCard);
        verify(this.taskCardRepo,times(1)).save(taskCard);
        assertNotNull(taskCard);
    }

    @Test
    public void showMyTasks() throws InvalidBoardIdException{
        // when(this.taskCardService.showMyTasks(1)).thenReturn(taskCards);
        //     assertNotNull(taskCards);
        when (this.taskCardRepo.findTasksById(1)).thenReturn(taskCards);
        List<TaskCard> tasks =this.taskCardService.showMyTasks(1);
        verify(this.taskCardRepo,times(1)).findTasksById(1);

    }
  
    @Test
    public void getRpTaskCards(){
        when (this.taskCardRepo.findReportTasks(1)).thenReturn(taskCards);
        List<TaskCard> reportTask = this.taskCardService.getReportTasks(1);
        verify(this.taskCardRepo,times(1)).findReportTasks(1);
    }

    @Test
    public void reportTaskCards(){
        when (this.taskCardRepo.rpTaskCards(1)).thenReturn(taskCards);
        List<TaskCard> reportTask = this.taskCardService.reportTaskCards(1);
        verify(this.taskCardRepo,times(1)).rpTaskCards(1);
    }

    @Test
    public void updateDeleteStatusTaskCard(){
        when ( this.taskCardRepo.findTaskCardById(0)).thenReturn(taskCard);
        TaskCard task = this.taskCardService.updateDeleteStatusTaskCard(1);
        verify(this.taskCardRepo,times(1)).findTaskCardById(1);
    }

    @Test
    public void updateTaskCardForDelete(){
        when (this.taskCardRepo.save(taskCard)).thenReturn(taskCard);
        TaskCard task = this.taskCardService.updateTaskCardForDelete(taskCard);
        verify(this.taskCardRepo,times(1)).save(taskCard);
    }

    @Test
    public void showDeleteStatusTaskCard(){
        when ( this.taskCardRepo.findDeleteTasks(1)).thenReturn(taskCards);
        List<TaskCard> taskCards= this.taskCardService.showDeleteStatusTaskCard(1);
        verify(this.taskCardRepo,times(1)).findDeleteTasks(1);
    }
}
