package com.lmt.selfblog.entity;

import com.lmt.selfblog.common.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "episode_translations", indexes = {
    @Index(name = "idx_episode_trans_episode_id", columnList = "episode_id"),
    @Index(name = "idx_episode_trans_lang", columnList = "language")
})
@Getter
@Setter
public class EpisodeTranslation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "episode_id", nullable = false)
    private Episode episode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Language language;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String markdownContent;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String renderedContent;

    @Column(length = 1000)
    private String conclusion;
}
