package com.encore.board.author.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AuthorDetailResDto
{
    private Long id;
    private String email;
    private String name;
    private String password;
    private String role;
    private int counts;
    private LocalDateTime create_time;
//    private LocalDateTime updated_time;
}
