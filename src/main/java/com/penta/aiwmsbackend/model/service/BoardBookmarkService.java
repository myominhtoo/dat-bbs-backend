package com.penta.aiwmsbackend.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.repo.BoardBookmarkRepo;

@Service
public class BoardBookmarkService {
    @Autowired
    private BoardBookmarkRepo boardBookmarkRepo;
}
