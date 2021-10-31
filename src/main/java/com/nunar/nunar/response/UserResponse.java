package com.nunar.nunar.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private int userIdx;
    private String name;
    private String profileUrl;
}
