package ru.kazimir.bortnik.controlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kazimir.bortnik.UserService;
import ru.kazimir.bortnik.model.RoleDTO;
import ru.kazimir.bortnik.model.UserDTO;
import ru.kazimir.bortnik.model.enums.RoleEnum;

import java.util.List;

@Controller
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getItems(Model model) {
        List<UserDTO> userDTOList = userService.getUsers();
        model.addAttribute("users", userDTOList);
        logger.info("Show Users {}", userDTOList);
        return "/users";
    }
}
