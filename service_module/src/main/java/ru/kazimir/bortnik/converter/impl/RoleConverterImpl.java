package ru.kazimir.bortnik.converter.impl;

import org.springframework.stereotype.Component;
import ru.kazimir.bortnik.converter.RoleConverter;
import ru.kazimir.bortnik.model.Role;
import ru.kazimir.bortnik.model.RoleDTO;

@Component
public class RoleConverterImpl implements RoleConverter {
    @Override
    public RoleDTO toDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleEnum(role.getRoleEnum());
        return roleDTO;
    }

    @Override
    public Role fromDTO(RoleDTO roleDTO) {
        Role role = new Role();
        role.setRoleEnum(roleDTO.getRoleEnum());
        return role;
    }
}
