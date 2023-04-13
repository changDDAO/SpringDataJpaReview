package com.changddao.SpringDataJpa2.repository;

import com.changddao.SpringDataJpa2.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberJpaRepository {
    @PersistenceContext
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }
    public Member find(Long id){
        return em.find(Member.class, id);

    }
}
