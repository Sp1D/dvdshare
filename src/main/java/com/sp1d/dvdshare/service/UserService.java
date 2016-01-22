/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.service;

import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.repos.UserRepo;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sp1d
 */
@Service
@Transactional
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired PasswordEncoder passwordEncoder;

    private static final Logger LOG = LogManager.getLogger(UserService.class);


    public List<User> findAll() {
        LOG.debug("finding all users");
        return userRepo.findAll();
    }

    public User findById(long id) {
        User user = userRepo.findById(id);
        LOG.debug("finding user by ID, found {}", user);
        return user;
    }

    public User findByUsername(String username) {
        User user = userRepo.findByUsername(username);
        LOG.debug("finding user by username, found {}", user);
        return user;
    }

    public User findByEmail(String email) {
        User user = userRepo.findByEmail(email);
        LOG.debug("finding user by email, found {}", user);
        return user;
    }

    public User add(User user) {
        if (user != null && user.getPassword() == null && user.getPlainPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPlainPassword()));
            user.setPlainPassword(null);
            user.setPlainPasswordCheck(null);
            user.setEnabled(true);
        }
        User persistedUser = userRepo.add(user);
        LOG.debug("adding user {}", persistedUser);
        return persistedUser;
    }

    public User save(User user) {
        User savedUser = userRepo.save(user);
        LOG.debug("saving user {}", savedUser);
        return savedUser;
    }

}
