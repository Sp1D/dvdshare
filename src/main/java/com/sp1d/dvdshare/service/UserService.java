/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.service;

import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.repos.UserRepo;
import java.util.List;
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


    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User add(User user) {
        if (user != null && user.getPassword() == null && user.getPlainPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPlainPassword()));
            user.setPlainPassword(null);
            user.setPlainPasswordCheck(null);
        }
        return userRepo.add(user);
    }

    public User save(User user) {
        return userRepo.save(user);
    }

}
