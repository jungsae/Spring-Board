package com.encore.board.author.domian;
public enum Role
{
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private String role;

    Role(String role)
    {
        this.role = role;
    }
}
