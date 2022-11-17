package com.penta.aiwmsbackend.model.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;

@Repository("boardsHasUsersRepo")
public interface BoardsHasUsersRepo extends JpaRepository<BoardsHasUsers, Integer> {

    @Query(name = "SELECT * FROM boards_has_users t WHERE t.user_id = ?1 ", nativeQuery = true)
    List<BoardsHasUsers> findByUserId(Integer userId);

    @Query(name = "SELECT * FROM boards_has_users t WHERE t.user_id = ?1 AND t.board_id = ?2 ", nativeQuery = true)
    Optional<BoardsHasUsers> findUserByUserIdAndBoardId(Integer userId, Integer boardId);

    // @Query(name = " SELECT * FROM boards_has_users t WHERE board_id = ?1 AND
    // joined_status = ?2 ", nativeQuery = true)
    // List<BoardsHasUsers> findUsersByBoardId(Integer boardId , boolean
    // joined_status );

    @Query(name = "SELECT * FROM boards_has_users t WHERE t.board_id = ?1 AND t.joined_status = true ", nativeQuery = true)
    List<BoardsHasUsers> findUsersByBoardId(Integer boardId);

    @Query(name = "SELECT * FROM boards_has_users b WHERE b.user_id = ?1 AND b.joined_status = true  ", nativeQuery = true)
    List<BoardsHasUsers> findBoardsByUserId(Integer userId);

    @Query(value = "SELECT distinct(bs.user_id),bs.* FROM boards_has_users bs LEFT JOIN boards b ON b.id=bs.board_id WHERE b.user_id=?1 && bs.joined_status=true;", nativeQuery = true)
    List<BoardsHasUsers> findAllBoardsMembersByUserId(Integer userId);

}
