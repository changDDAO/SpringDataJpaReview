package com.changddao.SpringDataJpa2.repository;

import com.changddao.SpringDataJpa2.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom{
    List<Member> findMemberCustom();
}
