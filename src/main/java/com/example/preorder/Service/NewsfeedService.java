package com.example.preorder.Service;

import com.example.preorder.Dto.Activity;
import com.example.preorder.Entity.*;
import com.example.preorder.JWT.JwtTokenProvider;
import com.example.preorder.Repository.MemberLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class NewsfeedService {
    @Autowired
    private MemberLoginRepository memberLoginRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public List<Activity> newsfeed(String token){

        String email = jwtTokenProvider.getAuthentication(token).getName();

        Member member = memberLoginRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("cannot find user"));

        Set<Follow> follows = member.getFollowings();
        List<Activity> activities = new ArrayList<>();

        for (Follow follow : follows){
            Member following=follow.getFollowing();

            Set<Board>boards=following.getBoards();
            for(Board board:boards){
                activities.add(Activity.builder()
                        .type("Board")
                        .userName(following.getUsername())
                        .content(board.getContent())
                        .createdAt(board.getCreatedAt())
                        .build()
                       );
            }

            Set<Comment>comments=following.getComments();
            for(Comment comment:comments){
                activities.add(Activity.builder()
                        .type("Comment")
                        .userName(following.getUsername())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .build()
                );
            }

            Set<Likes>likes=following.getLikesSet();
            for(Likes like:likes){
                String likeContent;
                if(like.getBoard()!=null){
                    likeContent=like.getBoard().getMember().getUsername()+"님의 글을 좋아합니다.";
                }
                else{
                    likeContent=like.getComment().getMember().getUsername()+"님의 댓글을 좋아합니다.";
                }
                activities.add(Activity.builder()
                        .type("Likes")
                        .userName(following.getUsername())
                        .content(likeContent)
                        .createdAt(like.getCreatedAt())
                        .build()
                );

            }

            Set<Follow>followSet=following.getFollowings();
            for(Follow friend:followSet){
                activities.add(Activity.builder()
                        .type("Follow")
                        .userName(following.getUsername())
                        .content(friend.getFollowing().getUsername())
                        .createdAt(friend.getCreatedAt())
                        .build()
                );
            }




        }

        Collections.sort(activities);
        return activities;

    }


}
