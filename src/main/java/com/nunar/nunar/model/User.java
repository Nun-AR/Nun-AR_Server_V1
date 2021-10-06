package com.nunar.nunar.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "User")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userIdx;

    @Column(name = "id", unique = true)
    private String id;

    @Column(name ="pw")
    private String pw;

    @Column(name = "name")
    private String name;

    @Column(name = "profileUrl")
    private String profileUrl;
}
