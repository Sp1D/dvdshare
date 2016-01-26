package com.sp1d.dvdshare.service;

import com.sp1d.dvdshare.entities.Disk;
import com.sp1d.dvdshare.entities.DiskRequest;
import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.repos.DiskRepo;
import com.sp1d.dvdshare.repos.DiskRequestRepo;
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

    @Autowired
    UserService userService;

    private static final Logger LOG = LogManager.getLogger(DiskRequestService.class);

    public DiskRequest add(DiskRequest request) {
        LOG.debug("adding diskRequest {}", request);
        return diskRequestRepo.add(request);
    }

    public DiskRequest save(DiskRequest request) {
        LOG.debug("saving diskRequest {}", request);
        return diskRequestRepo.save(request);
    }

    public DiskRequest findById(long id) {
        LOG.debug("finding diskRequest by ID {}", id);
        return diskRequestRepo.findById(id);
    }

    public List<DiskRequest> findByUser(RequestSelection selection, User user) {
        LOG.debug("finding diskRequests by user {}", user);
        return diskRequestRepo.find(selection, user);
    }

    public DiskRequest findByDiskId(long diskId) {
        LOG.debug("finding diskRequest by disk ID {}", diskId);
        return diskRequestRepo.findByDiskId(diskId);
    }

    public long countByUser(RequestSelection selection, User user) {
        LOG.debug("finding count of diskRequests by user {}", user);
        return diskRequestRepo.count(selection, user);
    }

    public long countNewIncomingByUser(RequestSelection selection, User user) {
        LOG.debug("finding count of new incoming diskRequests by user {}", user);
        return diskRequestRepo.countNewIncoming(selection, user);
    }

    public List<DiskRequest> findAll() {
        LOG.debug("finding all diskRequests");
        return diskRequestRepo.find(RequestSelection.ALL);
    }

    public boolean contains(DiskRequest diskRequest) {
        LOG.debug("finding if particular request {} is contains in table", diskRequest);
        return diskRequestRepo.contains(diskRequest);
    }

    public boolean containsDisk(Disk disk) {
        LOG.debug("finding if request for disk {} is contains in table", disk);
        return diskRequestRepo.containsDisk(disk);
    }

    public DiskRequest delete(DiskRequest diskRequest) {
        LOG.debug("deleting request {}", diskRequest);

        User userPrincipal = userService.getPrincipal();

        if (userPrincipal != null && diskRequest != null
                && diskRequest.getUser().equals(userPrincipal)) {
//            Реквест в этом статусе последний раз увидит получатель json.
//              это сигнал, что реквест удален
            diskRequest.setStatus(DiskRequest.Status.CANCELLED);

            diskRequest.getDisk().setRequest(null);
            diskRepo.save(diskRequest.getDisk());

            diskRequest = diskRequestRepo.save(diskRequest);
            diskRequestRepo.delete(diskRequest);
        }
        return diskRequest;

    }

    public DiskRequest setRequestStatus(long reqId, DiskRequest.Status status) {
        User userPrincipal = userService.getPrincipal();
        DiskRequest diskRequest = findById(reqId);

        if (userPrincipal != null && diskRequest != null
                && diskRequest.getDisk().getOwner().equals(userPrincipal)) {
            diskRequest.setStatus(status);
            diskRequest = save(diskRequest);
        }
        return diskRequest;
    }
}
