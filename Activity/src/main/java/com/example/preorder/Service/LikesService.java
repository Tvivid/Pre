package com.example.preorder.Service;

import com.example.preorder.Dto.LikesDTO;
import com.example.preorder.Entity.Board;
import com.example.preorder.Entity.Comment;
import com.example.preorder.Entity.Likes;
import com.example.preorder.Entity.Member;
import com.example.preorder.Feign.UserFeignClient;
import com.example.preorder.Repository.BoardRepository;
import com.example.preorder.Repository.CommentRepository;
import com.example.preorder.Repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {


    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;

    private final UserFeignClient userFeignClient;


    @Transactional
    public void likeTarget(String token, LikesDTO likesDTO){
        Member member = userFeignClient.getMember(token);

        Likes likes = new Likes();
        likes.setMember(member);


        if(likesDTO.getBoardId()!=null) {
            Board board = boardRepository.findById(likesDTO.getBoardId())
                    .orElseThrow(() -> new IllegalArgumentException("cannot find board"));
            likes.setBoard(board);
        }
        if(likesDTO.getCommentId()!=null) {
            Comment comment = commentRepository.findById(likesDTO.getCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("cannot find comment"));
            likes.setComment(comment);
        }

        likesRepository.save(likes);

    }







}
