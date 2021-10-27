package com.nunar.nunar.model;

import com.nunar.nunar.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userIdx;

    @Column(name = "id", unique = true)
    private String id;

    @Column(name ="pw")
    private String pw;

    @Column(name = "name")
    private String name;

    @Column(name = "profileUrl")
    private String profileUrl;

    public UserResponse toUserResponse() {
        return new UserResponse(name, profileUrl);
    }
}
