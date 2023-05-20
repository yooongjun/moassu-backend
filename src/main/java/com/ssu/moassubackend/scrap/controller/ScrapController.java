package com.ssu.moassubackend.scrap.controller;

import com.ssu.moassubackend.scrap.dto.HomepageUnivDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ScrapController {

    @PostMapping("/savedata")
    public void savedata(@RequestBody HomepageUnivDto[] homepageUnivDtos) {
        int cnt = 0;
        for (HomepageUnivDto dto : homepageUnivDtos) {
            System.out.println("cnt = " + cnt);
            cnt++;
            String url = dto.getUrl();
            String title = dto.getTitle();
            String admin = dto.getAdmin();
            String date = dto.getDate();
            String category = dto.getCategory();
            Map<String, String> attach = dto.getAttach();
            System.out.println("title = " + title);
            System.out.println("url = " + url);
            System.out.println("admin = " + admin);
            System.out.println("category = " + category);
            System.out.println("date = " + date);
            for (Map.Entry<String, String> att : attach.entrySet()) {
                String key = att.getKey();
                String value = att.getValue();
                System.out.println("key = " + key);
                System.out.println("value = " + value);
            }

            System.out.println("================================");
        }
    }

}
