package com.lmt.selfblog.entity;

import com.lmt.selfblog.common.ContentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "episodes", indexes = {
    @Index(name = "idx_episodes_chapter_id", columnList = "chapter_id"),
    @Index(name = "idx_episodes_slug", columnList = "slug", unique = true),
    @Index(name = "idx_episodes_status", columnList = "status"),
    @Index(name = "idx_episodes_event_date", columnList = "event_date"),
    @Index(name = "idx_episodes_order_index", columnList = "chapter_id, order_index")
})
@Getter
@Setter
public class Episode extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @Column(name = "slug", nullable = false, unique = true, length = 255)
    private String slug;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @Column(name = "event_date", nullable = false)
    private Instant eventDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private ContentStatus status;

    @OneToMany(mappedBy = "episode", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<MarginNote> marginNotes = new LinkedHashSet<>();

    public void addMarginNote(MarginNote note) {
        marginNotes.add(note);
        note.setEpisode(this);
    }

    public void removeMarginNote(MarginNote note) {
        marginNotes.remove(note);
        note.setEpisode(null);
    }

    @OneToMany(mappedBy = "episode", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EpisodeTranslation> translations = new LinkedHashSet<>();

    public void addTranslation(EpisodeTranslation translation) {
        translations.add(translation);
        translation.setEpisode(this);
    }

    public void removeTranslation(EpisodeTranslation translation) {
        translations.remove(translation);
        translation.setEpisode(null);
    }
}