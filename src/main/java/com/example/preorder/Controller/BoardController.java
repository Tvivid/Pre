package com.example.preorder.Controller;

import com.example.preorder.Entity.Board;
import com.example.preorder.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;


//    public String saveBoard(@RequestBody Board board, @AuthenticationPrincipal Principal principal){
//
//    }


}
