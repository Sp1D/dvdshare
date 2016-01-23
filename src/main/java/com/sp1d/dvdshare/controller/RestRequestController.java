/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.controller;

import com.sp1d.dvdshare.entities.Disk;
import com.sp1d.dvdshare.entities.DiskRequest;
import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.service.DiskRequestService;
import com.sp1d.dvdshare.service.DiskService;
import com.sp1d.dvdshare.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    DiskRequest createRequest(@RequestParam long id) {
        LOG.debug("entering controller POST /rest/request/create/{} from user {}", id);

        DiskRequest diskRequest;

        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Disk disk = diskService.findById(id);

        if (disk != null && user != null) {
            diskRequest = new DiskRequest();
            diskRequest.setStatus(DiskRequest.Status.REQUESTED);
            diskRequest.setDisk(disk);
            diskRequest.setUser(user);
            if (!diskRequestService.contains(diskRequest)) {
                diskRequest = diskRequestService.add(diskRequest);
            }
        } else {
            diskRequest = new DiskRequest();
            diskRequest.setStatus(DiskRequest.Status.CANCELLED);
        }

        return diskRequest;
    }
}