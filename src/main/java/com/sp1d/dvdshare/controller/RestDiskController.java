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
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sp1d
 */
@Controller
@RequestMapping("/disk")
public class RestDiskController {

    @Autowired
    DiskService diskService;

    @Autowired
    UserService userService;

    @RequestMapping(path = "create", method = RequestMethod.GET)
    String create(Model model) {
        model.addAttribute("disk", new Disk());
        return "disk_create";
    }

    @RequestMapping(path = "create", method = RequestMethod.POST)
    @ResponseBody
    Disk create(@RequestParam String title, HttpServletRequest req) {
        User user;
        Disk disk = new Disk();
        if (req.getUserPrincipal() != null) {
            user = userService.findByEmail(req.getUserPrincipal().getName());
            if (user != null) {
                disk.setTitle(title);
                disk.setOwner(user);
                disk.setHolder(user);
                disk = diskService.add(disk);
            }
        }
        return disk;
    }
}
