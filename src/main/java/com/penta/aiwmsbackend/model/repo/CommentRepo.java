package com.penta.aiwmsbackend.model.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.penta.aiwmsbackend.model.entity.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {

    @Query(name = "SELECT * FROM comments c WHERE c.task_card_id = ?1 ", nativeQuery = true)
    List<Comment> findCommentByTaskCardId(int id, Sort sort);

    @Transactional
    @Query(name = "DELETE FROM comments c  WHERE c.parent_id = ?1 ", nativeQuery = true)
    void deleteByParentCommentId(Integer id);

    @Query(name = "SELECT * FROM comments c WHERE c.parent_id = ?1 ", nativeQuery = true)
    List<Comment> findCommentsByParentCommentId(Integer parentId);

    // @Query(value = "DELETE FROM comments c WHERE c.task_card_id= ?1 ",
    // nativeQuery = true)
    // void deleteCommentByActivitieId(Integer id);
}
