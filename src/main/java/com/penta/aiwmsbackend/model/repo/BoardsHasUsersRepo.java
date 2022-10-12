package com.penta.aiwmsbackend.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;

@Repository("boardsHasUsersRepo")
public interface BoardsHasUsersRepo extends JpaRepository<BoardsHasUsers, Integer> {

    BoardsHasUsers findByUserId(Integer userId);

    @Query(name = "SELECT * FROM boards_has_users t WHERE t.user_id = ?1 AND t.board_id = ?2", nativeQuery = true)
    Optional<BoardsHasUsers> findUserByUserIdAndBoardId(Integer userId, Integer boardId);
}
