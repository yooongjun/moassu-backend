package com.ssu.moassubackend.post;

import com.ssu.moassubackend.post.dto.response.UnivDetailDto;
import com.ssu.moassubackend.post.dto.response.UnivListDto;
import com.ssu.moassubackend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity listUniv(@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<UnivListDto> univList = postService.getUnivList(pageable);
        return ResponseEntity.ok(univList);
    }

    @GetMapping("/univ/{post-id}")
    public ResponseEntity detailUnivPost(@PathVariable("post-id") Long post_id) {
        UnivDetailDto univDetailDto = postService.detailUnivPost(post_id);
        return ResponseEntity.ok(univDetailDto);
    }


}
