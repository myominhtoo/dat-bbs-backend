package com.penta.aiwmsbackend.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.entity.Notification;
import com.penta.aiwmsbackend.model.repo.NotificationRepo;

@Service("notificationService")
public class NotificationService {

    private NotificationRepo notificationRepo;

    public NotificationService(NotificationRepo notificationRepo) {
        this.notificationRepo = notificationRepo;
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

    public List<Notification> markAllRead(List<Notification> notifications, Integer userId) {
        List<Notification> getAllNoti = this.notificationRepo.readBySentUserId(userId);
        for (Notification getAll : getAllNoti) {
            for (Notification notification : notifications) {
                getAll.setSeenUsers(notification.getSeenUsers());
            }
        }

        return this.notificationRepo.saveAll(getAllNoti);
    }

    public List<Notification> seenNotiByUserId(Integer userId) {
        return this.notificationRepo.seenNotiByUserId((userId));
    }

}
