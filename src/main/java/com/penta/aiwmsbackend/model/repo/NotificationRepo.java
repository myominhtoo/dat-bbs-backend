package com.penta.aiwmsbackend.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.penta.aiwmsbackend.model.entity.Notification;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer> {

    @Query(value = "SELECT DISTINCT(n.id) , n.* FROM notifications n LEFT JOIN boards b ON n.board_id = b.id LEFT JOIN boards_has_users bs ON n.board_id = bs.board_id WHERE ( n.sent_user_id != ?1 AND bs.user_id = ?1 AND bs.joined_status = true ) OR ( n.sent_user_id != ?1  AND b.user_id = ?1 ) OR ( n.sent_user_id != ?1 AND n.is_invitiation = true) AND (b.delete_status = false) order by n.created_date desc ", nativeQuery = true)
    List<Notification> readBySentUserId(int userId);

}
