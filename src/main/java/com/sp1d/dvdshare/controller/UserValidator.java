package com.sp1d.dvdshare.controller;

import com.sp1d.dvdshare.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author sp1d
 */
@Component
public class UserValidator implements Validator {

    final static int DEFAULT_USERNAME_MIN = 4,
            DEFAULT_USERNAME_MAX = 30,
            DEFAULT_PASSWORD_MIN = 6,
            DEFAULT_PASSWORD_MAX = 30;

    @Autowired
    Environment env;

    @Override
    public boolean supports(Class<?> type) {
        return User.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required", "Username is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required", "Email is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "plainPassword", "required", "Password is required");

        int usernameMin = env.getProperty("user.username.minsize", Integer.class, DEFAULT_USERNAME_MIN);
        int usernameMax = env.getProperty("user.username.maxsize", Integer.class, DEFAULT_USERNAME_MAX);
        int passwordMin = env.getProperty("user.password.minsize", Integer.class, DEFAULT_PASSWORD_MIN);
        int passwordMax = env.getProperty("user.password.maxsize", Integer.class, DEFAULT_PASSWORD_MAX);

        User user = (User) o;

        if (user.getUsername().length() < usernameMin || user.getUsername().length() > usernameMax) {
            errors.rejectValue("username", "username.length", "Username must be " + usernameMin + " to " + usernameMax + " characters");
        }

        if (user.getPlainPassword().length() < passwordMin || user.getPlainPassword().length() > passwordMax) {
            errors.rejectValue("plainPassword", "password.length", "Password must be " + passwordMin + " to " + passwordMax + " characters");
        }

        if (!user.getPlainPassword().equals(user.getPlainPasswordCheck())) {
            errors.rejectValue("plainPasswordCheck", "Passwords are not match", "Passwords are not match");
        }
    }

}
