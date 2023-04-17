package com.changddao.SpringDataJpa2.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","username","age"})
public class Member extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String name) {
        this.username = name;
    }

    public Member(String name, int age, Team team) {
        this.username = name;
        this.age = age;
        this.team = team;
        if(team!=null){
            changeTeam(team);
        }

    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }


}
