package com.nunar.nunar.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostRequest {
    private String title;
    private String tag;
    private String thumbnail;
    private String fileUrl;
    private boolean isPublic;
}
