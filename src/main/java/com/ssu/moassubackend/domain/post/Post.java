package com.ssu.moassubackend.domain.post;

import com.ssu.moassubackend.domain.comment.Comment;
import com.ssu.moassubackend.domain.image.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@DiscriminatorColumn(name = "type")
public abstract class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "text")
    private String content;

    private String major; // 학부 이름

    private LocalDate writeDate; // 작성일

    private String field; // 분야

    @Enumerated(value = EnumType.STRING)
    private Status status; // 진행 상태

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Image> images = new ArrayList<>();

    private LocalDate applyStartDate;
    private LocalDate applyEndDate;
    private LocalDate operateStartDate;
    private LocalDate operateEndDate;

    public Post(String title, String content, LocalDate write_date, String field, String admin) {
        this.title = title;
        this.content = content;
        this.writeDate = write_date;
        this.field = field;
        this.major = admin;
    }

    public Post(String title, String content, LocalDate write_date, String admin) {
        this.title = title;
        this.content = content;
        this.writeDate = write_date;
        this.major = admin;
    }

    public Post(String title, String admin, String category, String content) {
        this.title = title;
        this.major = admin;
        this.field = category;
        this.content = content;
    }

    public Post(String major) {
        this.major = major;
    }

    public Post(String title, String admin, String category, String content,
                LocalDate applyStartDate, LocalDate applyEndDate, LocalDate operateStartDate, LocalDate operateEndDate) {
        this.title = title;
        this.major = admin;
        this.field = category;
        this.content = content;
        this.applyStartDate = applyStartDate;
        this.applyEndDate = applyEndDate;
        this.operateStartDate = operateStartDate;
        this.operateEndDate = operateEndDate;
    }

}
