package com.example.preorder.Controller;

import com.example.preorder.Entity.Member;
import com.example.preorder.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup"; // 뷰 페이지의 이름을 반환
    }

    @PostMapping("/signup")
    public void registerUser(@RequestBody Member member){
        memberService.registerUser(member);
    }



}