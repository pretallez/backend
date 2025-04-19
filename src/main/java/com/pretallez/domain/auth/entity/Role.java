package com.pretallez.domain.auth.entity;

import com.pretallez.domain.auth.enums.MemberRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    private Role(MemberRole memberRole) {
        this.memberRole = memberRole;
    }

    public static Role of(MemberRole memberRole) {
        return new Role(memberRole);
    }

}
