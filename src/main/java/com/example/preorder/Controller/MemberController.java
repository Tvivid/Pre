package com.example.preorder.Controller;

import com.example.preorder.Dto.JwtToken;
import com.example.preorder.Dto.LogInDTO;
import com.example.preorder.Dto.MemberDto;
import com.example.preorder.Entity.Member;


import com.example.preorder.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Slf4j
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


    @PostMapping("/signin")
    public JwtToken signIn(@RequestBody LogInDTO logInDTO) {
        String email = logInDTO.getEmail();
        String password = logInDTO.getPassword();
        JwtToken jwtToken = memberService.signIn(email, password);
        log.info("request email = {}, password = {}", email, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @PostMapping("/profile/{memberId}/update")
    public ResponseEntity<String> updateMember(@PathVariable Long memberId, @RequestBody MemberDto memberUpdateDTO) {
        memberService.updateMember(memberId, memberUpdateDTO);
        return ResponseEntity.ok("회원 정보가 성공적으로 업데이트되었습니다.");
    }



}