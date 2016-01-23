/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.service;

import com.sp1d.dvdshare.entities.Disk;
import com.sp1d.dvdshare.entities.DiskRequest;
import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.repos.DiskRepo;
import com.sp1d.dvdshare.repos.DiskRequestRepo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sp1d
 */
@Service
@Transactional
public class DiskRequestService {

    @Autowired
    DiskRequestRepo diskRequestRepo;

    private static final Logger LOG = LogManager.getLogger(DiskRequestService.class);

    public DiskRequest add(DiskRequest disk) {
        DiskRequest persistedRequest = diskRequestRepo.add(disk);
        LOG.debug("adding diskRequest {}", persistedRequest);
        return persistedRequest;
    }

    public DiskRequest save(DiskRequest disk) {
        DiskRequest savedRequest = diskRequestRepo.save(disk);
        LOG.debug("saving diskRequest {}", savedRequest);
        return savedRequest;
    }
    
    public DiskRequest findById(long id) {
        DiskRequest request = diskRequestRepo.findById(id);
        LOG.debug("finding diskRequest by ID {}, found {}", id, request);
        return request;
    }
   

}
