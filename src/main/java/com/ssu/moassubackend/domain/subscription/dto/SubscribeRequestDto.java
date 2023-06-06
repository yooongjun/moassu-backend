package com.ssu.moassubackend.domain.subscription.dto;

import com.ssu.moassubackend.domain.department.Department;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

// 구독 요청 DTO
@Data
@ToString(exclude = "keywordList")
public class SubscribeRequestDto {
    private List<String> keywordList;
    private String email;

    public SubscribeRequestDto(List<String> keywordList, String email) {
        this.keywordList = keywordList;
        this.email = email;
    }

    public SubscribeRequestDto() {
    }

}
