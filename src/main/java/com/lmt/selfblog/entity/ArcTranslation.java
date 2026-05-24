package com.lmt.selfblog.entity;

import com.lmt.selfblog.common.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "arc_translations", indexes = {
    @Index(name = "idx_arc_trans_arc_id", columnList = "arc_id"),
    @Index(name = "idx_arc_trans_lang", columnList = "language")
})
@Getter
@Setter
public class ArcTranslation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "arc_id", nullable = false)
    private Arc arc;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Language language;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 1000)
    private String summary;
}
