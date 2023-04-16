package com.changddao.SpringDataJpa2.repository;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.changddao.SpringDataJpa2.dto.MemberDto;
import com.changddao.SpringDataJpa2.entity.Member;
import com.changddao.SpringDataJpa2.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    
    @Autowired
    EntityManager em;
    @Test
    public void simpleTest() throws Exception {
        //given
        Member member = new Member("changHo");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();
        //when
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        //then

    }
    @Test
    @Transactional
    public void basicCRUD() throws Exception {
        //given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);



        //when
        //단건 조회
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        //list조회
        List<Member> members = memberRepository.findAll();
        //then
        assertThat(findMember1.getId()).isEqualTo(member1.getId());
        assertThat(findMember1.getUsername()).isEqualTo(member1.getUsername());
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        assertThat(members.size()).isEqualTo(2);
        assertThat(memberRepository.count()).isEqualTo(2);

        //삭제 검증하기
        memberRepository.delete(member1);
        assertThat(memberRepository.count()).isEqualTo(1);



    }

    @Test
    public void testQuery() throws Exception {
        //given
        Member member1 = new Member("member1",10);
        Member member2 = new Member("member2",23);
        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> findMember = memberRepository.findUser("member1", 10);
        assertThat(findMember).extracting("username").containsExactly("member1");

        //then

    }

    @Test
    public void usernameList() throws Exception {
        //given
        Member member1 = new Member("member1",10);
        Member member2 = new Member("member2",23);
        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<String> usernameList = memberRepository.findUsernameList();

        //then
        System.out.println("usernameList = " + usernameList);
    }

    @Test
    public void MemberDtoTest() throws Exception {
        //given
        Team teamA  = new Team("teamA");
        Team teamB  = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1",10,teamA);
        Member member2 = new Member("member2",23,teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<MemberDto> memberDto = memberRepository.findMemberDto();

        //then
        System.out.println(memberDto.get(0).getId());
        System.out.println(memberDto.get(0).getUsername());
        System.out.println(memberDto.get(0).getTeamName());

    }
    @Test
    public void paging() throws Exception {
        //given
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",10));
        memberRepository.save(new Member("member3",10));
        memberRepository.save(new Member("member4",10));
        memberRepository.save(new Member("member5",10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        //when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        //then
        List<Member> content = page.getContent();
        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();


    }
    @Test
    public void bulkTest(){
        //given
        Member member1  = new Member("member1",10);
        Member member2  = new Member("member2",15);
        Member member3  = new Member("member3",13);
        Member member4  = new Member("member4",20);
        Member member5  = new Member("member5",30);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);


        //when
        int result = memberRepository.bulkAgeUpdate(15);
        assertThat(result).isEqualTo(3);


        //then

    }
    @Test
    public void fetchJoinTest(){
    //given
    Team teamA = new Team("teamA");
    Team teamB = new Team("teamB");
    teamRepository.save(teamA);
    teamRepository.save(teamB);
        Member member1  = new Member("member1",10,teamA);
        Member member2  = new Member("member2",15,teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> members = memberRepository.memberFetchJoin();
        for (Member member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
            System.out.println("member.getTeam().getName()"+member.getTeam().getName());
        }



    //then

    }
    @Test
    public void customRepository(){
    //given
        Member member1  = new Member("member1",10);
        Member member2  = new Member("member2",15);
        Member member3  = new Member("member3",13);
        Member member4  = new Member("member4",20);
        Member member5  = new Member("member5",30);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);



    //when
        List<Member> members = memberRepository.findMemberCustom();


        //then
        for (Member member : members) {
            System.out.println("member = " + member);

        }

    }
    @Test
    public void JpaBaseEntityTest() throws Exception {
    //given
        Member member1 = new Member("changho Youn", 29);
        Member member2 = new Member("changho Youn", 29);
        
        memberRepository.save(member1);
        memberRepository.save(member2);
        
        //when
        Thread.sleep(1000);
        member2.setUsername("jina Kim");
        em.flush();
        em.clear();
        //then
        Member member = memberRepository.findById(member2.getId()).get();
        System.out.println("member.getUsername() = " + member.getUsername());
        System.out.println("member.getCreatedDate() = " + member.getCreatedDate());
        System.out.println("member.getCreatedDate() = " + member.getUpdatedDate());


    }

    }


