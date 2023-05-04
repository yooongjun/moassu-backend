package com.ssu.moassubackend.domain.post;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("insta")
@Getter
public class Instagram extends Post {

    private LocalDate recruit_start_date;
    private LocalDate recruit_end_date;

}
