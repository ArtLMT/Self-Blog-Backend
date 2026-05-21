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

    @Column(name = "note_content", nullable = false, columnDefinition = "TEXT")
    private String noteContent;

    @Column(name = "anchor_position", nullable = false, length = 255)
    private String anchorPosition;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false, length = 50)
    private Visibility visibility;
}
