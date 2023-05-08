package com.ssu.moassubackend.scrap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UniResponseDto {
    private String url;
    private String title;
    private String category;
    private String date;
    private String content;
    private Map<String, String> attach;
}
