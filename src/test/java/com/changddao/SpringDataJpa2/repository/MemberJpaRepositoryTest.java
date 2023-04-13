package com.changddao.SpringDataJpa2.repository;

import com.changddao.SpringDataJpa2.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

}