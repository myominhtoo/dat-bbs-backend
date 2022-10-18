package com.penta.aiwmsbackend.model.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.exception.custom.DuplicateTaskCardNameException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.TaskCard;
import com.penta.aiwmsbackend.model.repo.BoardRepo;
import com.penta.aiwmsbackend.model.repo.TaskCardRepo;

@Service("cardService")
public class TaskCardService {

    private TaskCardRepo taskCardRepo;

    private BoardRepo boardRepo;

    @Autowired
    public TaskCardService(TaskCardRepo taskCardRepo, BoardRepo boardRepo) {
        this.taskCardRepo = taskCardRepo;
        this.boardRepo = boardRepo;
    }

    public boolean createTask(TaskCard task) throws InvalidBoardIdException, DuplicateTaskCardNameException {
        task.setBookMark(false);
        task.setStartedDate(new Date());
        task.setDeleteStatus(false);
        task.setEndedDate(new Date());
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
            taskCardRepo.save(task);
            return true;
        }
    }

    public boolean updateTaskCard(TaskCard task) throws InvalidBoardIdException, DuplicateTaskCardNameException {
        task.setBookMark(false);
        task.setStartedDate(new Date());
        task.setDeleteStatus(false);
        task.setEndedDate(new Date());

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
                // if (oldTaskCard.getTaskName() == task.getTaskName()) {

                // }
            }
            taskCardRepo.save(task);
            return true;
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
}
