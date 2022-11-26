package com.penta.aiwmsbackend.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;

import com.penta.aiwmsbackend.exception.custom.InvalidTaskCardIdException;
import com.penta.aiwmsbackend.model.entity.Comment;
import com.penta.aiwmsbackend.model.entity.TaskCard;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.CommentRepo;
import com.penta.aiwmsbackend.model.repo.TaskCardRepo;
import com.penta.aiwmsbackend.model.service.CommentService;
import com.penta.aiwmsbackend.model.service.UserService;

@SpringBootTest
public class CommentServiceTest {

    @InjectMocks
    CommentService commentService;
    @InjectMocks
    UserService userService;

    @Mock
    CommentRepo commentRepo;
    @Mock
    TaskCardRepo taskCardRepo;
    
    private static User user;
    private static Comment comment ;
    private static List<Comment> comments;

   @BeforeAll
   public static void doBeforeTest(){
    TaskCard taskcard1=new TaskCard();
    taskcard1.setId(1);
    User user1 = new User();
    user1.setId(1);
    Comment comment1 = new Comment();
    comment1.setComment("comment");
    comment1.setCreatedDate(LocalDateTime.now());
    comment1.setId(1);
    comment1.setTaskCard(taskcard1);
    comment1.setUser(user1);

    TaskCard taskcard2=new TaskCard();
    taskcard2.setId(2);
    User user2 = new User();
    user2.setId(2);
    Comment comment2 = new Comment();
    comment2.setComment("comment");
    comment2.setCreatedDate(LocalDateTime.now());
    comment2.setId(2);
    comment2.setTaskCard(taskcard2);
    comment2.setUser(user2);

    comment = comment1;
    comments = new ArrayList<>();
        Collections.addAll( comments , comment1 , comment2);

   }

   @Test
   public void createComment() throws InvalidTaskCardIdException{
       when (this.commentRepo.findById(comment.getTaskCard().getId())).thenReturn(Optional.of(comment));
       when(this.commentRepo.save(comment)).thenReturn(comment);
       this.commentRepo.save(comment);
       verify(this.commentRepo , times (1)).save(comment);
   }

   @Test
   public void showComments(){
    
    when( this.commentRepo.findCommentByTaskCardId(1, Sort.by(Sort.Direction.DESC, "createdDate")) ).thenReturn( comments );
    assertEquals( this.commentService.showComments(1).size() , comments.size() );
    verify( this.commentRepo , times(1) ).findCommentByTaskCardId(1, Sort.by(Sort.Direction.DESC, "createdDate"));
   }


  @Test
  public void deleteComment(){
    when ( this.commentRepo.findById(1)).thenReturn(Optional.of(comment));
    this.commentRepo.deleteById(comment.getId());
    verify (this.commentRepo, times(1)).deleteById(1);

  }

  @Test
  public void deleteAllGenerations(){
    when(this.commentRepo.findCommentsByParentCommentId(1)).thenReturn(comments);
    List <Comment> comments= this.commentRepo.findCommentsByParentCommentId(1);
    this.commentRepo.deleteByParentCommentId(comment.getId());
    this.commentRepo.deleteById(comment.getId());
    verify (this.commentRepo, times(1)).findCommentsByParentCommentId(1);
  }

  @Test
  public void updateComment(){
    Optional<Comment> updateComment = Optional.of(comment);
    when(this.commentRepo.findById(1)).thenReturn(Optional.of(comment));
    when(this.commentRepo.save(comment)).thenReturn(comment);
    this.commentRepo.save(comment);
    verify(this.commentRepo , times (1)).save(comment);

  } 

}
