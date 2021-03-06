package com.nunar.nunar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Post")
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postIdx;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "tag")
    private String tag;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "file_url")
    private String fileUrl;
}
