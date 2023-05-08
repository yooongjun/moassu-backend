package com.ssu.moassubackend.domain.post;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("insta")
@Getter
public class Unipage extends Post {
    private String img_url;
    private String img_name;
}
