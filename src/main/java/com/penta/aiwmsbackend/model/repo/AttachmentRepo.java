package com.penta.aiwmsbackend.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.penta.aiwmsbackend.model.entity.Attachment;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, Integer> {

    @Query(name = "SELECT * FROM attachments a WHERE a.activity_id = ?1 ", nativeQuery = true)
    List<Attachment> findAttachmentsByActivityId(int id);
}
