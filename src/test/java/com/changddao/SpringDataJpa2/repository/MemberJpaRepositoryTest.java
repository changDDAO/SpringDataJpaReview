package com.changddao.SpringDataJpa2.repository;

import com.changddao.SpringDataJpa2.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
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

    @Test
    public void paging() throws Exception {
        //given
        memberJpaRepository.save(new Member("member1",10));
        memberJpaRepository.save(new Member("member2",10));
        memberJpaRepository.save(new Member("member3",10));
        memberJpaRepository.save(new Member("member4",10));
        memberJpaRepository.save(new Member("member5",10));
        int age =10;
        int offset = 0;
        int limit = 3;
        //when
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        //then
        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(5);

    }


    @Test
    public void bulkTest(){
    //given
        Member member1  = new Member("member1",10);
        Member member2  = new Member("member2",15);
        Member member3  = new Member("member3",13);
        Member member4  = new Member("member4",20);
        Member member5  = new Member("member5",30);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        memberJpaRepository.save(member3);
        memberJpaRepository.save(member4);
        memberJpaRepository.save(member5);


    //when
        int result = memberJpaRepository.bulkUpdate(15);
        assertThat(result).isEqualTo(3);


        //then

    }

}