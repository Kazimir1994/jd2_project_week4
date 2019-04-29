package ru.kazimir.bortnik.model;

import ru.kazimir.bortnik.model.enums.RoleEnum;

public class RoleDTO {
    private RoleEnum roleEnum;

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }
}
