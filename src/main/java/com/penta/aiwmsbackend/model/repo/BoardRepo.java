package com.penta.aiwmsbackend.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.penta.aiwmsbackend.model.entity.Board;

@Repository("boardRepo")
public interface BoardRepo extends JpaRepository<Board, Integer> {

    @Query(value = "SELECT * FROM boards bs WHERE bs.delete_status = false  AND bs.user_id = ?1 ", nativeQuery = true)
    List<Board> findBoardsByUserId(Integer userId);

    @Query(value = "SELECT * FROM  boards WHERE id = ?1 ; ", nativeQuery = true)
    Board updateDeleteStatusOnBoardsByBoardId(Integer id);

    @Query ( value = " SELECT * FROM boards b WHERE b.delete_status = true AND b.user_id = ?1 " , nativeQuery = true )
    List<Board> findDeletedBoardsByUserId( Integer userId );

}
