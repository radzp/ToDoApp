package com.marcin.ania.ToDoAPP.roles;

public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");

    final String value;

    UserRole(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
