package com.penta.aiwmsbackend.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.penta.aiwmsbackend.model.entity.Notification;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer> {
     
    @Query ( value = "SELECT * FROM notifications n LEFT JOIN boards_has_users b ON b.board_id=n.board_id LEFT JOIN boards b1 ON n.board_id= b1.id WHERE b.user_id=?" , nativeQuery = true)
    List<Notification> findNotificationsByUserId( int userId);

}
