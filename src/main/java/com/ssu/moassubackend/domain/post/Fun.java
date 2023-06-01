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
@DiscriminatorValue("fun")
@Getter
public class Fun extends Post {
    private LocalDate recruit_start_date;
    private LocalDate recruit_end_date;

    @Column(columnDefinition = "text")
    private String ssu_link;

    @Column(columnDefinition = "text")
    private String cover;

    public Fun(String title, String admin, String ssu_link, String category,
               String content, String cover) {
        super(title, admin, category, content);
        this.ssu_link = ssu_link;
        this.cover = cover;
    }

}
