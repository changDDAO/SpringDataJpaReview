package com.changddao.SpringDataJpa2.repository;

import com.changddao.SpringDataJpa2.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberJpaRepositoryTest {
    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    @Transactional
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember1 = memberJpaRepository.save(member);
        memberJpaRepository.find(savedMember1.getId());

        assertThat(savedMember1.getUsername()).isEqualTo("memberA");


    }
    @Test
    @Transactional
    public void basicCRUD() throws Exception {
        //given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);



        //when
        //단건 조회
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
        //list조회
        List<Member> members = memberJpaRepository.findAll();
        //then
        assertThat(findMember1.getId()).isEqualTo(member1.getId());
        assertThat(findMember1.getUsername()).isEqualTo(member1.getUsername());
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        assertThat(members.size()).isEqualTo(2);
        assertThat(memberJpaRepository.count()).isEqualTo(2);

        //삭제 검증하기
        memberJpaRepository.delete(member1);
        assertThat(memberJpaRepository.count()).isEqualTo(1);



    }

}