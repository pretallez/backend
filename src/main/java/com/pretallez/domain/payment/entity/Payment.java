package com.pretallez.domain.payment.entity;

import java.time.LocalDateTime;

import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.payment.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String paymentKey;

    private String orderId;

    private String method;

    private Long amount;

    private LocalDateTime approvedAt;

    private String receiptUrl;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private Payment(Member member, String paymentKey, String orderId, String method, Long amount, LocalDateTime approvedAt,String receiptUrl, PaymentStatus status) {
        this.member = member;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.method = method;
        this.amount = amount;
        this.approvedAt = approvedAt;
        this.receiptUrl = receiptUrl;
        this.status = status;
    }

    public static Payment complete(Member member, String paymentKey, String orderId, String method, Long amount, LocalDateTime approvedAt, String receiptUrl) {
        return new Payment(
            member,
            paymentKey,
            orderId,
            method,
            amount,
            approvedAt,
            receiptUrl,
            PaymentStatus.DONE
        );
    }
}