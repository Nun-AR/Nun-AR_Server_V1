package com.nunar.nunar.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class RegisterRequest {
    private String id;
    private String password;
    private String name;
    private String profileUrl;
}
