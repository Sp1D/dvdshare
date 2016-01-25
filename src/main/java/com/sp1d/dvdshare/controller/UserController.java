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

//    Страница со списком всех пользователей
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    String showAllUsers(Model model, HttpServletRequest req) {
        LOG.debug("entering controller at GET /users");

        User userPrincipal = userService.getPrincipal(req);
        if (userPrincipal != null) {
            model.addAttribute("incomingRequestsCount",
                    diskRequestService.countNewIncomingByUser(RequestSelection.IN, userPrincipal));
        }
        model.addAttribute("users", userService.findAll());
        return "users";
    }

//    Страница со списком всех дисков
    @RequestMapping(path = "/users/disks", method = RequestMethod.GET)
    String showAllDisks(Model model, HttpServletRequest req) {
        LOG.debug("entering controller at GET /users/disks");

        User userPrincipal = userService.getPrincipal(req);
        setDiskModel(userPrincipal, userPrincipal, model, DiskSelection.FOREIGN);
        return "disks";
    }

//    Страница конкретного пользователя ( с набором данных по умолчанию )
    @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
    String showUser(@PathVariable long id, Model model, HttpServletRequest req) {
        LOG.debug("entering controller at GET /user/{}", id);
        setDiskModel(id, req, model, null);
        return "user";
    }

//    Страница конкретного пользователя с указанным набором данных
    @RequestMapping(path = "/user/{id}/{whose}", method = RequestMethod.GET)
    String showUser(@PathVariable long id, @PathVariable String whose, Model model, HttpServletRequest req) {
        LOG.debug("entering controller at GET /user/{}/{}", id, whose);
        setDiskModel(id, req, model, whose);
        return "user";
    }

//    Страница залогиненного пользователя ( с набором данных по умолчанию )
    @RequestMapping(path = "/user/self", method = RequestMethod.GET)
    String showHomePage(Model model, HttpServletRequest req) {
        LOG.debug("entering controller at GET /user/self");
        setDiskModel(req, model, null);
        return "home";
    }

//    Страница залогиненного пользователя с указанным набором данных
    @RequestMapping(path = "/user/self/{whose}", method = RequestMethod.GET)
    String showHomeTabs(@PathVariable String whose, Model model, HttpServletRequest req) {
        LOG.debug("entering controller at GET /user/self/{}", whose);
        setDiskModel(req, model, whose);
        return "home";
    }

//    Страница запросов залогиненного пользователя с указанным набором данных
    @RequestMapping(path = "/user/self/requests/{select}")
    String showRequests(@PathVariable String select, Model model, HttpServletRequest req) {
        LOG.debug("entering controller at GET /user/self/requests/{}", select);
        User userPrincipal = null;
        if (req.getUserPrincipal() != null) {

            userPrincipal = userService.findByEmail(req.getUserPrincipal().getName());
            if (userPrincipal != null) {
                RequestSelection selection = RequestSelection.IN;
                if (select != null) {
                    try {
                        selection = RequestSelection.valueOf(select.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        selection = RequestSelection.IN;
                    }
                }
                List<DiskRequest> requests = diskRequestService.findByUser(selection, userPrincipal);

                model.addAttribute("userPrincipal", userPrincipal);
                model.addAttribute("requests", requests);
                model.addAttribute("selection", selection);
                model.addAttribute("incomingRequestsCount", diskRequestService.countNewIncomingByUser(RequestSelection.IN, userPrincipal));
            }
        }
        return "requests";
    }

    private Model setDiskModel(long id, HttpServletRequest req, Model model, String whose) {
        User user = userService.findById(id);
        User userPrincipal = userService.getPrincipal(req);
        return setDiskModel(userPrincipal, user, model, resolveSelection(whose));
    }

    private Model setDiskModel(HttpServletRequest req, Model model, String whose) {
        User userPrincipal = userService.getPrincipal(req);
        return setDiskModel(userPrincipal, userPrincipal, model, resolveSelection(whose));
    }



/*
 *   userPrincipal - пользователь, от чьего имени пришел запрос.
    user - пользователь, относительно которого требуется поиск дисков,
    например взятые им диски, отданные им, или не принадлежащие ему(DiskSelection.FOREIGN)
    selection - набор данных, который должен быть сформирован
 */

    private Model setDiskModel(User userPrincipal, User user, Model model, DiskSelection selection) {
        if (user != null) {
            List<Disk> disks;

            disks = diskService.findByUser(selection, user);
            model.addAttribute("userPrincipal", userPrincipal);
            model.addAttribute("selection", selection.toString());
            model.addAttribute("user", user);
            model.addAttribute("disks", disks);
            model.addAttribute("requests", diskRequestService.findAll());
            model.addAttribute("principalRequests", diskRequestService.findByUser(RequestSelection.OUT, userPrincipal));
            model.addAttribute("incomingRequestsCount", diskRequestService.countNewIncomingByUser(RequestSelection.IN, userPrincipal));
        }
        return model;
    }

    private DiskSelection resolveSelection(String whose) {
        DiskSelection selection = DiskSelection.OWN;
        if (whose != null) {
            try {
                selection = DiskSelection.valueOf(whose.toUpperCase());
            } catch (IllegalArgumentException e) {
                selection = DiskSelection.OWN;
            }
        }
        return selection;
    }

}
