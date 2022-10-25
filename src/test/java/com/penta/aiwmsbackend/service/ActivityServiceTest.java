package com.penta.aiwmsbackend.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.penta.aiwmsbackend.model.entity.Activity;
import com.penta.aiwmsbackend.model.entity.TaskCard;
import com.penta.aiwmsbackend.model.repo.ActivityRepo;
import com.penta.aiwmsbackend.model.repo.AttachmentRepo;
import com.penta.aiwmsbackend.model.repo.TaskCardRepo;
import com.penta.aiwmsbackend.model.service.ActivityService;

@SpringBootTest
public class ActivityServiceTest {

    @Mock
    ActivityRepo activityRepo;
    @Mock
    TaskCardRepo taskCardRepo;
    @Mock
    AttachmentRepo attachmentRepo;

    @InjectMocks
    ActivityService activityService;

    // public LocalDateTime now=LocalDateTime.now();

    static Activity activity, activity1;
    static TaskCard tk;
    static List<Activity> list = new ArrayList<>();

    @BeforeAll
    public static void beforeRunAll() {

        activity = new Activity();

        activity.setId(1);
        activity.setActivityName("sppl");
        activity.setStatus(false);
        activity.setStartedDate(LocalDateTime.now());
        activity.setEndedDate(LocalDateTime.now());

        tk = new TaskCard();
        tk.setId(1);

        activity.setAttatchments(null);
        activity.setTaskCard(tk);

        activity1 = new Activity();

        activity1.setId(2);
        activity1.setActivityName("spplpop");
        activity1.setStatus(false);
        activity1.setStartedDate(LocalDateTime.now());
        activity1.setEndedDate(LocalDateTime.now());

        tk = new TaskCard();
        tk.setId(1);

        activity1.setAttatchments(null);
        activity1.setTaskCard(tk);

        list.add(activity);

        // list.add(activity1);

    }

    @Test
    void createActivityTest() throws Exception{

        when(this.taskCardRepo.findById(1)).thenReturn(Optional.of(tk)); 

        when(this.activityRepo.findActivityByTaskCardId(1)).thenReturn(new ArrayList<Activity>());

        this.activityService.createActivity(activity);

        verify(this.activityRepo,times(1)).save(activity);

    }

    @Test
    void showActivitiesTest() throws Exception{

             when(this.activityRepo.findActivityByTaskCardId(1)).thenReturn(list);

             assertNotNull(this.activityService.showActivities(1));

    }

    @Test
    void findByActivityIdTest() throws Exception{

         when(this.activityRepo.findById(1)).thenReturn(Optional.of(activity));

         assertNotNull(activity);
    }

    @Test

    void updateActivityTest() throws Exception{

        when(this.taskCardRepo.findById(1)).thenReturn(Optional.of(tk));

        when(this.activityRepo.findActivityByTaskCardId(1)).thenReturn(list);

        this.activityService.createActivity(activity1);

        verify(this.activityRepo,times(1)).save(activity1);
    }

}
