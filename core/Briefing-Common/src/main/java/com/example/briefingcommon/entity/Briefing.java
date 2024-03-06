package com.example.briefingcommon.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.briefingcommon.entity.enums.BriefingType;
import com.example.briefingcommon.entity.enums.GptModel;
import com.example.briefingcommon.entity.enums.TimeOfDay;
import jakarta.persistence.*;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Briefing extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BriefingType type;

    @Column(nullable = false)
    private Integer ranks;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false, length = 1000)
    private String content;

    @Builder.Default
    @OneToMany(mappedBy = "briefing", fetch = FetchType.LAZY)
    private List<BriefingArticle> briefingArticles = new ArrayList<>();

    @Builder.Default @Transient private Integer scrapCount = 0;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TimeOfDay timeOfDay = TimeOfDay.MORNING;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private GptModel gptModel = GptModel.GPT_3_5_TURBO;

    @ColumnDefault("0")
    @Builder.Default
    private int viewCount = 0;

    public void setScrapCount(Integer scrapCount) {
        this.scrapCount = scrapCount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void updateBriefing(String title, String subtitle, String content) {
        Optional.ofNullable(title).ifPresent(this::setTitle);
        Optional.ofNullable(subtitle).ifPresent(this::setSubtitle);
        Optional.ofNullable(content).ifPresent(this::setContent);
    }
}
