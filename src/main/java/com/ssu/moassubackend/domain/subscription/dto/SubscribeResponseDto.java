package com.ssu.moassubackend.domain.subscription.dto;

import com.ssu.moassubackend.domain.department.Department;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubscribeResponseDto {

    private List<Department> subscriptionList;

    public SubscribeResponseDto(List<Department> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }

}
