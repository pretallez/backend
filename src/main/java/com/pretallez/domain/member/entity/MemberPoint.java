package com.pretallez.domain.member.entity;

import com.pretallez.domain.member.enums.PointType;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "MemberPoint")
public class MemberPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "point_type", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private PointType pointType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "point_amount", nullable = false)
    private Integer pointAmount;

}
