package com.ssu.moassubackend.domain.post;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@DiscriminatorValue("insta")
@Getter
public class Instagram extends Post {

    @Column(columnDefinition = "text")
    private String thumbnail;

    @Column(columnDefinition = "text")
    private String url;

    public Instagram(String admin, String url, String title, String content, LocalDate date) {
        super(title, content, date, admin);
        this.url = url;
    }

}
