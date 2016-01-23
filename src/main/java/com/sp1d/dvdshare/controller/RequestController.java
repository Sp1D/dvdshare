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
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author che
 */
@Controller
//@RestController
@RequestMapping(path = "/rest/request")
public class RequestController {

    @Autowired
    DiskService diskService;
    @Autowired
    UserService userService;
    @Autowired
    DiskRequestService diskRequestService;

    private static final Logger LOG = LogManager.getLogger(RequestController.class);

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
            diskRequest = diskRequestService.add(diskRequest);
        } else {
            diskRequest = new DiskRequest();
            diskRequest.setStatus(DiskRequest.Status.CANCELLED);
        }

        return diskRequest;
    }
}
