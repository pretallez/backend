package com.pretallez.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FencingClub")
public class FencingClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //todo : SABRE, FOIL, EPEE 가 SET으로 들어가야 함
    @Column(name = "type", nullable = false, length = 20)
    private String type;

    @Column(name = "contact", nullable = false, length = 20)
    private String contact;

    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "gear_exist_yn", length = 1, nullable = false)
    private String gearExist; // 'Y' -> true, 'N' -> false

}
