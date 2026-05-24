package com.lmt.selfblog.entity;

import com.lmt.selfblog.common.ContentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "chapters", indexes = {
    @Index(name = "idx_chapters_arc_id", columnList = "arc_id"),
    @Index(name = "idx_chapters_slug", columnList = "slug", unique = true),
    @Index(name = "idx_chapters_status", columnList = "status"),
    @Index(name = "idx_chapters_order_index", columnList = "arc_id, order_index")
})
@Getter
@Setter
public class Chapter extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "arc_id", nullable = false)
    private Arc arc;

    @Column(name = "slug", nullable = false, unique = true, length = 255)
    private String slug;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private ContentStatus status;

    @Column(name = "reading_time_minutes")
    private Integer readingTimeMinutes;

    @Column(name = "published_at")
    private Instant publishedAt;

    @OneToMany(mappedBy = "chapter", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OrderBy("orderIndex ASC")
    private Set<Episode> episodes = new LinkedHashSet<>();

    public void addEpisode(Episode episode) {
        episodes.add(episode);
        episode.setChapter(this);
    }

    public void removeEpisode(Episode episode) {
        episodes.remove(episode);
        episode.setChapter(null);
    }

    // Business rule: Can only publish if at least 3 episodes exist
    public boolean canPublish() {
        return episodes.size() >= 3;
    }

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChapterTranslation> translations = new LinkedHashSet<>();

    public void addTranslation(ChapterTranslation translation) {
        translations.add(translation);
        translation.setChapter(this);
    }

    public void removeTranslation(ChapterTranslation translation) {
        translations.remove(translation);
        translation.setChapter(null);
    }
}
