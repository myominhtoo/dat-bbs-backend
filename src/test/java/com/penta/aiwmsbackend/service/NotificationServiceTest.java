package com.penta.aiwmsbackend.service;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.Notification;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.NotificationRepo;
import com.penta.aiwmsbackend.model.service.NotificationService;

@SpringBootTest
public class NotificationServiceTest {

    @Mock
    NotificationRepo notificationRepo;

    @InjectMocks
    NotificationService notificationService;

    private static Notification noti;
    private static List<Notification> notilist = new ArrayList<>();

    @BeforeAll
    public static void doBeforeTest() {

        noti = new Notification();
        noti.setId(1);
        noti.setContent("sppl");
        noti.setCreatedDate(LocalDateTime.now());

        Board b = new Board();
        b.setId(1);
        User u = new User();
        u.setId(1);

        noti.setBoard(b);
        noti.setSentUser(u);

        Notification noti1 = new Notification();
        noti1.setId(2);
        noti1.setContent("sppl");
        noti.setCreatedDate(LocalDateTime.now());

        Board b1 = new Board();
        b1.setId(2);
        User u1 = new User();
        u1.setId(1);

        noti1.setBoard(b);
        noti1.setSentUser(u);

        notilist.add(noti);
        notilist.add(noti1);

    }

    @Test
    void saveNotiTest() {

    when(this.notificationRepo.save(noti)).thenReturn(noti);

      assertNotNull(noti);

    }

    @Test
    void getNotificationsWithUserIdTest(){
        when(this.notificationRepo.readBySentUserId(1)).thenReturn(notilist);
        assertNotNull(notilist);
    }

}
