package com.jitendra.homehelp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Token {
    private String _token;
    private String userName;

    public Token(String token) {
    }
}
