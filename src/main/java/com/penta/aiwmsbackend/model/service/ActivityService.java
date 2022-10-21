package com.penta.aiwmsbackend.model.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.exception.custom.DuplicateActivityNameException;
import com.penta.aiwmsbackend.exception.custom.InvalidTaskCardIdException;
import com.penta.aiwmsbackend.model.entity.Activity;
import com.penta.aiwmsbackend.model.entity.TaskCard;
import com.penta.aiwmsbackend.model.repo.ActivityRepo;
import com.penta.aiwmsbackend.model.repo.TaskCardRepo;

@Service("activityService")
public class ActivityService {
    private ActivityRepo activityRepo;
    private TaskCardRepo taskCardRepo;

    @Autowired
    public ActivityService ( ActivityRepo activityRepo , TaskCardRepo taskCardRepo ){
        this.activityRepo = activityRepo;
        this.taskCardRepo = taskCardRepo;
    }

    public Activity createActivity ( Activity activity ) throws InvalidTaskCardIdException, DuplicateActivityNameException{
        activity.setStatus(false);
        activity.setStartedDate(LocalDate.now());
        activity.setEndedDate(LocalDate.now());
        Optional <TaskCard> taskCardStatus = taskCardRepo.findById(activity.getTaskCard().getId());
        if ( taskCardStatus.isEmpty()){
            throw new InvalidTaskCardIdException("Invalid TaskCard!");
        }else {
                 List<Activity> activityList = this.activityRepo.findActivityByTaskCardId(activity.getTaskCard().getId());
                    for (Activity activityName : activityList) {
                        if (activityName.getActivityName().equalsIgnoreCase(activity.getActivityName())){
                            throw new DuplicateActivityNameException("Duplicate Activity Name!");
                        }
                    }
        
        return this.activityRepo.save(activity);
    }
    
    }
}

