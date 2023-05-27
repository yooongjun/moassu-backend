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
@DiscriminatorValue("uni")
@Getter
public class Unipage extends Post {

    @Column(columnDefinition = "text")
    private String ssu_link;



    public Unipage(String title, String content, LocalDate write_date, String field, String ssu_link) {
        super(title, content, write_date, field);
        this.ssu_link = ssu_link;
    }

}
