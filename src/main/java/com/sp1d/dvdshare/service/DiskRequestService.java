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

    @Autowired
    DiskRepo diskRepo;

    private static final Logger LOG = LogManager.getLogger(DiskRequestService.class);

    public DiskRequest add(DiskRequest request) {
        LOG.debug("adding diskRequest {}", request);
        DiskRequest persistedRequest = diskRequestRepo.add(request);
        return persistedRequest;
    }

    public DiskRequest save(DiskRequest request) {
        LOG.debug("saving diskRequest {}", request);
        DiskRequest savedRequest = diskRequestRepo.save(request);
        return savedRequest;
    }

    public DiskRequest findById(long id) {
        LOG.debug("finding diskRequest by ID {}", id);
        DiskRequest request = diskRequestRepo.findById(id);
        return request;
    }

    public List<DiskRequest> findByUser(RequestSelection selection, User user) {
        LOG.debug("finding diskRequests by user {}", user);
        List<DiskRequest> diskRequests = diskRequestRepo.find(selection, user);
        return diskRequests;
    }

    public List<DiskRequest> findAll() {
        LOG.debug("finding all diskRequests");
        List<DiskRequest> diskRequests = diskRequestRepo.find(RequestSelection.ALL);
        return diskRequests;
    }

    public boolean contains(DiskRequest diskRequest) {
        LOG.debug("finding if particular request {} is contains in table", diskRequest);
        
        return diskRequestRepo.contains(diskRequest);
    }

    public void delete(DiskRequest diskRequest) {
        LOG.debug("deleting request {}", diskRequest);
        
        diskRequest.getDisk().setRequest(null);
        diskRepo.save(diskRequest.getDisk());       
        diskRequest = diskRequestRepo.save(diskRequest);
        diskRequestRepo.delete(diskRequest);
    }

}
