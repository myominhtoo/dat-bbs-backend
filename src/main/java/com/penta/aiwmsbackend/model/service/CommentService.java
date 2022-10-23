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
    private UserService userService;
    
    
    @Autowired
    public CommentService(CommentRepo commentRepo ,TaskCardRepo taskCardRepo , UserService userService ) {
        this.commentRepo = commentRepo;
        this.taskCardRepo = taskCardRepo;
        this.userService = userService;
    }

    public Comment createComment ( Comment comment) throws InvalidTaskCardIdException{
        comment.setCreatedDate(LocalDate.now());

        Optional<TaskCard> taskCardStatus = taskCardRepo.findById(comment.getTaskCard().getId());
        if ( taskCardStatus.isEmpty()){
            throw new InvalidTaskCardIdException("Invalid TaskCard Id!!!");
        }
        Comment savedComment =  commentRepo.save(comment);
        savedComment.setUser(this.userService.findById(comment.getUser().getId()));
        return savedComment;
    }


    public List<Comment> showComments ( int id ) {
        List<Comment> commentList = commentRepo.findCommentByTaskCardId(id);
        return commentRepo.findCommentByTaskCardId(id);
    }
}
