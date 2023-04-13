package com.changddao.SpringDataJpa2.repository;

import com.changddao.SpringDataJpa2.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MemberRepository extends JpaRepository<Member,Long> {

}
