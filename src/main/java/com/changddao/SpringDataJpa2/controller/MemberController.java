package com.changddao.SpringDataJpa2.controller;

import com.changddao.SpringDataJpa2.dto.MemberDto;
import com.changddao.SpringDataJpa2.entity.Member;
import com.changddao.SpringDataJpa2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }
    //내부 entity를 외부로 바로 노출하는건 절대안된다. 꼭 dto로 바꿔서 내보내자
    //여러가지 이유가있겠지만 혼자 일을하는것도 아니고 같이 협업을 하는데 entity수정을 했을때 api 스펙 자체가 바뀌기때문에
    //곤란한 상황들이 생길 수 있다.
    @GetMapping("/members")
    public Page<MemberDto> list(Pageable pageable){
        Page<Member> page = memberRepository.findAll(pageable);
        Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
        return map;
    }

    @PostConstruct
    public void init(){

        for(int i =0; i<100;i++){
            memberRepository.save(new Member("user"+i,i));
        }
    }
}
