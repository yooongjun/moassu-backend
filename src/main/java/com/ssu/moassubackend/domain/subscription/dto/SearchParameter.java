package com.ssu.moassubackend.domain.subscription.dto;

import com.ssu.moassubackend.domain.post.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchParameter {

    private String title;
    private String content;
    private String major;

    @Builder
    public SearchParameter(String title, String content, String major) {
        this.title = title;
        this.content = content;
        this.major = major;
    }

}
