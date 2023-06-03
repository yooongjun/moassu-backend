package com.ssu.moassubackend.post;

import com.ssu.moassubackend.post.dto.response.*;
import com.ssu.moassubackend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping("/list/univ")
    public ResponseEntity listUniv(@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        List<UnivListDto> univList = postService.getUnivList(pageable);
        String totalPages = postService.getTotalPagesUniv(pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("totalPages", totalPages);
        return ResponseEntity.ok()
                .headers(headers)
                .body(univList);

    }

    @GetMapping("/univ/{post-id}")
    public ResponseEntity detailUnivPost(@PathVariable("post-id") Long post_id) {
        UnivDetailDto univDetailDto = postService.detailUnivPost(post_id);
        return ResponseEntity.ok(univDetailDto);
    }

    @GetMapping("/list/department")
    public ResponseEntity listDepartment(@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        List<UnivListDto> departmentList = postService.getDepartmentList(pageable);
        String totalPages = postService.getTotalPagesDepartment(pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("totalPages", totalPages);
        return ResponseEntity.ok()
                .headers(headers)
                .body(departmentList);
    }

    @GetMapping("/department/{post-id}")
    public ResponseEntity detailDepartmentPost(@PathVariable("post-id") Long post_id) {
        HomepageDetailDto departmentPost = postService.getDepartmentPost(post_id);
        return ResponseEntity.ok(departmentPost);
    }

    @GetMapping("/list/fun")
    public ResponseEntity listFun(@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        List<FunListDto> funList = postService.getFunList(pageable);
        String totalPages = postService.getTotalPagesFun(pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("totalPages", totalPages);
        return ResponseEntity.ok()
                .headers(headers)
                .body(funList);
    }

    @GetMapping("/list/insta")
    public ResponseEntity listInsta(@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        List<InstaListDto> instagramList = postService.getInstagramList(pageable);
        String totalPages = postService.getTotalPagesInsta(pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("totalPages", totalPages);
        return ResponseEntity.ok()
                .headers(headers)
                .body(instagramList);
    }

}
