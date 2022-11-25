package com.penta.aiwmsbackend.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        comment.setCreatedDate(LocalDateTime.now());

        Optional<TaskCard> taskCardStatus = taskCardRepo.findById(comment.getTaskCard().getId());
        if ( taskCardStatus.isEmpty()){
            throw new InvalidTaskCardIdException("Invalid TaskCard Id!!!");
        }
        Comment savedComment =  commentRepo.save(comment);
        savedComment.setUser(this.userService.findById(comment.getUser().getId()));
        return savedComment;
    }


    public List<Comment> showComments ( int id ) {
        List<Comment> commentList = commentRepo.findCommentByTaskCardId(id , Sort.by(Sort.Direction.DESC, "createdDate") );
        return commentList;
    }

    public Boolean deleteComment(Integer id){
        try{
            this.deleteAllGenerations(id);
            this.commentRepo.deleteById(id);
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void deleteAllGenerations( Integer commentId ){
        List<Comment> childCommets = this.commentRepo.findCommentsByParentCommentId( commentId );
        childCommets.stream()
        .forEach( childComment -> {
            this.deleteAllGenerations(childComment.getId());
            this.commentRepo.deleteByParentCommentId(childComment.getId());
            this.commentRepo.deleteById(childComment.getId());
        });
    }
   
    public Comment updateComment(Comment comment){
        Optional<Comment> cmt =commentRepo.findById(comment.getId());
        Comment updateComment = cmt.get();
        updateComment.setComment(comment.getComment());
        return this.commentRepo.save(updateComment);

    }
}
