package com.penta.aiwmsbackend.model.service;

import java.time.LocalDateTime;
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
        activity.setStartedDate(LocalDateTime.now());
        activity.setEndedDate(LocalDateTime.now());
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
    
    public List<Activity> showActivities ( int taskCardId ) {
        List<Activity> activiyList = activityRepo.findActivityByTaskCardId(taskCardId);
        return activiyList;
    } 
    
    public Activity findByActivityId ( Integer activityId ){
        Optional<Activity> activity=this.activityRepo.findById(activityId);
        if (activity.isPresent()){
            return activity.get();
        }
        return null;
    }

    public Activity updateActivity( Activity activity) throws InvalidTaskCardIdException, DuplicateActivityNameException{
       
        Optional <TaskCard> taskCardStatus = taskCardRepo.findById(activity.getTaskCard().getId());
        if ( taskCardStatus.isEmpty()){
            throw new InvalidTaskCardIdException("Invalid TaskCard!");
        }else {
                 List<Activity> activityList = this.activityRepo.findActivityByTaskCardId(activity.getTaskCard().getId());
                    for (Activity activityName : activityList) {
                        if (activityName.getActivityName().equalsIgnoreCase(activity.getActivityName()) && 
                        activityName.getId()!= activity.getId()){
                            throw new DuplicateActivityNameException("Duplicate Activity Name!");
                        }
                    }
            // activity.setStatus(activity.isStatus()==null ? false : activity.isStatus());
            activity.setStartedDate(activity.getStartedDate()==null ? LocalDateTime.now() : activity.getStartedDate());
            activity.setEndedDate(activity.getEndedDate()==null ? LocalDateTime.now() : activity.getEndedDate());
            
            return this.activityRepo.save(activity);
        }
    }
    
}
