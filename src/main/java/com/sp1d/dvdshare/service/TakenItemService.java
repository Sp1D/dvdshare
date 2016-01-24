/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.service;

import com.sp1d.dvdshare.entities.DiskRequest;
import com.sp1d.dvdshare.entities.TakenItem;
import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.repos.DiskRepo;
import com.sp1d.dvdshare.repos.DiskRequestRepo;
import com.sp1d.dvdshare.repos.TakenItemRepo;
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
public class TakenItemService {

    @Autowired
    TakenItemRepo repo;

    private static final Logger LOG = LogManager.getLogger(TakenItemService.class);

    public TakenItem add(TakenItem item) {
        LOG.debug("adding takenItem {}", item);
        return repo.add(item);
    }

    public TakenItem save(TakenItem item) {
        LOG.debug("saving takenItem {}", item);
        return repo.save(item);
    }

    public TakenItem findByDiskId(long diskId) {
        LOG.debug("finding takenItem by disk's ID {}", diskId);
        return repo.findByDiskId(diskId);
    }

//    public TakenItem findById(long id) {
//        LOG.debug("finding takenItem by ID {}", id);
//        return repo.findById(id);
//    }

//    public List<DiskRequest> findByUser(RequestSelection selection, User user) {
//        LOG.debug("finding diskRequests by user {}", user);
//        List<DiskRequest> diskRequests = diskRequestRepo.find(selection, user);
//        return diskRequests;
//    }
//
//    public long countByUser(RequestSelection selection, User user) {
//        LOG.debug("finding count of diskRequests by user {}", user);
//        long diskRequests = diskRequestRepo.count(selection, user);
//        return diskRequests;
//    }
//
//    public long countNewIncomingByUser(RequestSelection selection, User user) {
//        LOG.debug("finding count of new incoming diskRequests by user {}", user);
//        long diskRequests = diskRequestRepo.countNewIncoming(selection, user);
//        return diskRequests;
//    }
//
//    public List<DiskRequest> findAll() {
//        LOG.debug("finding all diskRequests");
//        List<DiskRequest> diskRequests = diskRequestRepo.find(RequestSelection.ALL);
//        return diskRequests;
//    }
//
//    public boolean contains(DiskRequest diskRequest) {
//        LOG.debug("finding if particular request {} is contains in table", diskRequest);
//
//        return diskRequestRepo.contains(diskRequest);
//    }

    public void delete(TakenItem item) {
        LOG.debug("deleting takenItem {}", item);
//        repo.save(item);
        repo.delete(item);
    }

}
