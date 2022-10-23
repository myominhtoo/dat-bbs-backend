package com.penta.aiwmsbackend.model.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.exception.custom.InvalidTaskCardIdException;
import com.penta.aiwmsbackend.model.entity.Comment;
import com.penta.aiwmsbackend.model.entity.TaskCard;
import com.penta.aiwmsbackend.model.repo.CommentRepo;
import com.penta.aiwmsbackend.model.repo.TaskCardRepo;

@Service("CommentService")
public class CommentService {

    private CommentRepo commentRepo;
    private TaskCardRepo taskCardRepo;
    
    
    @Autowired
    public CommentService(CommentRepo commentRepo ,TaskCardRepo taskCardRepo) {
        this.commentRepo = commentRepo;
        this.taskCardRepo = taskCardRepo;
        
    }

    public Comment createComment ( Comment comment) throws InvalidTaskCardIdException{
        comment.setCreatedDate(LocalDate.now());

        Optional<TaskCard> taskCardStatus = taskCardRepo.findById(comment.getTaskCard().getId());
        if ( taskCardStatus.isEmpty()){
            throw new InvalidTaskCardIdException("Invalid TaskCard Id!!!");
        }
        return commentRepo.save(comment);
    }


    public List<Comment> showComments ( int id ) {
        List<Comment> commentList = commentRepo.findCommentByTaskCardId(id);
        return commentRepo.findCommentByTaskCardId(id);
    }
}
