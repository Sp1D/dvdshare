package com.sp1d.dvdshare.service;

import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.repos.UserRepo;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final Logger LOG = LogManager.getLogger(UserService.class);

    public List<User> findAll() {
        LOG.debug("finding all users");
        return userRepo.findAll();
    }

    public User findById(long id) {
        LOG.debug("finding user by ID {}", id);
        return userRepo.findById(id);
    }

    public User findByUsername(String username) {
        LOG.debug("finding user by username {}", username);
        return userRepo.findByUsername(username);
    }

    public User findByEmail(String email) {
        LOG.debug("finding user by email {}", email);
        return userRepo.findByEmail(email);
    }

    public User add(User user) {
        LOG.debug("adding user", user);
        if (user != null && user.getPassword() == null && user.getPlainPassword() != null) {
            LOG.debug("user's password is not set, so encoding it from plain value");
            user.setPassword(passwordEncoder.encode(user.getPlainPassword()));
            user.setPlainPassword(null);
            user.setPlainPasswordCheck(null);
            user.setEnabled(true);
        }
        return userRepo.add(user);
    }

    public User save(User user) {
        LOG.debug("saving user {}", user);
        return userRepo.save(user);
    }

    public User getPrincipal(HttpServletRequest req) {
        if (req.getUserPrincipal() != null) {
            return findByEmail(req.getUserPrincipal().getName());
        } else {
            return null;
        }
    }

    public User getPrincipal() {
        return findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
