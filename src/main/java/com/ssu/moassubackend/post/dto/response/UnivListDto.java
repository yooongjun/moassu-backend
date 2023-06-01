package com.ssu.moassubackend.post.dto.response;

import com.ssu.moassubackend.domain.post.Homepage;
import com.ssu.moassubackend.domain.post.Post;
import com.ssu.moassubackend.domain.post.Unipage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class UnivListDto {
    private Long id;
    private String category;
    private String title;
    private LocalDate date;
    private String link;

    public UnivListDto() {}

    @Builder
    public UnivListDto(Long id, String category, String title, LocalDate date, String link) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.date = date;
        this.link = link;
    }


    public UnivListDto(Unipage page) {
        this.id = page.getId();
        this.category = page.getField();
        this.title = page.getTitle();
        this.date = page.getWrite_date();
        this.link = page.getSsu_link();
    }
    public UnivListDto(Homepage page) {
        this.id = page.getId();
        this.category = page.getField();
        this.title = page.getTitle();
        this.date = page.getWrite_date();
        this.link = page.getSsu_link();
    }
}
