package com.nunar.nunar.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostResponse {
    private int postIdx;
    private int userIdx;
    private String writer;
    private String title;
    private int bookmarks;
    private Boolean isBookmarks;
    private String tag;
    private String thumbnail;
    private String fileUrl;
}
