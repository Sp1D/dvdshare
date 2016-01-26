package com.sp1d.dvdshare.controller;

import com.sp1d.dvdshare.entities.Disk;
import com.sp1d.dvdshare.entities.DiskRequest;
import com.sp1d.dvdshare.entities.TakenItem;
import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.service.DiskRequestService;
import com.sp1d.dvdshare.service.DiskService;
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

    @Autowired
    UserService userService;
    @Autowired
    DiskRequestService diskRequestService;
    @Autowired
    TakenItemService takenItemService;
    @Autowired
    DiskService diskService;

    private static final Logger LOG = LogManager.getLogger(TakenItemController.class);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    TakenItem takeItem(@RequestParam("id") long reqId, HttpServletRequest req) {
        LOG.debug("entering controller at POST /rest/take");

        User userPrincipal = userService.getPrincipal(req);
        DiskRequest diskRequest = diskRequestService.findById(reqId);
        TakenItem takenItem = null;
        if (diskRequest != null && userPrincipal != null
                && diskRequest.getStatus() == DiskRequest.Status.ACCEPTED
                && userPrincipal.equals(diskRequest.getUser())) {

//            Ради чего всё и затевалось - передача диска новому владельцу
            Disk disk = diskRequest.getDisk();
            disk.setHolder(userPrincipal);
            disk = diskService.save(disk);

            takenItem = new TakenItem();
            takenItem.setDate(new Date());
            takenItem.setDisk(disk);
            takenItem.setUser(userPrincipal);
            takenItem.setOwner(disk.getOwner());
            takenItem = takenItemService.add(takenItem);

            diskRequestService.delete(diskRequest);

        }
        return takenItem;
    }

    @RequestMapping(path = "back", method = RequestMethod.POST)
    @ResponseBody
    Disk returnItem(@RequestParam("id") long diskId, HttpServletRequest req) {
        LOG.debug("entering controller at POST /rest/take/back");

        User userPrincipal = userService.getPrincipal(req);
        TakenItem takenItem = takenItemService.findByDiskId(diskId);
        Disk disk = null;
        if (userPrincipal != null && takenItem != null && userPrincipal.equals(takenItem.getUser())) {
//          Диск возвращается владельцу
            disk = takenItem.getDisk();
            disk.setHolder(disk.getOwner());
            disk = diskService.save(disk);

            takenItemService.delete(takenItem);
        }
        return disk;
    }
}
