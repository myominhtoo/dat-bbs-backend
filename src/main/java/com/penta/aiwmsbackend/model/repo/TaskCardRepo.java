package com.penta.aiwmsbackend.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.penta.aiwmsbackend.model.entity.TaskCard;

@Repository
public interface TaskCardRepo extends JpaRepository<TaskCard, Integer> {

    @Query( name = "SELECT * FROM task_cards t WHERE t.board_id = ?1 " , nativeQuery = true )
    List<TaskCard> findTaskCardsByBoardId( int boardId );

}
