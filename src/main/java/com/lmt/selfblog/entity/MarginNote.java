package com.lmt.selfblog.entity;

import com.lmt.selfblog.common.Visibility;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "margin_notes", indexes = {
    @Index(name = "idx_margin_notes_episode_id", columnList = "episode_id")
})
@Getter
@Setter
public class MarginNote extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "episode_id", nullable = false)
    private Episode episode;

    @Column(name = "anchor_position", nullable = false, length = 255)
    private String anchorPosition;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false, length = 50)
    private Visibility visibility;

    @OneToMany(mappedBy = "marginNote", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.Set<MarginNoteTranslation> translations = new java.util.LinkedHashSet<>();

    public void addTranslation(MarginNoteTranslation translation) {
        translations.add(translation);
        translation.setMarginNote(this);
    }

    public void removeTranslation(MarginNoteTranslation translation) {
        translations.remove(translation);
        translation.setMarginNote(null);
    }
}
