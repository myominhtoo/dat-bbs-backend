package com.penta.aiwmsbackend.controller.activity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.penta.aiwmsbackend.exception.custom.DuplicateActivityNameException;
import com.penta.aiwmsbackend.exception.custom.InvalidActivityIdException;
import com.penta.aiwmsbackend.exception.custom.InvalidTaskCardIdException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Activity;
import com.penta.aiwmsbackend.model.service.ActivityService;

@RestController
@RequestMapping(value = "/api" ,  produces = { MediaType.APPLICATION_JSON_VALUE} )
@CrossOrigin(originPatterns = "*")
public class ActivityController {

    private ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping(value = "/create-activity")
    public ResponseEntity<HttpResponse<Activity>> createActivity(@RequestBody Activity activity)
            throws InvalidTaskCardIdException, DuplicateActivityNameException {
        Activity createActivityStatus = activityService.createActivity(activity);
        HttpResponse<Activity> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                createActivityStatus != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                createActivityStatus != null ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
                createActivityStatus != null ? "Successfully Added!" : "Failed to add!",
                createActivityStatus != null ? "Ok" : "Error Occured!",
                createActivityStatus != null,
                createActivityStatus);
        return new ResponseEntity<HttpResponse<Activity>>(httpResponse, httpResponse.getHttpStatus());
    }

    @GetMapping(value = "/task-card/{taskCardId}/activities")
    public ResponseEntity<List<Activity>> showActivity(@PathVariable("taskCardId") Integer taskCardId) {
        List<Activity> showActivities = activityService.showActivities(taskCardId);
        return ResponseEntity.ok().body(showActivities);
    }

    @GetMapping(value = "/task-card/{taskCardId}/activities/{activityId}")
    public Activity showActivity(@PathVariable("taskCardId") Integer taskCardId,
            @PathVariable("activityId") Integer activityId) {
        return this.activityService.findByActivityId(activityId);
    }

    @PutMapping(value = "/update-activity")
    public ResponseEntity<HttpResponse<Activity>> UpdateActivity(@RequestBody Activity activity)
            throws InvalidTaskCardIdException, DuplicateActivityNameException, InvalidActivityIdException {
        Activity updateStatus = activityService.updateActivity(activity);
        HttpResponse<Activity> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                updateStatus != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                updateStatus != null ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
                updateStatus != null ? "Successfully Updated!" : "Failed to update!",
                updateStatus != null ? "OK!" : "Error Occured!",
                updateStatus != null,
                updateStatus);
        return new ResponseEntity<HttpResponse<Activity>>(httpResponse, httpResponse.getHttpStatus());
    }

}
