/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.controller;

import com.sp1d.dvdshare.entities.DiskRequest;
import com.sp1d.dvdshare.entities.TakenItem;
import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.service.DiskRequestService;
import com.sp1d.dvdshare.service.TakenItemService;
import com.sp1d.dvdshare.service.UserService;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sp1d
 */
@Controller
@RequestMapping(path = "/rest/take")
public class TakenItemController {

    @Autowired UserService userService;
    @Autowired DiskRequestService diskRequestService;
    @Autowired TakenItemService takenItemService;

    private static final Logger LOG = LogManager.getLogger(TakenItemController.class);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody TakenItem takeItem(@RequestParam("id") long reqId, HttpServletRequest req){
        LOG.debug("entering controller at POST /rest/take");

        User userPrincipal = userService.getPrincipal(req);
        DiskRequest diskRequest = diskRequestService.findById(reqId);
        TakenItem takenItem = null;
        if (diskRequest != null && userPrincipal != null
                && diskRequest.getStatus() == DiskRequest.Status.ACCEPTED
                && userPrincipal.equals(diskRequest.getUser())) {
            takenItem = new TakenItem();
            takenItem.setDate(new Date());
            takenItem.setDisk(diskRequest.getDisk());
            takenItem.setUser(userPrincipal);
            takenItem.setOwner(diskRequest.getDisk().getOwner());
            takenItem = takenItemService.add(takenItem);
        }
        return takenItem;
    }
}
