package com.example.cloudsky.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "realname", nullable = false)
    private String realname;

    @Column(name = "introduction")
    private String introduction;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Post> postList;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, String realname, String introduction, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.introduction = introduction;
        this.role = role;
    }
}