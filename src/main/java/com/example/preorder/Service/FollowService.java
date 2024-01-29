package com.example.preorder.Service;

import com.example.preorder.Entity.Follow;
import com.example.preorder.Entity.Member;
import com.example.preorder.Repository.FollowRepository;
import com.example.preorder.Repository.MemberLoginRepository;
import com.example.preorder.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.rmi.server.LogStream.log;
import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final MemberLoginRepository memberLoginRepository;

    public void follow(Long followerId, Long followingId){
        log("service까지는 옴");
        Member follower = memberLoginRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("팔로워가 존재하지 않습니다."));
        Member following = memberLoginRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("팔로잉 대상이 존재하지 않습니다."));

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);

        followRepository.save(follow);

    }

    public void unfollow(Long followId) {
        followRepository.deleteById(followId);
    }




}
