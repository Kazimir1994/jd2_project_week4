package ru.kazimir.bortnik.converter;


import ru.kazimir.bortnik.model.User;
import ru.kazimir.bortnik.model.UserDTO;

public interface UserConverter {
    UserDTO toDTO(User user);

    User fromDTO(UserDTO userDTO);
}
