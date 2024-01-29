package com.example.preorder.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String password;

    private String name;

    @Lob
    private byte[] profileImage;

    private String introduce;

    private String verificationToken;

    // 팔로워 목록: 나를 팔로우하는 회원들
    @OneToMany(mappedBy = "following")
    private Set<Follow> followers = new HashSet<>();

    // 팔로잉 목록: 내가 팔로우하는 회원들
    @OneToMany(mappedBy = "follower")
    private Set<Follow> followings = new HashSet<>();






}

