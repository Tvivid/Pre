package Service;

import Entity.Member;
import Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MailService mailService;


    public void registerUser(Member member) {
        // 사용자 정보를 저장하고 인증 이메일을 전송
        memberRepository.save(member);

        String subject = "회원가입 인증";
        String text = "회원가입을 완료하려면 아래 링크를 클릭하세요: http://your-app-url/confirm?token=" + member.getVerificationToken();
        mailService.sendEmail(member.getEmail(), subject, text);
    }

    /**
     * 회원가입
     */
    @Transactional //변경
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
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