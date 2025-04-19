package com.pretallez.domain.posting.entity;

import com.pretallez.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PostMember")
public class PostMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false, unique = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "votepost_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private VotePost votePost;

    @Column(name = "paid_points", nullable = false)
    private Integer paidPoints;

}
