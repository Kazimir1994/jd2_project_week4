package ru.kazimir.bortnik.converter;


import ru.kazimir.bortnik.model.Role;
import ru.kazimir.bortnik.model.RoleDTO;

public interface RoleConverter {
    RoleDTO toDTO(Role role);

    Role fromDTO(RoleDTO roleDTO);
}
