package com.pretallez.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "VotePost")
public class VotePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Column(name = "fencing_club_id", nullable = false)
    private Long fencingClubId;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @Column(name = "min_capacity", nullable = false)
    private Integer minCapacity;

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Column(name = "training_date", nullable = false)
    private Timestamp trainingDate;

}
