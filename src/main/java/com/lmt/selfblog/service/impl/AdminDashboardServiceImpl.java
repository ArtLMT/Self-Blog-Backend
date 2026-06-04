package com.lmt.selfblog.service.impl;

import com.lmt.selfblog.common.ContentStatus;
import com.lmt.selfblog.common.Language;
import com.lmt.selfblog.common.LanguageResolver;
import com.lmt.selfblog.dto.response.AdminWorkspaceMetricsDTO;
import com.lmt.selfblog.dto.response.RecentlyEditedEpisodeDTO;
import com.lmt.selfblog.entity.EpisodeTranslation;
import com.lmt.selfblog.repository.ArcRepository;
import com.lmt.selfblog.repository.ChapterRepository;
import com.lmt.selfblog.repository.EpisodeRepository;
import com.lmt.selfblog.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final ArcRepository arcRepository;
    private final ChapterRepository chapterRepository;
    private final EpisodeRepository episodeRepository;
    private final LanguageResolver languageResolver;

    @Override
    public AdminWorkspaceMetricsDTO getMetrics() {
        AdminWorkspaceMetricsDTO metrics = new AdminWorkspaceMetricsDTO();

        long draftsCount = chapterRepository.countByStatus(ContentStatus.DRAFT) +
                           episodeRepository.countByStatus(ContentStatus.DRAFT);
        
        long publishedChaptersCount = chapterRepository.countByStatus(ContentStatus.PUBLISHED);
        
        long unfinishedArcsCount = arcRepository.countByEndDateIsNull();

        Language lang = languageResolver.resolveLanguage();

        List<RecentlyEditedEpisodeDTO> recentlyEdited = episodeRepository.findTop5ByOrderByUpdatedAtDesc()
                .stream()
                .map(episode -> {
                    RecentlyEditedEpisodeDTO dto = new RecentlyEditedEpisodeDTO();
                    dto.setId(episode.getId());
                    dto.setStatus(episode.getStatus());
                    dto.setUpdatedAt(episode.getUpdatedAt());
                    
                    EpisodeTranslation translation = episode.getTranslations().stream()
                            .filter(t -> t.getLanguage() == lang)
                            .findFirst()
                            .orElseGet(() -> episode.getTranslations().stream().findFirst().orElse(null));
                            
                    if (translation != null) {
                        dto.setTitle(translation.getTitle());
                        String content = translation.getMarkdownContent();
                        if (content != null) {
                            dto.setContentSnippet(content.length() > 100 ? content.substring(0, 100) + "..." : content);
                        }
                    }
                    return dto;
                })
                .collect(Collectors.toList());

        metrics.setDraftsCount(draftsCount);
        metrics.setPublishedChaptersCount(publishedChaptersCount);
        metrics.setUnfinishedArcsCount(unfinishedArcsCount);
        metrics.setRecentlyEditedEpisodes(recentlyEdited);

        return metrics;
    }
}
