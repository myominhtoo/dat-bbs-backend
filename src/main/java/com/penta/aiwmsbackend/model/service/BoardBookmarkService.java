package com.penta.aiwmsbackend.model.service;

import java.time.LocalDateTime;
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
            }
        }
        return boardBookmark;
    }

    // public BoardBookmark save(BoardBookmark boardBookmark) {
    // return this.boardBookmarkRepo.save(boardBookmark);
    // }

    // public List<BoardBookmark> findByUserId(Integer userId) {
    // return this.boardBookmarkRepo.findByUserId(userId);
    // }

    // public BoardBookmark findUserByIdAndBoardId(Integer userId, Integer boardId)
    // {
    // Optional<BoardBookmark> optionalBoardBookmark =
    // this.boardBookmarkRepo.findUserByUserIdAndBoardId(userId,
    // boardId);
    // return optionalBoardBookmark.isPresent() ? optionalBoardBookmark.get() :
    // null;
    // }

}
