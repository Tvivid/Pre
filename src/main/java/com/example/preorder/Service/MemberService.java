package com.example.preorder.Service;

import com.example.preorder.Dto.JwtToken;
import com.example.preorder.Dto.MemberDto;
import com.example.preorder.Entity.Member;
import com.example.preorder.JWT.JwtTokenProvider;
import com.example.preorder.Repository.MemberLoginRepository;
import com.example.preorder.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MailService mailService;

    private final MemberLoginRepository memberLoginRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(Member member) {
        // 사용자 정보를 저장하고 인증 이메일을 전송
        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);

        String subject = "회원가입 인증";
        String text = "회원가입을 완료하려면 아래 링크를 클릭하세요: http://your-app-url/confirm?token=" + member.getVerificationToken();
//        mailService.sendEmail(member.getEmail(), subject, text);
    }


//    @Transactional //변경
//    public Long join(Member member) {
//        validateDuplicateMember(member); //중복 회원 검증
//        memberRepository.save(member);
//        return member.getId();
//    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public JwtToken signIn(String email, String password) {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }


    @Transactional
    public void updateMember(Long memberId, MemberDto memberUpdateDTO) {
        // 회원 정보 조회
        Member member = memberLoginRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. id=" + memberId));

        // 회원 정보 업데이트

        if (memberUpdateDTO.getName() != null) {
            member.setName(memberUpdateDTO.getName());
        }
        if (memberUpdateDTO.getIntroduce() != null) {
            member.setIntroduce(memberUpdateDTO.getIntroduce());
        }
        if (memberUpdateDTO.getProfileImage() != null) {
            member.setProfileImage(memberUpdateDTO.getProfileImage());
        }

        if (memberUpdateDTO.getPassword() != null && !memberUpdateDTO.getPassword().isEmpty()) {
            // 비밀번호 암호화 후 업데이트
            String encryptedPassword = passwordEncoder.encode(memberUpdateDTO.getPassword());
            member.setPassword(encryptedPassword);
        }

        // 프로필 이미지나 기타 필드 업데이트 로직 추가 가능
        // memberRepository.save(member); // JPA의 변경 감지 기능으로 인해 save 호출 생략 가능
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}