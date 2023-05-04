package com.ssu.moassubackend.domain.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    BEFORE, INPROGRESS, AFTER
}
