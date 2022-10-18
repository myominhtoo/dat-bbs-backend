package com.penta.aiwmsbackend.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.penta.aiwmsbackend.model.entity.Board;

@Repository("boardRepo")
public interface BoardRepo extends JpaRepository<Board, Integer> {

    @Query(name = "SELECT * FROM boards bs WHERE bs.user_id = ?1  ", nativeQuery = true)
    List<Board> findBoardsByUserId(Integer userId);

}
