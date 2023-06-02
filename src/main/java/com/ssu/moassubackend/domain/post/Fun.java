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

    @Column(columnDefinition = "text")
    private String ssu_link;

    @Column(columnDefinition = "text")
    private String cover;

    public Fun(String title, String admin, String ssu_link, String category, String content, String cover,
               LocalDate applyStartDate, LocalDate applyEndDate, LocalDate operateStartDate, LocalDate operateEndDate) {
        super(title, admin, category, content, applyStartDate, applyEndDate, operateStartDate, operateEndDate);
        this.ssu_link = ssu_link;
        this.cover = cover;
    }

}
