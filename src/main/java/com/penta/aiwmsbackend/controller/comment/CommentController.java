package com.penta.aiwmsbackend.controller.comment;

import java.time.LocalDate;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.penta.aiwmsbackend.exception.custom.InvalidTaskCardIdException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Comment;
import com.penta.aiwmsbackend.model.service.CommentService;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin( originPatterns = "*")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping(value = "create-comment")
          public ResponseEntity<HttpResponse<Comment>> CreateComment ( @RequestBody Comment comment)
          throws InvalidTaskCardIdException{
            Comment createCommentStatus = commentService.createComment(comment);
            HttpResponse<Comment> httpResponse= new HttpResponse<>(
                LocalDate.now(),
                createCommentStatus != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                createCommentStatus != null ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
                createCommentStatus != null ? "Successfully Created!" : "Failed to create!",
                createCommentStatus != null ? "OK" : " Error!",
                createCommentStatus != null,
                createCommentStatus
            );
            return new ResponseEntity<HttpResponse<Comment>>(httpResponse, httpResponse.getHttpStatus());
          }


          @GetMapping(value = "/tasks/{id}/comments")
          public ResponseEntity<List<Comment>> showComment(@PathVariable("id") Integer id)  {
            List<Comment> showComments = commentService.showComments(id);
           return ResponseEntity.ok().body(showComments);
        }  
}