/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.controller;

import com.sp1d.dvdshare.entities.Disk;
import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.service.DiskService;
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
@RequestMapping("/disk")
public class DiskController {

    @Autowired
    DiskService diskService;

    @RequestMapping(path = "create", method = RequestMethod.GET)
    String create(Model model) {
        model.addAttribute("disk", new Disk());
        return "disk_create";
    }

    @RequestMapping(path = "create", method = RequestMethod.POST)
    String create(Disk disk, User user) {
        disk.setOwner(user);
        disk.setHolder(user);
        diskService.add(disk);
        return "disk_create";
    }
}
