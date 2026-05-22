package com.lmt.selfblog.entity;

import com.lmt.selfblog.common.ContentStatus;
import com.lmt.selfblog.common.Visibility;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "arcs", indexes = {
    @Index(name = "idx_arcs_slug", columnList = "slug", unique = true),
    @Index(name = "idx_arcs_status", columnList = "status"),
    @Index(name = "idx_arcs_display_order", columnList = "display_order")
})
@Getter
@Setter
public class Arc extends AuditableEntity {

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "slug", nullable = false, unique = true, length = 255)
    private String slug;

    @Column(name = "summary", length = 1000)
    private String summary;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false, length = 50)
    private Visibility visibility;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private ContentStatus status;

    @OneToMany(mappedBy = "arc", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OrderBy("orderIndex ASC")
    private Set<Chapter> chapters = new LinkedHashSet<>();

    public void addChapter(Chapter chapter) {
        chapters.add(chapter);
        chapter.setArc(this);
    }

    public void removeChapter(Chapter chapter) {
        chapters.remove(chapter);
        chapter.setArc(null);
    }
}