package com.penta.aiwmsbackend.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.penta.aiwmsbackend.model.entity.Attachment;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, Integer> {

}
