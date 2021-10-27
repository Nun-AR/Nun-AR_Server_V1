package com.nunar.nunar.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifiedUserRequest {
    private String name;
    private String profileUrl;
}
