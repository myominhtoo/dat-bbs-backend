package com.penta.aiwmsbackend.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.entity.Notification;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.NotificationRepo;
import com.penta.aiwmsbackend.model.repo.UserRepo;

@Service("notificationService")
public class NotificationService {
    private UserRepo userRepo;
    private NotificationRepo notificationRepo;

    public NotificationService(NotificationRepo notificationRepo, UserRepo userRepo) {
        this.notificationRepo = notificationRepo;
        this.userRepo = this.userRepo;
    }

    public Notification saveNoti(Notification noti) {
        return this.notificationRepo.save(noti);
    }

    public List<Notification> getNotificationsWithUserId(Integer userId) {
        List<Notification> notis = this.notificationRepo.readBySentUserId(userId);
        return notis;
    }

    public Notification seenNoti(Notification notification) {
        Optional<Notification> getNoti = this.notificationRepo.findById(notification.getId());
        getNoti.get().setSeenUsers(notification.getSeenUsers());
        return this.notificationRepo.save(getNoti.get());
    }

}
