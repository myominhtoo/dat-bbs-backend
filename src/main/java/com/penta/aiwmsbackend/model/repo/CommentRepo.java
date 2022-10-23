package com.penta.aiwmsbackend.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.penta.aiwmsbackend.model.entity.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {

    @Query(name = "SELECT * FROM comments c WHERE c.taskCard_id = ?1 ", nativeQuery = true)
    List<Comment> findCommentByTaskCardId(int id);
}
