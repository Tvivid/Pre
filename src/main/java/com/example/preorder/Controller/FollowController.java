package com.example.preorder.Controller;

import com.example.preorder.Entity.Follow;
import com.example.preorder.Service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {


    private final FollowService followService;

    @PostMapping
    public ResponseEntity<Void> follow(@RequestParam Long followerId, @RequestParam Long followingId) {
        followService.follow(followerId, followingId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{followId}")
    public ResponseEntity<Void> unfollow(@PathVariable Long followId) {
        followService.unfollow(followId);
        return ResponseEntity.ok().build();
    }



}
