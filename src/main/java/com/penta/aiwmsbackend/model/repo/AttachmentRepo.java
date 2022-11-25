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

    @Query(value = "SELECT * FROM attachments atts LEFT JOIN activities acts ON atts.activity_id = acts.id LEFT JOIN task_cards tsks ON tsks.id = acts.task_card_id LEFT JOIN boards b  ON b.id = tsks.board_id WHERE b.id= ?1 ", nativeQuery = true)
    List<Attachment> downloadAttachments(Integer boardId);

}
