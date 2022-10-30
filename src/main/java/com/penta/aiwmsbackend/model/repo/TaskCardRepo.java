package com.penta.aiwmsbackend.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.penta.aiwmsbackend.model.entity.TaskCard;

@Repository
public interface TaskCardRepo extends JpaRepository<TaskCard, Integer> {

    @Query(name = "SELECT * FROM task_cards t WHERE t.board_id = ?1 ", nativeQuery = true)
    List<TaskCard> findTaskCardsByBoardId(int boardId);

    @Query(name = "SELECT * FROM task_cards t WHERE t.board_id =?1 AND id=?2", nativeQuery = true)
    TaskCard findTaskCardByBoardIdAndId(int boardId, int id);

    // @Query(name = "SELECT * FROM task_cards JOIN users ON task_cards.id=users.id
    // WHERE users.id=?1 ", nativeQuery = true)
    // List<TaskCard> findTaskCardByUserId(int userId);

    @Query(name = "SELECT  task_cards.* from task_cards INNER JOIN users_has_tasks ON task_cards.id=users_has_tasks.task_card_id INNER JOIN users ON users.id=users_has_tasks.user_id where users_has_tasks.id=?1 ", nativeQuery = true)
    List<TaskCard> findTaskCardByUserId(int userId);
}
