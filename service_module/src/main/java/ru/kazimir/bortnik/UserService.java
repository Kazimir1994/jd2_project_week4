package ru.kazimir.bortnik;

import ru.kazimir.bortnik.model.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();

    void add(UserDTO userDTO);

     UserDTO getByEmail(String userDTO);
}
