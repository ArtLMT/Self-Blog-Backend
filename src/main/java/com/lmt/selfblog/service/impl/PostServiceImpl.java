package com.lmt.selfblog.service.impl;

import com.lmt.selfblog.dto.request.PostRequestDTO;
import com.lmt.selfblog.dto.response.PostResponseDTO;
import com.lmt.selfblog.entity.Post;
import com.lmt.selfblog.repository.PostRepository;
import com.lmt.selfblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponseDTO> getPublishedPosts() {
        return postRepository.findByPublishedTrue().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostResponseDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        return mapToDTO(post);
    }

    @Override
    public PostResponseDTO createPost(PostRequestDTO postRequestDTO) {
        Post post = Post.builder()
                .titleEn(postRequestDTO.getTitleEn())
                .titleVi(postRequestDTO.getTitleVi())
                .contentEn(postRequestDTO.getContentEn())
                .contentVi(postRequestDTO.getContentVi())
                .published(postRequestDTO.isPublished())
                .build();
        
        Post savedPost = postRepository.save(post);
        return mapToDTO(savedPost);
    }

    @Override
    public PostResponseDTO updatePost(Long id, PostRequestDTO postRequestDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        
        post.setTitleEn(postRequestDTO.getTitleEn());
        post.setTitleVi(postRequestDTO.getTitleVi());
        post.setContentEn(postRequestDTO.getContentEn());
        post.setContentVi(postRequestDTO.getContentVi());
        post.setPublished(postRequestDTO.isPublished());
        
        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    private PostResponseDTO mapToDTO(Post post) {
        return PostResponseDTO.builder()
                .id(post.getId())
                .titleEn(post.getTitleEn())
                .titleVi(post.getTitleVi())
                .contentEn(post.getContentEn())
                .contentVi(post.getContentVi())
                .published(post.isPublished())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
