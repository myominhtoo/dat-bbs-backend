package com.penta.aiwmsbackend.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.repo.BoardMessageRepo;

@Service("boardMessageService")
public class BoardMessageService {
    
    private BoardMessageRepo boardMessageRepo;

    @Autowired
    public BoardMessageService(){

    }

}
