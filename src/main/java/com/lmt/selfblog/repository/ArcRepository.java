package com.lmt.selfblog.repository;

import com.lmt.selfblog.common.ContentStatus;
import com.lmt.selfblog.common.Visibility;
import com.lmt.selfblog.entity.Arc;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
