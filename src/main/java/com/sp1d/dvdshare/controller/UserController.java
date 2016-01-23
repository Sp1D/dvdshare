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
import com.sp1d.dvdshare.service.DiskSelection;
import com.sp1d.dvdshare.service.DiskService;
import com.sp1d.dvdshare.service.RequestSelection;
import com.sp1d.dvdshare.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author sp1d
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    DiskService diskService;

    @Autowired
    DiskRequestService diskRequestService;

    private static final Logger LOG = LogManager.getLogger(UserController.class);

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    String showAllUsers(Model model) {
        LOG.debug("entering controller at GET /users");
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
    String showUser(@PathVariable long id, Model model, HttpServletRequest req) {
        LOG.debug("entering controller at GET /user/{id}");
        setModel(id, req, model, null);
        return "user";
    }

    @RequestMapping(path = "/user/{id}/{whose}", method = RequestMethod.GET)
    String showUser(@PathVariable long id, @PathVariable String whose, Model model, HttpServletRequest req) {
        LOG.debug("entering controller at GET /user/{id}/{whose}");
        setModel(id, req, model, whose);
        return "user";
    }

    @RequestMapping(path = "/user/self", method = RequestMethod.GET)
    String showHomePage(Model model, HttpServletRequest req) {
        LOG.debug("entering controller at GET /user/self");
        setModel(req, model, null);
        return "home";
    }

    @RequestMapping(path = "/user/self/{whose}", method = RequestMethod.GET)
    String showHomeTabs(@PathVariable String whose, Model model, HttpServletRequest req) {
        LOG.debug("entering controller at GET /user/self/{whose}");
        setModel(req, model, whose);
        return "home";
    }

    @RequestMapping(path = "/user/self/requests/{select}")
    String showRequests(@PathVariable String select, Model model, HttpServletRequest req) {
        LOG.debug("entering controller at GET /user/self/requests");
        User user = null;
        if (req.getUserPrincipal() != null) {

            user = userService.findByEmail(req.getUserPrincipal().getName());
            if (user != null) {
                RequestSelection selection = RequestSelection.OUT;
                if (select != null) {
                    try {
                        selection = RequestSelection.valueOf(select.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        selection = RequestSelection.OUT;
                    }
                }
//                List<DiskRequest> requests = diskRequestService.findByUser(selection, user);
                for (DiskRequest request : user.getRequests()) {
                    System.out.println(request.getId() + " : " + request.getDisk().getTitle());
                }
                model.addAttribute("requests", user.getRequests());
                model.addAttribute("selection", selection);
            }
        }
        return "requests";
    }

    private Model setModel(long id, HttpServletRequest req, Model model, String whose) {
        User user = userService.findById(id);
        User principal = null;
        if (req.getUserPrincipal() != null) {
            principal = userService.findByEmail(req.getUserPrincipal().getName());
        }
        return setModel(principal, user, model, whose);
    }

    private Model setModel(HttpServletRequest req, Model model, String whose) {
        User principal = null;
        if (req.getUserPrincipal() != null) {
            principal = userService.findByEmail(req.getUserPrincipal().getName());
        }
        return setModel(principal, principal, model, whose);
    }

    private Model setModel(User userPrincipal, User user, Model model, String whose) {
        if (user != null) {
            List<Disk> disks;
            DiskSelection selection = DiskSelection.OWN;
            if (whose != null) {
                try {
                    selection = DiskSelection.valueOf(whose.toUpperCase());
                } catch (IllegalArgumentException e) {
                    selection = DiskSelection.OWN;
                }
            }

            disks = diskService.findByUser(selection, user);
            model.addAttribute("userPrincipal", userPrincipal);
            model.addAttribute("selection", selection.toString());
            model.addAttribute("user", user);
            model.addAttribute("disks", disks);
            model.addAttribute("requests", diskRequestService.findAll());
        }
        return model;
    }

}
