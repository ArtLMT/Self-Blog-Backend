package com.lmt.selfblog.repository;

import com.lmt.selfblog.common.ContentStatus;
import com.lmt.selfblog.common.Visibility;
import com.lmt.selfblog.dto.response.TimelineItemDTO;
import com.lmt.selfblog.entity.Arc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArcRepository extends JpaRepository<Arc, UUID> {

    Optional<Arc> findBySlug(String slug);

    Optional<Arc> findBySlugAndVisibilityAndStatusIn(String slug, Visibility visibility, Collection<ContentStatus> statuses);

    List<Arc> findByVisibilityAndStatusInOrderByDisplayOrderAsc(Visibility visibility, Collection<ContentStatus> statuses);

    List<Arc> findAllByOrderByDisplayOrderAsc();

    long countByEndDateIsNull();

    @Query("""
        SELECT new com.lmt.selfblog.dto.response.TimelineItemDTO(
            at.title, a.slug, a.displayOrder,
            ct.title, c.slug, c.orderIndex,
            et.title, e.slug, e.orderIndex
        )
        FROM Arc a
        LEFT JOIN a.translations at ON at.language = :language
        LEFT JOIN a.chapters c ON c.status = com.lmt.selfblog.common.ContentStatus.PUBLISHED
        LEFT JOIN c.translations ct ON ct.language = :language
        LEFT JOIN c.episodes e ON e.status = com.lmt.selfblog.common.ContentStatus.PUBLISHED
        LEFT JOIN e.translations et ON et.language = :language
        WHERE a.visibility = com.lmt.selfblog.common.Visibility.PUBLIC
          AND a.status     = com.lmt.selfblog.common.ContentStatus.PUBLISHED
        ORDER BY a.displayOrder ASC, c.orderIndex ASC, e.orderIndex ASC
    """)
    List<TimelineItemDTO> findTimeline(@org.springframework.data.repository.query.Param("language") com.lmt.selfblog.common.Language language);
}
