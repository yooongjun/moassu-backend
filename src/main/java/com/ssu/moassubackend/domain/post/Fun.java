package com.ssu.moassubackend.domain.post;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("fun")
@Getter
public class Fun extends Post {
    private LocalDate recruit_start_date;
    private LocalDate recruit_end_date;
}
