package com.penta.aiwmsbackend.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.entity.Notification;
import com.penta.aiwmsbackend.model.repo.NotificationRepo;

@Service("notificationService")
public class NotificationService {

    private NotificationRepo notificationRepo;

    public NotificationService( NotificationRepo notificationRepo ) {
        this.notificationRepo = notificationRepo;
    }

    public Notification saveNoti( Notification noti ) {
        return this.notificationRepo.save(noti);
    }

    public List<Notification> getNotificationsWithUserId(Integer userId) {
        List<Notification> notis = this.notificationRepo.readBySentUserId(userId);
        return notis;
    }

}
