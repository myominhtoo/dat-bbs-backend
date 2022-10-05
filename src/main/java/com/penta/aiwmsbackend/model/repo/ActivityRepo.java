package com.penta.aiwmsbackend.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.penta.aiwmsbackend.model.entity.Activity;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, Integer> {

}
