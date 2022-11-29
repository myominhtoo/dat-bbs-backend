package com.penta.aiwmsbackend.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.penta.aiwmsbackend.model.entity.TaskCard;

@Repository
public interface TaskCardRepo extends JpaRepository<TaskCard, Integer> {

    @Query(value = "SELECT * FROM task_cards t WHERE t.delete_status=false AND t.board_id = ?1 ", nativeQuery = true)
    List<TaskCard> findTaskCardsByBoardId(int boardId);

    @Query(name = "SELECT * FROM task_cards t WHERE t.board_id =?1 AND id=?2", nativeQuery = true)
    TaskCard findTaskCardByBoardIdAndId(int boardId, int id);

    @Query(value = "SELECT * FROM task_cards t1 RIGHT JOIN users_has_tasks t2 ON t1.id = t2.task_card_id  WHERE t2.user_id = ?1 ", nativeQuery = true)
    List<TaskCard> findTasksById(int userId);

    @Query(name = "SELECT * FROM task_cards t WHERE t.stage_id = ?1 ", nativeQuery = true)
    List<TaskCard> findTaskCardsByStageId(int stageId);

    @Query(value = "SELECT * FROM task_cards t WHERE t.delete_status = true AND t.board_id = ?1 ", nativeQuery = true)
    List<TaskCard> findDeletedTaskCardsByBoardId(int id);

    @Query(value = "SELECT * FROM task_cards t LEFT JOIN stages s ON t.stage_id = s.id WHERE t.board_id=?1 ", nativeQuery = true)
    List<TaskCard> findReportTasks(int id);

    @Query(value = "SELECT * FROM task_cards t1 left join stages t2 on t1.stage_id=t2.id WHERE delete_status=false and t1.board_id=?1 ", nativeQuery = true)
    List<TaskCard> rpTaskCards(int id);

    @Query(value = "SELECT * FROM task_cards WHERE stage_id=3 AND id = ?1 ", nativeQuery = true)
    TaskCard findTaskCardById(int id);

    @Query(value = "SELECT * FROM task_cards WHERE delete_status=true AND board_id= ?1 ", nativeQuery = true)
    List<TaskCard> findDeleteTasks(int boardId);

    @Query(value = "SELECT * FROM task_cards WHERE delete_status=true AND  stage_id=3 AND id = ?1 ", nativeQuery = true)
    TaskCard findTaskCardByDeleteStatus(int id);

    @Query(value = "SELECT * FROM task_cards t LEFT JOIN stages s ON t.stage_id = s.id WHERE t.delete_status = true AND t.board_id=?1", nativeQuery = true)
    List<TaskCard> findArchiveTaskCard(int id);
}
