package com.example.preorder.Entity;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Follow {

    @Id @GeneratedValue
    private Long id;


    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Member follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private Member following;


    @CreationTimestamp
    private LocalDateTime createdAt;
}
