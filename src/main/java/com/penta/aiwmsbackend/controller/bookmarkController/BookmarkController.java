package com.penta.aiwmsbackend.controller.bookmarkController;

import java.util.List;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.penta.aiwmsbackend.model.entity.BoardBookmark;
import com.penta.aiwmsbackend.model.service.BoardBookmarkService;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(originPatterns = "*")
public class BookmarkController {

    private BoardBookmarkService boardBookmarkService;
    
    @Autowired
    public BookmarkController (BoardBookmarkService boardBookmarkService){
        this.boardBookmarkService=boardBookmarkService;
    }

    @GetMapping(value = "/users/{userId}/bookmarks")
    public ResponseEntity<List<BoardBookmark>> getBoardBookmarksForUser(@PathVariable("userId") int userId) {
        List<BoardBookmark> bookmarks = boardBookmarkService.getBoardBookmarksForUser(userId);
        return ResponseEntity.ok().body(bookmarks);
    }
}
