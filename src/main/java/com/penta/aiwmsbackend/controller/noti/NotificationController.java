package com.penta.aiwmsbackend.controller.noti;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.penta.aiwmsbackend.model.entity.Notification;
import com.penta.aiwmsbackend.model.service.NotificationService;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin( originPatterns = "*")
public class NotificationController {
    private NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService){
        this.notificationService=notificationService;
    }

    @GetMapping("users/{userId}/notifications")
    public ResponseEntity<List<Notification>> showNotifications (@PathVariable("userId") Integer userId){
        List<Notification> showNotifications = notificationService.getNotificationsWithUserId(userId);
        return ResponseEntity.ok().body(showNotifications);
    }
}
