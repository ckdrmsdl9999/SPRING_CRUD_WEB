package project.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import project.Repository.MemberRepository;
import project.domain.Member;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원_가입(){
        //given
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Member member = Member.builder()
                .username("ㅇㄹ")
                .password("1234")
                .email("")
                .build();

        Set<ConstraintViolation<Member>> violations = validator.validate(member);
        for (ConstraintViolation<Member> violation : violations) {
            System.out.println("violation=" + violation);
            System.out.println("violation.message=" + violation.getMessage());
        }

        //when
        Long joinId = memberService.join(member);
        //then
        Assertions.assertEquals(member, memberRepository.findOne(joinId));
    }

    @Test
    void 중복_회원_가입 (){
        //given
        Member member = Member.builder()
                .username("min")
                .password("1234")
                .email("email")
                .build();

        Member member2 = Member.builder()
                .username("min")
                .password("1234")
                .email("email")
                .build();
        //when
        memberService.join(member);
        memberService.join(member2);
        //then
//        Assertions.assertEquals(member, member2);
        assertThrows(IllegalStateException.class , () -> memberService.join(member2));
    }


}