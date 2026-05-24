package com.lmt.selfblog.entity;

import com.lmt.selfblog.common.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "chapter_translations", indexes = {
    @Index(name = "idx_chapter_trans_chapter_id", columnList = "chapter_id"),
    @Index(name = "idx_chapter_trans_lang", columnList = "language")
})
@Getter
@Setter
public class ChapterTranslation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Language language;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 500)
    private String quote;

    @Column(length = 1000)
    private String summary;
}
