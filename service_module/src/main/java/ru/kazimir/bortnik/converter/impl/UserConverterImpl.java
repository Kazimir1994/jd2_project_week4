package ru.kazimir.bortnik.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kazimir.bortnik.converter.RoleConverter;
import ru.kazimir.bortnik.converter.UserConverter;
import ru.kazimir.bortnik.model.User;
import ru.kazimir.bortnik.model.UserDTO;

@Component
public class UserConverterImpl implements UserConverter {
    private final RoleConverter roleConverter;

    @Autowired
    public UserConverterImpl(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Override
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setRole(roleConverter.toDTO(user.getRole()));
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    @Override
    public User fromDTO(UserDTO userDTO) {
        User user = new User();
        user.setRole(roleConverter.fromDTO(userDTO.getRole()));
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
