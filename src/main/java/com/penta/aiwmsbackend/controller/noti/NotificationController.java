package com.penta.aiwmsbackend.controller.noti;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.penta.aiwmsbackend.model.entity.Notification;
import com.penta.aiwmsbackend.model.service.NotificationService;

@RestController
@RequestMapping(value = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin(originPatterns = "*")
public class NotificationController {
    private NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<List<Notification>> showNotifications(@PathVariable("userId") Integer userId) {
        List<Notification> showNotifications = notificationService.getNotificationsWithUserId(userId);
        return ResponseEntity.ok().body(showNotifications);
    }

    @PutMapping("/users/{userId}/noti/seen")
    public ResponseEntity<Notification> seenNotification(@RequestBody Notification noti,
            @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok().body(notificationService.saveNoti(noti));
    }

    @PutMapping("/users/{userId}/noti/read-all")
    public ResponseEntity<List<Notification>> readAllNoti(@RequestBody List<Notification> noti,
            @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok().body(notificationService.markAllRead(noti, userId));
    }

    @GetMapping("/users/{userId}/seen-notis")
    public ResponseEntity<List<Notification>> getSeenNotiByUserId(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok().body(notificationService.seenNotiByUserId(userId));
    }
}
