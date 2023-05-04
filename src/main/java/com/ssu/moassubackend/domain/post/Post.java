package com.ssu.moassubackend.domain.post;

import com.ssu.moassubackend.domain.comment.Comment;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@DiscriminatorColumn(name = "type")
public abstract class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String major; // 학부 이름

    private LocalDateTime write_date; // 작성일

    private String field; // 분야

    @Enumerated(value = EnumType.STRING)
    private Status status; // 진행 상태

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

}
