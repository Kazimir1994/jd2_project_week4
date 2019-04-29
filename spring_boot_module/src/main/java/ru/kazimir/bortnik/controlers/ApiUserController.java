package ru.kazimir.bortnik.controlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kazimir.bortnik.UserService;
import ru.kazimir.bortnik.model.UserDTO;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(ApiUserController.class);

    @Autowired
    public ApiUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity saveUser(@RequestBody UserDTO userDTO) {
        logger.info("Came to add user {}", userDTO);
        userService.add(userDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
