package com.penta.aiwmsbackend.controller.bookmarkController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.penta.aiwmsbackend.jasperReport.BookMarkReportService;
import com.penta.aiwmsbackend.model.entity.BoardBookmark;
import com.penta.aiwmsbackend.model.service.BoardBookmarkService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(originPatterns = "*")
public class BookmarkController {

    private BoardBookmarkService boardBookmarkService;
    private BookMarkReportService bookmarkReport;
    
    @Autowired
    public BookmarkController (BoardBookmarkService boardBookmarkService,
                                BookMarkReportService bookmarkReport){
                                    this.bookmarkReport=bookmarkReport;
        this.boardBookmarkService=boardBookmarkService;
    }

    @GetMapping(value = "/users/{userId}/bookmarks")
    public ResponseEntity<List<BoardBookmark>> getBoardBookmarksForUser(@PathVariable("userId") int userId) {
        List<BoardBookmark> bookmarks = boardBookmarkService.getBoardBookmarksForUser(userId);
        return ResponseEntity.ok().body(bookmarks);
    }

    @GetMapping(value = "/users/{id}/report-bookmark")
    public void generateReport(@PathVariable("id") Integer id, @RequestParam("format") String format)
            throws JRException, IOException {

        this.bookmarkReport.reportBookmark(id);

        String flag = this.bookmarkReport.exportBoardReport(format);

        Map<String, String> responsetoangular = new HashMap<>();
        responsetoangular.put("flag", flag);
    }

}
