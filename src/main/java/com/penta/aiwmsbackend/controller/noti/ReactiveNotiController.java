package com.penta.aiwmsbackend.controller.noti;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.penta.aiwmsbackend.model.entity.Notification;
import com.penta.aiwmsbackend.model.service.NotificationService;

@Controller
public class ReactiveNotiController {
    
    private NotificationService notificationService;
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ReactiveNotiController( NotificationService  notificationService , SimpMessagingTemplate simpMessagingTemplate  ){
        this.notificationService = notificationService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping( value = "/boards/{id}/send-notification")
    public Notification sendNotification( @Payload Notification notification , @DestinationVariable("id") Integer boardId ){
        notification.setCreatedDate(LocalDateTime.now());
        notification.setInvitiation(false);
        Notification savedNotification = this.notificationService.saveNoti(notification);
        this.simpMessagingTemplate.convertAndSend( "/boards/"+notification.getBoard().getId()+"/notifications"  , savedNotification);
        return savedNotification;
    }

    /*
     * added for internal app invitiation notification
     */ 
    @MessageMapping( value = "/users/{userId}/send-notification" )
    public void sendInvitiationNoti( @Payload Notification notification , @DestinationVariable("userId") Integer userId ){
        notification.setCreatedDate(LocalDateTime.now());
        System.out.println("Hello");
        notification.setInvitiation(true);
        Notification savedNotification = this.notificationService.saveNoti(notification);
        this.simpMessagingTemplate.convertAndSend("/users/"+userId+"/notifications", savedNotification);     
    }

}
