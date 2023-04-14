package com.changddao.SpringDataJpa2.repository;

import com.changddao.SpringDataJpa2.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
