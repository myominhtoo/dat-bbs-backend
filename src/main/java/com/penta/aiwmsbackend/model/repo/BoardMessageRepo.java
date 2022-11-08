package com.penta.aiwmsbackend.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.penta.aiwmsbackend.model.entity.BoardMessage;

@Repository
public interface BoardMessageRepo extends JpaRepository<BoardMessage, Integer> {
 
      @Query( value = "SELECT * FROM board_messages WHERE board_id = ?1 ", nativeQuery = true )
      List <BoardMessage> findBoardMessageByBoardId ( Integer boardId );
}
