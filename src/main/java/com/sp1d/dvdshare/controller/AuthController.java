/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.controller;

import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author sp1d
 */
@Controller
public class AuthController {

    @Autowired
    UserValidator userValidator;

    @Autowired
    UserService userService;

    private static final Logger LOG = LogManager.getLogger(AuthController.class);

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    String showLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        LOG.debug("entering controller at GET /login");
        if (error != null) {
            model.addAttribute("error", "Wrong login or password");
        }
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    String showRegisterPage(Model model) {
        LOG.debug("entering controller at GET /register");
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    String register(User user, BindingResult result) {
        LOG.debug("entering controller at POST /register");
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "register";
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            result.reject("user.exists", "Hey, there is such email in our DB already!");
            return "register";
        }
        userService.add(user);
        return "redirect:/login";
    }
}
