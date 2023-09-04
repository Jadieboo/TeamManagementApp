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

    @Autowired
    private final AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public AppUserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("In the user service");
        logger.trace("user service");

        //TODO: validate that the user exists and matches what is in the database

        // hard coded user
//        return new AppUser("user", encoder.encode("password"), AppUserRole.USER);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new  UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }
}
