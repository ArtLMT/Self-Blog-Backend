package com.lmt.selfblog.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titleEn;

    @Column(nullable = false)
    private String titleVi;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contentEn;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contentVi;

    @Builder.Default
    private boolean published = false;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
