/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.controller;

import com.sp1d.dvdshare.entities.Disk;
import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.service.DiskService;
import com.sp1d.dvdshare.service.UserService;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author sp1d
 */
@Controller
@RequestMapping(path = "/")
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    DiskService diskService;

    private static final Logger LOG = LogManager.getLogger(MainController.class);

    @RequestMapping(method = RequestMethod.GET)
    String showHomePage(Model model, HttpServletRequest req) {
        LOG.debug("entering controller at GET /");
        User user;
        if (req.getUserPrincipal() != null) {
            user = userService.findByEmail(req.getUserPrincipal().getName());
            if (user != null) {
                model.addAttribute("user", user);
                model.addAttribute("disks", diskService.findAllByOwner(user, Disk.Field.ID));
            }
        }
        return "user";
    }
}
