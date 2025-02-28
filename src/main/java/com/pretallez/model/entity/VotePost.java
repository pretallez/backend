package com.pretallez.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "VotePost")
public class VotePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "board_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @JoinColumn(name = "fencing_club_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private FencingClub fencingClub;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @Column(name = "min_capacity", nullable = false)
    private Integer minCapacity;

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Column(name = "training_date", nullable = false)
    private LocalDateTime trainingDate;

    private VotePost(Board board, FencingClub fencingClub, Integer maxCapacity, Integer minCapacity, Integer totalAmount, LocalDateTime trainingDate) {
        this.board = board;
        this.fencingClub = fencingClub;
        this.maxCapacity = maxCapacity;
        this.minCapacity = minCapacity;
        this.totalAmount = totalAmount;
        this.trainingDate = trainingDate;
    }

    public static VotePost of(Board board, FencingClub fencingClub, Integer maxCapacity, Integer minCapacity, Integer totalAmount, LocalDateTime trainingDate) {
        return new VotePost(board, fencingClub, maxCapacity, minCapacity, totalAmount, trainingDate);
    }
}
