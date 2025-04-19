package com.pretallez.domain.payment.entity;

import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "status", nullable = false, length = 30)
    private PaymentStatus status;

    @Column(name = "paid_amount", nullable = false)
    private Integer paidAmount;

}
