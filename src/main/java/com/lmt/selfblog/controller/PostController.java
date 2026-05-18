package com.lmt.selfblog.controller;

import com.lmt.selfblog.dto.request.PostRequestDTO;
import com.lmt.selfblog.dto.response.ApiResponse;
import com.lmt.selfblog.dto.response.PostResponseDTO;
import com.lmt.selfblog.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "Posts", description = "Endpoints for managing blog posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    @Operation(summary = "Get published posts", description = "Returns all published blog posts (public)")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Posts retrieved successfully")
    })
    public ResponseEntity<ApiResponse<List<PostResponseDTO>>> getPublishedPosts() {
        return ResponseEntity.ok(ApiResponse.<List<PostResponseDTO>>builder()
                .success(true)
                .message("Posts retrieved successfully")
                .data(postService.getPublishedPosts())
                .build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get post by ID", description = "Returns a single post by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Post found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Post not found",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ResponseEntity<ApiResponse<PostResponseDTO>> getPostById(
            @Parameter(description = "ID of the post") @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<PostResponseDTO>builder()
                .success(true)
                .message("Post retrieved successfully")
                .data(postService.getPostById(id))
                .build());
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a post", description = "Creates a new blog post (requires authentication)")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Post created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<ApiResponse<PostResponseDTO>> createPost(
            @Valid @RequestBody PostRequestDTO postRequestDTO) {
        return new ResponseEntity<>(ApiResponse.<PostResponseDTO>builder()
                .success(true)
                .message("Post created successfully")
                .data(postService.createPost(postRequestDTO))
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a post", description = "Updates an existing blog post (requires authentication)")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Post updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Post not found")
    })
    public ResponseEntity<ApiResponse<PostResponseDTO>> updatePost(
            @Parameter(description = "ID of the post") @PathVariable Long id,
            @Valid @RequestBody PostRequestDTO postRequestDTO) {
        return ResponseEntity.ok(ApiResponse.<PostResponseDTO>builder()
                .success(true)
                .message("Post updated successfully")
                .data(postService.updatePost(id, postRequestDTO))
                .build());
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete a post", description = "Deletes a blog post by ID (requires authentication)")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Post deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Post not found")
    })
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @Parameter(description = "ID of the post") @PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Post deleted successfully")
                .data(null)
                .build());
    }

    @GetMapping("/admin/all")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get all posts (admin)", description = "Returns all posts including unpublished (requires authentication)")
    public ResponseEntity<ApiResponse<List<PostResponseDTO>>> getAllPosts() {
        return ResponseEntity.ok(ApiResponse.<List<PostResponseDTO>>builder()
                .success(true)
                .message("All posts retrieved successfully")
                .data(postService.getAllPosts())
                .build());
    }
}
