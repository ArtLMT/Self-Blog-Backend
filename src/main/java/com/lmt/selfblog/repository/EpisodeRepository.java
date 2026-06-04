package com.lmt.selfblog.repository;

import com.lmt.selfblog.common.ContentStatus;
import com.lmt.selfblog.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, UUID> {

    Optional<Episode> findBySlug(String slug);

    Optional<Episode> findBySlugAndStatusIn(String slug, Collection<ContentStatus> statuses);

    List<Episode> findByChapterIdOrderByOrderIndexAsc(UUID chapterId);

    List<Episode> findByChapterSlugAndStatusInOrderByOrderIndexAsc(String chapterSlug, Collection<ContentStatus> statuses);

    long countByStatus(ContentStatus status);

    List<Episode> findTop5ByOrderByUpdatedAtDesc();
}
