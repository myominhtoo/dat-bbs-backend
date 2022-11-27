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
    public ActivityService(ActivityRepo activityRepo, TaskCardRepo taskCardRepo) {
        this.activityRepo = activityRepo;
        this.taskCardRepo = taskCardRepo;
    }

    public Activity createActivity(Activity activity)
            throws InvalidTaskCardIdException, DuplicateActivityNameException {
        activity.setStatus(false);
        activity.setStartedDate(LocalDateTime.now());
        activity.setEndedDate(LocalDateTime.now());
        activity.setDeleteStatus(false);
        Optional<TaskCard> taskCardStatus = taskCardRepo.findById(activity.getTaskCard().getId());
        if (taskCardStatus.isEmpty()) {
            throw new InvalidTaskCardIdException("Invalid TaskCard!");
        } else {
            List<Activity> activityList = this.activityRepo.findActivityByTaskCardId(activity.getTaskCard().getId());
            for (Activity activityName : activityList) {
                if (activityName.getActivityName().equalsIgnoreCase(activity.getActivityName())) {
                    throw new DuplicateActivityNameException("Duplicate Activity Name!");
                }
            }

            return this.activityRepo.save(activity);
        }
    }

    public List<Activity> showActivities(int taskCardId) {
        List<Activity> activiyList = activityRepo.findActivityByTaskCardId(taskCardId);
        return activiyList;
    }

    public Activity findByActivityId(Integer activityId) {
        Optional<Activity> activity = this.activityRepo.findById(activityId);
        if (activity.isPresent()) {
            return activity.get();
        }
        return null;
    }

    public Activity updateActivity(Activity activity)
            throws InvalidTaskCardIdException, DuplicateActivityNameException {

        Optional<TaskCard> taskCardStatus = taskCardRepo.findById(activity.getTaskCard().getId());
        if (taskCardStatus.isEmpty()) {
            throw new InvalidTaskCardIdException("Invalid TaskCard!");
        } else {
            List<Activity> activityList = this.activityRepo.findActivityByTaskCardId(activity.getTaskCard().getId());
            for (Activity activityName : activityList) {
                if (!activityName.getId().equals(activity.getId())
                        && activityName.getActivityName().equalsIgnoreCase(activity.getActivityName())) {
                    throw new DuplicateActivityNameException("Duplicate Activity Name!");
                }
            }
            Activity savedActivity = this.activityRepo.findById(activity.getId()).get();
            savedActivity.setActivityName(activity.getActivityName());
            savedActivity.setStatus(activity.isStatus());
            savedActivity.setStartedDate(activity.getStartedDate());
            savedActivity.setDeleteStatus(activity.isDeleteStatus());
            savedActivity.setEndedDate(activity.getEndedDate());

            return this.activityRepo.save(savedActivity);
        }
    }

    public void deleteActivity(Integer id) {

        // this.attachmentService.deleteAttachmenActivities(id);

        // this.attachmentRepo.deleteAttachmentByActivityId(id);

        // boolean flag = false;

        // try {

        // this.attachmentService.deleteAttachmenActivities(id);
        // flag = true;

        // } catch (Exception e) {
        // flag = false;
        // }

        // if (flag) {

        this.activityRepo.deleteById(id);

    }

}
