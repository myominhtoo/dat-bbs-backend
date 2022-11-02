package com.penta.aiwmsbackend.model.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.exception.custom.DuplicateTaskCardNameException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.exception.custom.InvalidTaskCardIdException;
import com.penta.aiwmsbackend.model.entity.Activity;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.Stage;
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
        task.setStartedDate(LocalDate.now());
        task.setDeleteStatus(false);
        task.setEndedDate(LocalDate.now());
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
        // TaskCard taskCard
        // =this.taskCardRepo.findTaskCardByBoardIdAndId(task.getBoard().getId() ,
        // task.getId() );
        task.setBookMark(false);
        task.setDeleteStatus(false);
        // task.setStartedDate(taskCard.getStartedDate());
        // task.setEndedDate(taskCard.getEndedDate());

        // TaskCard oldTaskCard =
        // this.taskCardRepo.findTaskCardByBoardIdAndId(task.getBoard().getId(),
        // task.getId());
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

    // public TaskCard checkTaskCard(Activity activity) throws
    // InvalidBoardIdException, DuplicateTaskCardNameException {
    // // activity.getTaskCard().setBookMark(false);
    // // activity.getTaskCard().setDeleteStatus(false);
    // Optional<Board> boardStatus =
    // boardRepo.findById(activity.getTaskCard().getBoard().getId());
    // if (boardStatus.isEmpty()) {
    // throw new InvalidBoardIdException("Invalid Board !!");
    // } else {
    // List<TaskCard> taskCardList = this.taskCardRepo
    // .findTaskCardsByBoardId(activity.getTaskCard().getBoard().getId());
    // for (TaskCard taskCardName : taskCardList) {
    // if
    // (taskCardName.getTaskName().equalsIgnoreCase(activity.getTaskCard().getTaskName())
    // && !taskCardName.getId().equals(activity.getTaskCard().getId())) {
    // throw new DuplicateTaskCardNameException("Duplicate TaskCardName !!");
    // }
    // }

    // List<Activity> activityList =
    // this.activityRepo.findActivityByTaskCardId(activity.getTaskCard().getId());
    // List<Stage> getStage =
    // this.stageRepo.findStageByBoardId(activity.getTaskCard().getBoard().getId());
    // TaskCard prevtaskCard = this.taskCardRepo.findTaskCardByBoardIdAndId(
    // activity.getTaskCard().getBoard().getId(), activity.getTaskCard().getId());
    // if (activityList.stream().anyMatch(res -> res.isStatus() != true)) {
    // Stage prevStage = (Stage) getStage.stream().filter(x -> x.getId() == 2);
    // prevtaskCard.setStage(prevStage);
    // System.out.println("Doing is work");
    // return taskCardRepo.save(prevtaskCard);// update
    // } else {
    // Stage prevStage = (Stage) getStage.stream().filter(x -> x.getId() == 3);
    // prevtaskCard.setStage(prevStage);
    // System.out.println("DOne is work");
    // return taskCardRepo.save(prevtaskCard);
    // }
    // }
    // }

}
