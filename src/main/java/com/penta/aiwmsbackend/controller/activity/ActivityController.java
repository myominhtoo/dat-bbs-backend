package com.penta.aiwmsbackend.controller.activity;

import java.time.LocalDate;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.penta.aiwmsbackend.exception.custom.DuplicateActivityNameException;
import com.penta.aiwmsbackend.exception.custom.InvalidTaskCardIdException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Activity;
import com.penta.aiwmsbackend.model.service.ActivityService;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin( originPatterns = "*")
public class ActivityController {

    private ActivityService activityService;

    @Autowired
    public ActivityController( ActivityService activityService ) {
        this.activityService = activityService;
    }
    @PostMapping(value = "/create-activity")
        public ResponseEntity<HttpResponse<Activity>> createActivity (@RequestBody Activity activity)
        throws InvalidTaskCardIdException , DuplicateActivityNameException {
            Activity createActivityStatus = activityService.createActivity(activity);
            HttpResponse<Activity> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                createActivityStatus != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST ,
                createActivityStatus != null ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
                createActivityStatus != null ? "Successfully Added!" : "Failed to add!",
                createActivityStatus != null ? "Ok" : "Error Occured!",
                createActivityStatus != null  ,
                createActivityStatus
            );
            return new ResponseEntity<HttpResponse<Activity>>(httpResponse , httpResponse.getHttpStatus());
        }
   
}

