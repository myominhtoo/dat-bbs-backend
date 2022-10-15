package com.penta.aiwmsbackend.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.penta.aiwmsbackend.model.entity.TaskCard;

@Repository
public interface TaskCardRepo extends JpaRepository<TaskCard, Integer> {

}
