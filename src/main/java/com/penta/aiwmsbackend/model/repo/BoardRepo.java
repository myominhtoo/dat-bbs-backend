package com.penta.aiwmsbackend.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.penta.aiwmsbackend.model.entity.Board;

@Repository
public interface BoardRepo extends JpaRepository<Board, Integer> {

}
