package com.example.briefingcommon.entity;

import com.example.briefingcommon.entity.enums.SubscriptionStatus;
import com.example.briefingcommon.entity.enums.SubscriptionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Subscription extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private SubscriptionType type;

    @Setter
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @Setter
    private LocalDateTime expiryDate;

}
