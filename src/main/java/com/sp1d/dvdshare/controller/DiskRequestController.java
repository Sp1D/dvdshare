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

/*
 * Создание, удаление запросов на взятие диска. Смена статуса запросов. В каждом
 * случае измененный объект запроса возвращается в виде объекта JSON
 *
 * @author che
 */
@Controller
@RequestMapping(path = "/rest/request")
public class DiskRequestController {

    @Autowired
    DiskService diskService;

    @Autowired
    UserService userService;

    @Autowired
    DiskRequestService diskRequestService;

    private static final Logger LOG = LogManager.getLogger(DiskRequestController.class);

    /*
     * Создание запроса. Объект запроса возвращается со статусом REQUESTED в
     * виде JSON. В случае неудачи при создании, возвращается объект со статусом
     * CANCELLED
     */
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

    /*
     * Удаление запроса по ID запроса
     */
    @RequestMapping(path = "delete", method = RequestMethod.POST)
    @ResponseBody
    DiskRequest deleteRequest(@RequestParam("id") long reqId) {
        LOG.debug("entering controller POST /rest/request/delete/{}", reqId);

        DiskRequest diskRequest = diskRequestService.findById(reqId);
        return diskRequestService.delete(diskRequest);
    }

    /*
     * Удаление запроса по ID диска
     */
    @RequestMapping(path = "delete/bydisk", method = RequestMethod.POST)
    @ResponseBody
    DiskRequest deleteRequestByDisk(@RequestParam("id") long diskId) {
        LOG.debug("entering controller POST /rest/request/delete/bydisk/{}", diskId);

        DiskRequest diskRequest = diskRequestService.findByDiskId(diskId);
        return diskRequestService.delete(diskRequest);

    }

    /*
     * Установка запросу статуса REJECTED
     */
    @RequestMapping(path = "reject", method = RequestMethod.POST)
    @ResponseBody
    DiskRequest rejectRequest(@RequestParam("id") long reqId) {
        LOG.debug("entering controller POST /rest/request/reject/{}", reqId);

        return diskRequestService.setRequestStatus(reqId, DiskRequest.Status.REJECTED);
    }

    /*
     * Установка запросу статуса ACCEPTED
     */
    @RequestMapping(path = "accept", method = RequestMethod.POST)
    @ResponseBody
    DiskRequest acceptRequest(@RequestParam("id") long reqId) {
        LOG.debug("entering controller POST /rest/request/accept/{}", reqId);

        return diskRequestService.setRequestStatus(reqId, DiskRequest.Status.ACCEPTED);
    }

}
