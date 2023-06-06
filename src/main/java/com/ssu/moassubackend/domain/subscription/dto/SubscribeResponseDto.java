package com.ssu.moassubackend.domain.subscription.dto;

import com.ssu.moassubackend.domain.department.Department;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubscribeResponseDto {

    private List<String> subscriptionList;

    public SubscribeResponseDto(List<String> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }

}
