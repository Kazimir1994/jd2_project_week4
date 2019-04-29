package ru.kazimir.bortnik.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kazimir.bortnik.UserService;
import ru.kazimir.bortnik.model.UserDTO;
import ru.kazimir.bortnik.model.UserDetail;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(AppUserDetailsService.class);
    private final UserService userService;

    @Autowired
    public AppUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("User is trying to login by email |{}", email);
        UserDTO userDTO = userService.getByEmail(email);
        if (userDTO == null) {
            logger.info("User with this email was not found |{}", email);
            throw new UsernameNotFoundException("User is not found");
        }
        logger.info("This user is logged in {}", userDTO);
        return new UserDetail(userDTO);
    }
}
