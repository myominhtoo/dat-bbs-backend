package com.penta.aiwmsbackend.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.penta.aiwmsbackend.model.entity.Stage;

@Repository
public interface StageRepo extends JpaRepository<Stage, Integer> {

    @Query( name = "SELECT * FROM stages t WHERE t.board_id = ?1 " ,nativeQuery = true  )
    List<Stage> findStageByBoardId( Integer boardId );

    // Object update(Stage stage);

    // @Query( name = "SELECT * FROM stages t WHERE t.board_id = ?1 OR t.default_status = 1" , nativeQuery = false )
    // List<Stage> findDefaultStageAndStageByBoardId( Integer boardId ); 

   
}
