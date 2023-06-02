package com.ssu.moassubackend.post.dto.response;

import com.ssu.moassubackend.domain.post.Fun;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FunListDto {

    private Long id;
    private String title;
    private String url;
    private String thumbnail;
    private LocalDate applyStartDate;
    private LocalDate applyEndDate;
    private LocalDate operateStartDate;
    private LocalDate operateEndDate;

    @Builder
    public FunListDto(Long id, String title, String url, String thumbnail,
                      LocalDate applyStartDate, LocalDate applyEndDate, LocalDate operateStartDate, LocalDate operateEndDate) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnail = thumbnail;
        this.applyStartDate = applyStartDate;
        this.applyEndDate = applyEndDate;
        this.operateStartDate = operateStartDate;
        this.operateEndDate = operateEndDate;
    }

    public FunListDto(Fun page) {
        this.id = page.getId();
        this.title = page.getTitle();
        this.url = page.getSsu_link();
        this.thumbnail = page.getCover();
        this.applyStartDate = page.getApplyStartDate();
        this.applyEndDate = page.getApplyEndDate();
        this.operateStartDate = page.getOperateStartDate();
        this.operateEndDate = page.getOperateEndDate();
    }

}
