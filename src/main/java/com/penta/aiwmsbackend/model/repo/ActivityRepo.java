package com.penta.aiwmsbackend.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.penta.aiwmsbackend.model.entity.Activity;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, Integer> {

    @Query( value  = "SELECT * FROM activities a WHERE a.task_card_id = ?1 order by started_date desc ", nativeQuery = true)
    List<Activity> findActivityByTaskCardId(int taskCardId);

}
