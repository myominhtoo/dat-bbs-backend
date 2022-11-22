package com.penta.aiwmsbackend.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.entity.BoardBookmark;
import com.penta.aiwmsbackend.model.repo.BoardBookmarkRepo;

@Service
public class BoardBookmarkService {

    private BoardBookmarkRepo boardBookmarkRepo;

    @Autowired
    public BoardBookmarkService(BoardBookmarkRepo boardBookmarkRepo) {
        this.boardBookmarkRepo = boardBookmarkRepo;
    }

    public BoardBookmark toggleBoardBookmark(BoardBookmark boardBookmark) {
        Optional<BoardBookmark> optionalBookMark = this.boardBookmarkRepo
                .findByUserIdAndBoardId(boardBookmark.getUser().getId(), boardBookmark.getBoard().getId());

        if (optionalBookMark.isEmpty()) {
            boardBookmark.setCreatedDate(LocalDateTime.now());
            return this.boardBookmarkRepo.save(boardBookmark);
        } else {
            try {
                this.boardBookmarkRepo.deleteById(boardBookmark.getId());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return boardBookmark;
    }

    public List<BoardBookmark> getBoardBookmarksForUser(Integer userId) {
        return this.boardBookmarkRepo.findByUserId(userId);
    }

    public List<BoardBookmark> getBookmarkForUser( Integer id){
        return this.boardBookmarkRepo.findBookmarkByUserId(id);
    }

}

