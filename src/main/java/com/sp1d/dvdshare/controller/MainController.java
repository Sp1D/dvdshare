/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author sp1d
 */
@Controller
@RequestMapping(path = "/")
public class MainController {

    private static final Logger LOG = LogManager.getLogger(MainController.class);

    @RequestMapping(method = RequestMethod.GET)
    String showRoot(){
        LOG.debug("entering controller GET /");
        return "redirect:/user/self";
    }

}
