package com.example.preorder.Service;

import com.example.preorder.Entity.Member;
import com.example.preorder.Repository.FollowRepository;
import com.example.preorder.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public void follow(Long followerId, Long followingId){
        Member follower = memberRepository.findById
    }




}
