package com.penta.aiwmsbackend.model.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.exception.custom.DuplicateTaskCardNameException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.exception.custom.InvalidTaskCardIdException;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.TaskCard;
import com.penta.aiwmsbackend.model.repo.ActivityRepo;
import com.penta.aiwmsbackend.model.repo.BoardRepo;
import com.penta.aiwmsbackend.model.repo.StageRepo;
import com.penta.aiwmsbackend.model.repo.TaskCardRepo;

@Service("cardService")
public class TaskCardService {

    private TaskCardRepo taskCardRepo;
    private ActivityRepo activityRepo;
    private BoardRepo boardRepo;
    private StageRepo stageRepo;

    @Autowired
    public TaskCardService(TaskCardRepo taskCardRepo, BoardRepo boardRepo, ActivityRepo activityRepo,
            StageRepo stageRepo) {
        this.activityRepo = activityRepo;
        this.stageRepo = stageRepo;
        this.taskCardRepo = taskCardRepo;
        this.boardRepo = boardRepo;
    }

    public TaskCard createTask(TaskCard task) throws InvalidBoardIdException, DuplicateTaskCardNameException {
        task.setBookMark(false);
        task.setDeleteStatus(false);
        
        if( task.getStartedDate() == null || task.getEndedDate() == null ){
            task.setStartedDate(LocalDateTime.now());
            task.setEndedDate(LocalDateTime.now()); 
        }

        Optional<Board> boardStatus = boardRepo.findById(task.getBoard().getId());
        if (boardStatus.isEmpty()) {
            throw new InvalidBoardIdException("Invalid Board !!");
        } else {
            List<TaskCard> taskCardList = this.taskCardRepo.findTaskCardsByBoardId(task.getBoard().getId());
            for (TaskCard taskCardName : taskCardList) {
                if (taskCardName.getTaskName().equalsIgnoreCase(task.getTaskName())) {
                    throw new DuplicateTaskCardNameException("Duplicate TaskCardName !!");
                }
            }
            return taskCardRepo.save(task);
        }
    }

    public TaskCard updateTaskCard(TaskCard task) throws InvalidBoardIdException, DuplicateTaskCardNameException {
        task.setBookMark(false);
        task.setDeleteStatus(false);

        Optional<Board> boardStatus = boardRepo.findById(task.getBoard().getId());
        if (boardStatus.isEmpty()) {
            throw new InvalidBoardIdException("Invalid Board !!");
        } else {
            List<TaskCard> taskCardList = this.taskCardRepo.findTaskCardsByBoardId(task.getBoard().getId());
            for (TaskCard taskCardName : taskCardList) {
                if (taskCardName.getTaskName().equalsIgnoreCase(task.getTaskName())
                        && !taskCardName.getId().equals(task.getId())) {
                    throw new DuplicateTaskCardNameException("Duplicate TaskCardName !!");
                }
            }
            return taskCardRepo.save(task);
        }

    }

    public List<TaskCard> showAllTaskCard(int i) throws InvalidBoardIdException {
        Optional<Board> boardStatus = boardRepo.findById(i);
        if (boardStatus.isEmpty()) {
            throw new InvalidBoardIdException("Invalid Board !!");
        } else {
            List<TaskCard> taskCardList = taskCardRepo.findTaskCardsByBoardId(i);
            return taskCardList;
        }
    }

    public TaskCard assignTasksToMembers(TaskCard taskCard) throws InvalidTaskCardIdException {

        TaskCard savedTaskCard = this.taskCardRepo.findById(taskCard.getId())
                .orElseThrow(() -> new InvalidTaskCardIdException("Invalid Task Id!"));
        savedTaskCard.setUsers(taskCard.getUsers());
        return this.taskCardRepo.save(savedTaskCard);
    }

    public List<TaskCard> showMyTasks(int userId) {
        return this.taskCardRepo.findTasksById(userId);
    }

    // public List<TaskCard> reportTaskCards(){
    // return this.taskCardRepo.findAll();
    // }

    public List<TaskCard> getReportTasks(int id) {
        return this.taskCardRepo.findReportTasks(id);
        // public List<TaskCard> reportTaskCards(int boardId){
        // return this.taskCardRepo.findTaskCardsByBoardId(boardId);
    }

    public List<TaskCard> reportTaskCards(Integer id) {
        return taskCardRepo.rpTaskCards(id);
        }

    public TaskCard updateDeleteStatusTaskCard(Integer id) {
        return taskCardRepo.findTaskCardById(id);
    }

    public TaskCard updateTaskCardForDelete(TaskCard taskCard) {
        return taskCardRepo.save(taskCard);
    }

    public List<TaskCard> showDeleteStatusTaskCard(Integer boardId) {
        return taskCardRepo.findDeleteTasks(boardId);
    }

    public TaskCard restoreTaskCard(int id) {
        return taskCardRepo.findTaskCardByDeleteStatus(id);
    }

    public List<TaskCard> reportArchiveTaskCards( int id ){
        return taskCardRepo.findArchiveTaskCard(id);
    }

    public List<TaskCard> reportAssignedTasks(int id){
        return taskCardRepo.findTasksById(id);
    }
}
