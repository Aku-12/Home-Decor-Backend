package com.example.homedecor.Pojo;

import lombok.*;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponsePojo {
    private String accessToken;
    private String refreshToken;
    private Integer userId;
    private List<String> roles;
}
