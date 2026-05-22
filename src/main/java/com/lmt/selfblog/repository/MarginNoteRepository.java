package com.lmt.selfblog.repository;

import com.lmt.selfblog.common.Visibility;
import com.lmt.selfblog.entity.MarginNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface MarginNoteRepository extends JpaRepository<MarginNote, UUID> {

    List<MarginNote> findByEpisodeId(UUID episodeId);

    List<MarginNote> findByEpisodeSlugAndVisibilityIn(String episodeSlug, Collection<Visibility> visibilities);
}
