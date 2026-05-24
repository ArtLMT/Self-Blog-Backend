package com.lmt.selfblog.entity;

import com.lmt.selfblog.common.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "margin_note_translations", indexes = {
    @Index(name = "idx_note_trans_note_id", columnList = "margin_note_id"),
    @Index(name = "idx_note_trans_lang", columnList = "language")
})
@Getter
@Setter
public class MarginNoteTranslation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "margin_note_id", nullable = false)
    private MarginNote marginNote;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Language language;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String noteContent;
}
