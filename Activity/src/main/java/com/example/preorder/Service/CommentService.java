package com.example.preorder.Service;


import com.example.preorder.Dto.CommentDTO;
import com.example.preorder.Entity.Board;
import com.example.preorder.Entity.Comment;
import com.example.preorder.Entity.Member;
import com.example.preorder.Feign.UserFeignClient;

import com.example.preorder.Repository.BoardRepository;
import com.example.preorder.Repository.CommentRepository;
import com.example.preorder.Repository.MemberLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {


    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    private final UserFeignClient userFeignClient;






    @Transactional
    public void save(String token, CommentDTO commentDTO){


        Member member = userFeignClient.getMember(token);


        Board board = boardRepository.findById(commentDTO.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("cannot find board"));

        Comment comment = new Comment();

        comment.setMember(member);
        comment.setContent(commentDTO.getContent());
        comment.setBoard(board);
        commentRepository.save(comment);


    }
}
