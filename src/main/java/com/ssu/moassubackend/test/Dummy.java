package com.ssu.moassubackend.test;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Dummy {
    private String daytime;
    private String name;

    public Dummy(LocalDateTime localDateTime, String name) {
        this.daytime = localDateTime.toString();
        this.name = name;
    }
}
