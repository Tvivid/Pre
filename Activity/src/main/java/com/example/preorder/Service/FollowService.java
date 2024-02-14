package com.example.preorder.Service;

import com.example.preorder.Entity.Follow;
import com.example.preorder.Entity.Member;
import com.example.preorder.Feign.UserFeignClient;
import com.example.preorder.Repository.FollowRepository;
import com.example.preorder.Repository.MemberLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserFeignClient userFeignClient;

    private final MemberLoginRepository memberLoginRepository;


    @Transactional
    public void follow(String token, String followingEmail){
        Member follower = userFeignClient.getMember(token);


        Member following = memberLoginRepository.findByEmail(followingEmail)
                .orElseThrow(() -> new IllegalArgumentException("not exist following user"));

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        followRepository.save(follow);



    }

    public void unfollow(Long followId) {
        followRepository.deleteById(followId);
    }




}
