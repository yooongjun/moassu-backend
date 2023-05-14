package com.ssu.moassubackend.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
//@EnableScheduling
public class TestController {

    private LocalDateTime todayTime;
    private final SseEmitters sseEmitters;
//    private List<String> list = new ArrayList<>();

    /**
     * 연결 테스트 Api
     * @return
     */
    @GetMapping("/test")
    public ResponseEntity test() {
        Dummy dummy = new Dummy(LocalDateTime.now(), "이름1");
        log.info("dummy.daytime = {}", dummy.getDaytime());
        log.info("dummy.name = {}", dummy.getName());
        return ResponseEntity.ok(dummy);
    }

    @GetMapping("/user/detail")
    public ResponseEntity<?> userDetails() {
        return ResponseEntity.ok("user detail page");
    }

// 주기적으로 보내기 테스트 코드

//    @Scheduled(fixedRate = 10000)
//    @GetMapping("/test2")
    public ResponseEntity sendTestContent() {

//        TodayDum td = new TodayDum();
//        td.todayTime = LocalDateTime.now();
//        String result = td.todayTime.toString();
        log.info("today is = {}", todayTime);
        return ResponseEntity.ok(todayTime);
    }


//    @Scheduled(fixedRate = 10000)
    public void setTime() {
        todayTime = LocalDateTime.now();
        log.info("change time = {}", todayTime);
    }

//    @GetMapping(value = "/test3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect() {
        SseEmitter emitter = new SseEmitter(10000L);

        sseEmitters.add(emitter);
        try{
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected!"));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(emitter);
    }

//    @GetMapping("/count")
    public ResponseEntity<Void> count() {
        sseEmitters.count();
        return ResponseEntity.ok().build();
    }


}
