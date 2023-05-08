package com.ssu.moassubackend.domain.post;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@DiscriminatorValue("insta")
@Getter
public class Unipage extends Post {
    private String img_url;
    private String img_name;

    public Unipage(String title, String content, LocalDateTime write_date, String field, String img_url, String img_name) {
        super(title, content, write_date, field);
        this.img_url = img_url;
        this.img_name = img_name;
    }

}
