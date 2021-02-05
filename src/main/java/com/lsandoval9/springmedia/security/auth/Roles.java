package com.lsandoval9.springmedia.security.auth;

public enum Roles {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String role;

    Roles(String role) {

        this.role = role;
    }

    public String getRoleName() {
        return role;
    }
}
