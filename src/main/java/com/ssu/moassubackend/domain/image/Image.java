package com.ssu.moassubackend.domain.image;

import com.ssu.moassubackend.domain.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(columnDefinition = "text")
    private String image_url;

    @Column(columnDefinition = "text")
    private String image_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Image(String image_url, String image_name) {
        this.image_name = image_name;
        this.image_url = image_url;
    }

    // 연관관계 편의 메서드
    public void changeRelationWithPost(Post post) {
        this.post = post;
        post.getImages().add(this);
    }

}
