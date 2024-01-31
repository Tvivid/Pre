package com.example.preorder.Service;

import com.example.preorder.Entity.Board;
import com.example.preorder.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    @Transactional
    public void createBoard(String title, String content){
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
    }

}
