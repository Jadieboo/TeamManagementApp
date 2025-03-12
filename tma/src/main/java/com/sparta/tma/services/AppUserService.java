package com.sparta.tma.services;

import com.sparta.tma.entities.Role;
import com.sparta.tma.repositories.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AppUserService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final static String USER_NOT_FOUND_MSG = "User with username: \"%s\" is not found";
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder encoder) {
        this.appUserRepository = appUserRepository;
        this.encoder = encoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("In the user service, load user by username: " + username);

        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new  UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }
}
