package com.yui.clearops.services;

import com.yui.clearops.model.entities.Software;
import com.yui.clearops.repositories.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareService {

    @Autowired
    private SoftwareRepository repository;

    public List<Software> listAll(){
        return repository.findAll();
    }

}
