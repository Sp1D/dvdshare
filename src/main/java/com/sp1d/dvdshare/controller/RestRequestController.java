package com.sp1d.dvdshare.controller;

import com.sp1d.dvdshare.entities.Disk;
import com.sp1d.dvdshare.entities.DiskRequest;
import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.service.DiskRequestService;
import com.sp1d.dvdshare.service.DiskService;
import com.sp1d.dvdshare.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author che
 */
@Controller
@RequestMapping(path = "/rest/request")
public class RestRequestController {

    @Autowired
    DiskService diskService;

    @Autowired
    UserService userService;

    @Autowired
    DiskRequestService diskRequestService;

    private static final Logger LOG = LogManager.getLogger(RestRequestController.class);

    @RequestMapping(path = "create", method = RequestMethod.POST)
    @ResponseBody
    DiskRequest createRequest(@RequestParam("id") long diskId, HttpServletRequest req) {
        LOG.debug("entering controller POST /rest/request/create/{}", diskId);

        DiskRequest diskRequest;

        User userPrincipal = userService.getPrincipal(req);
        Disk disk = diskService.findById(diskId);

        if (disk != null && userPrincipal != null && disk.getHolder().equals(disk.getOwner())
                && !diskRequestService.containsDisk(disk)) {
            diskRequest = new DiskRequest();
            diskRequest.setStatus(DiskRequest.Status.REQUESTED);
            diskRequest.setDisk(disk);
            diskRequest.setUser(userPrincipal);
            diskRequest = diskRequestService.add(diskRequest);

            disk.setRequest(diskRequest);
            diskService.save(disk);

        } else {
            diskRequest = new DiskRequest();
            diskRequest.setStatus(DiskRequest.Status.CANCELLED);
        }

        return diskRequest;
    }

    @RequestMapping(path = "delete", method = RequestMethod.POST)
    @ResponseBody
    DiskRequest deleteRequest(@RequestParam("id") long reqId) {
        LOG.debug("entering controller POST /rest/request/delete/{}", reqId);

        User userPrincipal = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        DiskRequest diskRequest = diskRequestService.findById(reqId);

        if (userPrincipal != null && diskRequest != null
                && diskRequest.getUser().equals(userPrincipal)) {
//            Реквест в этом статусе последний раз увидит получатель json.
//              это сигнал, что реквест удален
            diskRequest.setStatus(DiskRequest.Status.CANCELLED);
            diskRequestService.delete(diskRequest);
        }
        return diskRequest;
    }

    @RequestMapping(path = "delete/bydisk", method = RequestMethod.POST)
    @ResponseBody
    DiskRequest deleteRequestByDisk(@RequestParam("id") long diskId) {
        LOG.debug("entering controller POST /rest/request/delete/bydisk/{}", diskId);

        User userPrincipal = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        DiskRequest diskRequest = diskRequestService.findByDiskId(diskId);

        if (userPrincipal != null && diskRequest != null
                && diskRequest.getUser().equals(userPrincipal)) {
//            Реквест в этом статусе последний раз увидит получатель json.
//              это сигнал, что реквест удален
            diskRequest.setStatus(DiskRequest.Status.CANCELLED);
            diskRequestService.delete(diskRequest);
        }
        return diskRequest;
    }

    @RequestMapping(path = "reject", method = RequestMethod.POST)
    @ResponseBody
    DiskRequest rejectRequest(@RequestParam("id") long reqId) {
        LOG.debug("entering controller POST /rest/request/reject/{}", reqId);

        User userPrincipal = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        DiskRequest diskRequest = diskRequestService.findById(reqId);

        if (userPrincipal != null && diskRequest != null
                && diskRequest.getDisk().getOwner().equals(userPrincipal)) {
            diskRequest.setStatus(DiskRequest.Status.REJECTED);
            diskRequest = diskRequestService.save(diskRequest);
        }
        return diskRequest;
    }

    @RequestMapping(path = "accept", method = RequestMethod.POST)
    @ResponseBody
    DiskRequest acceptRequest(@RequestParam("id") long reqId) {
        LOG.debug("entering controller POST /rest/request/accept/{}", reqId);

        User userPrincipal = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        DiskRequest diskRequest = diskRequestService.findById(reqId);

        if (userPrincipal != null && diskRequest != null
                && diskRequest.getDisk().getOwner().equals(userPrincipal)) {
            diskRequest.setStatus(DiskRequest.Status.ACCEPTED);
            diskRequest = diskRequestService.save(diskRequest);
        }
        return diskRequest;
    }

}
