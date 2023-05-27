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
@DiscriminatorValue("homepage")
@Getter
public class Homepage extends Post {

    private LocalDate recruit_start_date;
    private LocalDate recruit_end_date;

    @Column(columnDefinition = "text")
    private String ssu_link;

    // computer
    public Homepage(String title, String content, LocalDate write_date, String field, String ssu_link, String admin) {
        super(title, content, write_date, field, admin);
        this.ssu_link = ssu_link;
    }

    // electronic
    public  Homepage(String title, String content, LocalDate write_date, String admin, String ssu_link) {
        super(title, content, write_date, admin);
        this.ssu_link = ssu_link;
    }


}
