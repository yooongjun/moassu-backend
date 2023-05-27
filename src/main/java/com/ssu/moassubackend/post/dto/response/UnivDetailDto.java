package com.ssu.moassubackend.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter @Setter
public class UnivDetailDto {

    private Long id;
    private String url;
    private String title;
    private String category;
    private String date;
    private String content;
    private List attach;


    @Builder
    public UnivDetailDto(Long id, String url, String title, String category, String date, String content,
                           List attach) {
        this.url = url;
        this.title = title;
        this.category = category;
        this.date = date;
        this.content = content;
        this.attach = attach;
        this.id = id;
    }

}
