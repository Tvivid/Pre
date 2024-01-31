package com.example.preorder.Controller;

import com.example.preorder.Dto.FollowDTO;
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
    public ResponseEntity<Void> follow(@RequestHeader("Authorization") String accesstoken, @RequestBody FollowDTO followingEmail) {

        accesstoken=accesstoken.substring(7);
        followService.follow(accesstoken, followingEmail.getFollowing());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{followId}")
    public ResponseEntity<Void> unfollow(@PathVariable Long followId) {
        followService.unfollow(followId);
        return ResponseEntity.ok().build();
    }



}
