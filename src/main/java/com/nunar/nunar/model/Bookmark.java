package com.nunar.nunar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@IdClass(value = Bookmark.BookmarkId.class)
@Table(name = "Bookmark")
@NoArgsConstructor
public class Bookmark {

    @Id
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "post")
    private Post post;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookmarkId implements Serializable {
        private Integer user;
        private Integer post;
    }

}
