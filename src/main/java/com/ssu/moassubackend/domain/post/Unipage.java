package com.ssu.moassubackend.domain.post;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@DiscriminatorValue("insta")
@Getter
public class Unipage extends Post {

    @Column(columnDefinition = "text")
    private String img_url;

    @Column(columnDefinition = "text")
    private String img_name;

    public Unipage(String title, String content, LocalDate write_date, String field, String img_url, String img_name) {
        super(title, content, write_date, field);
        this.img_url = img_url;
        this.img_name = img_name;
    }

}
