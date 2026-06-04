package com.lmt.selfblog.repository;

import com.lmt.selfblog.common.ContentStatus;
import com.lmt.selfblog.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, UUID> {

    Optional<Chapter> findBySlug(String slug);

    Optional<Chapter> findBySlugAndStatusIn(String slug, Collection<ContentStatus> statuses);

    List<Chapter> findByArcIdOrderByOrderIndexAsc(UUID arcId);

    List<Chapter> findByArcSlugAndStatusInOrderByOrderIndexAsc(String arcSlug, Collection<ContentStatus> statuses);

    long countByStatus(ContentStatus status);
}
