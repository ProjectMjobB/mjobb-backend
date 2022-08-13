package com.mjobb.enums;

public enum RoleEnum {

    EMPLOYEE("ROLE_EMPLOYEE"),
    COMPANY("ROLE_COMPANY"),
    ADMIN("ROLE_ADMIN"),
    MODERATOR("ROLE_MODERATOR");

    private final String code;


    RoleEnum(String role) {
        code = role;
    }

    public String code() {
        return code;
    }
}
