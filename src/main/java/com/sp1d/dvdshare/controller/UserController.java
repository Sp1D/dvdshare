/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.controller;

import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.repos.UserRepo;
import com.sp1d.dvdshare.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author sp1d
 */
@Controller
@RequestMapping(value = "/users")
public class UserController {

    @Autowired UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    String userList(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    String userNew(Model model) {
        model.addAttribute("user", new User());
        return "usercreate";
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    ModelAndView userAdd(User user, Model model) {
        User saved = userService.add(user);
        model.addAttribute("user", saved);
        return new ModelAndView("redirect:/disk/create", "usermodel", model);
    }
}
