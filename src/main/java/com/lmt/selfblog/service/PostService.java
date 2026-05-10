package com.lmt.selfblog.service;

import com.lmt.selfblog.dto.PostRequestDTO;
import com.lmt.selfblog.dto.PostResponseDTO;

import java.util.List;

public interface PostService {
    List<PostResponseDTO> getAllPosts();
    List<PostResponseDTO> getPublishedPosts();
    PostResponseDTO getPostById(Long id);
    PostResponseDTO createPost(PostRequestDTO postRequestDTO);
    PostResponseDTO updatePost(Long id, PostRequestDTO postRequestDTO);
    void deletePost(Long id);
}
