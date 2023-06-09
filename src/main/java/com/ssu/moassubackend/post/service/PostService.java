package com.ssu.moassubackend.post.service;

import com.ssu.moassubackend.domain.image.Image;
import com.ssu.moassubackend.domain.post.*;
import com.ssu.moassubackend.domain.subscription.dto.SearchParameter;
import com.ssu.moassubackend.domain.subscription.service.KeywordAlarmService;
import com.ssu.moassubackend.post.dto.response.*;
import com.ssu.moassubackend.post.image.service.ImageService;
import com.ssu.moassubackend.post.repository.*;
import com.ssu.moassubackend.scrap.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final KeywordAlarmService alarmService;
    private final PostRepository postRepository;
    private final ImageService imageService;
    private final InstagramRepository instagramRepository;
    private final UnipageRepository unipageRepository;
    private final FunRepository funRepository;
    private final HomepageRepository homepageRepository;

    public List<UnivListDto> getUnivList(Pageable pageable) {
        Page<Unipage> all = unipageRepository.findAll(pageable);
        List<Unipage> unipages = all.getContent();

        List<UnivListDto> univListDtoList = unipages.stream()
                .map(page -> new UnivListDto(page))
                .collect(Collectors.toList());

        return univListDtoList;
    }



    public UnivDetailDto detailUnivPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("detail exception"));

        if(post instanceof Unipage) {
            Unipage unipage = (Unipage) post;
            List<Image> images = unipage.getImages();
            List<String> urls = images.stream()
                    .map(img -> img.getImage_url())
                    .collect(Collectors.toList());

            UnivDetailDto univDetailDto = UnivDetailDto.builder()
                    .url(unipage.getSsu_link())
                    .category(unipage.getField())
                    .content(unipage.getContent())
                    .title(unipage.getTitle())
                    .attach(urls)
                    .build();

            return univDetailDto;
        }

        return null;
    }

    public List<UnivListDto> getDepartmentList(Pageable pageable) {
        Page<Homepage> all = homepageRepository.findAll(pageable);
        List<Homepage> homepages = all.getContent();

        List<UnivListDto> univListDtoList = homepages.stream()
                .map(page -> new UnivListDto(page))
                .collect(Collectors.toList());

        return univListDtoList;
    }


    public HomepageDetailDto getDepartmentPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("detail exception"));
        if(post instanceof Homepage) {
            Homepage homepage = (Homepage) post;
            List<Image> images = homepage.getImages();

            List<String> urls = images.stream()
                    .map(img -> img.getImage_url())
                    .collect(Collectors.toList());

            HomepageDetailDto detailDto = HomepageDetailDto.builder()
                    .admin(homepage.getMajor())
                    .id(homepage.getId())
                    .url(homepage.getSsu_link())
                    .num(homepage.getField())
                    .content(homepage.getContent())
                    .date(homepage.getWriteDate().toString())
                    .attach(urls)
                    .build();

            return detailDto;
        }

        return null;
    }

    public List<FunListDto> getFunList(Pageable pageable) {
        /*Page<Post> all = postRepository.findAll(pageable);
        List<Post> posts = all.getContent();

        List<Fun> funs = new ArrayList<>();

        for (Post post : posts) {
            if(post instanceof Fun) {
                Fun fun = (Fun) post;
                funs.add(fun);
            }
        }*/
        Page<Fun> all = funRepository.findAll(pageable);
        List<Fun> funs = all.getContent();

        List<FunListDto> funListDtoList = funs.stream()
                .map(page -> new FunListDto(page))
                .collect(Collectors.toList());

        return funListDtoList;
    }

    public List<InstaListDto> getInstagramList(Pageable pageable) {

        Page<Instagram> all = instagramRepository.findAll(pageable);
        List<Instagram> instagrams = all.getContent();

        List<InstaListDto> instaListDtoList = instagrams.stream()
                .map(page -> new InstaListDto(page))
                .collect(Collectors.toList());

        return instaListDtoList;

    }

    public void saveHomepageUniv(List<HomepageUnivDto> homepageUnivDtos) {
        for (HomepageUnivDto dto : homepageUnivDtos) {
            List<String> attachList = new ArrayList<>();

            // String -> LocalDate 변환
            String dateString = dto.getDate();
//            LocalDate date = convertToLocalDateComputer(dateString);
            LocalDate date = convertToLocalDateAll(dateString);

            // 새로운 Post 저장
            Post post = new Unipage(dto.getTitle(), dto.getContent(), date, dto.getCategory(), dto.getUrl());
            Post savedPost = postRepository.save(post);

            // 알림 발송
            alarmService.doAlarm(toParameter(dto.getTitle(), dto.getContent(), dto.getAdmin()));

            // 알림 발송
            alarmService.doAlarm(toParameter(dto.getTitle(), dto.getContent(), dto.getAdmin()));

            // 새로운 Post 의 이미지 리스트 저장
            if(dto.getAttach() != null && !dto.getAttach().isEmpty()) {
                Map<String, String> attach = dto.getAttach();
                for (Map.Entry<String, String> entry : attach.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    imageService.saveImage(savedPost, key, value);
                }
            }
        }
    }

    public void saveHomepageComputer(List<HomepageComDto> homepageComDtos) {
        for (HomepageComDto dto : homepageComDtos) {
            List<String> attachList = new ArrayList<>();

            // String -> LocalDate 변환
            String dateString = dto.getDate();
//            LocalDate date = convertToLocalDateUniv(dateString);
            LocalDate date = convertToLocalDateAll(dateString);

            Post post = new Homepage(dto.getTitle(), dto.getContent(), date, dto.getNum(), dto.getUrl(), dto.getAdmin());
            Post savedPost = postRepository.save(post);

            // 알림 발송
            alarmService.doAlarm(toParameter(dto.getTitle(), dto.getContent(), dto.getAdmin()));

            // 새로운 Post 의 이미지 리스트 저장
            if(dto.getAttach() != null && !dto.getAttach().isEmpty()) {
                Map<String, String> attach = dto.getAttach();
                for (Map.Entry<String, String> entry : attach.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    imageService.saveImage(savedPost, key, value);
                }
            }

        }
    }
    public void saveHomepageElectronic(List<HomepageElecDto> homepageElecDtos) {
        for (HomepageElecDto dto : homepageElecDtos) {
            List<String> attachList = new ArrayList<>();

            // String -> LocalDate 변환
            String dateString = dto.getDate();
//            LocalDate date = convertToLocalDateElec(dateString);
            LocalDate date = convertToLocalDateAll(dateString);

            Post post = new Homepage(dto.getTitle(), dto.getContent(), date, dto.getAdmin(), dto.getUrl());
            Post savedPost = postRepository.save(post);

            // 알림 발송
            alarmService.doAlarm(toParameter(dto.getTitle(), dto.getContent(), dto.getAdmin()));
        }
    }

    public void saveHomepageSoftware(List<HomepageSoftDto> homepageSoftDtos) {
        for (HomepageSoftDto dto : homepageSoftDtos) {
            List<String> attachList = new ArrayList<>();

            // String -> LocalDate 변환
            String dateString = dto.getDate();
//            LocalDate date = convertToLocalDateSoft2(dateString);
            LocalDate date = convertToLocalDateAll(dateString);

            Post post = new Homepage(dto.getTitle(), dto.getContent(), date, dto.getNum(), dto.getUrl(), dto.getAdmin());
            Post savedPost = postRepository.save(post);

            // 알림 발송
            alarmService.doAlarm(toParameter(dto.getTitle(), dto.getContent(), dto.getAdmin()));

            // 새로운 Post 의 이미지 리스트 저장
            if(dto.getAttach() != null && !dto.getAttach().isEmpty()) {
                Map<String, String> attach = dto.getAttach();
                for (Map.Entry<String, String> entry : attach.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    imageService.saveImage(savedPost, key, value);
                }
            }

        }
    }

    public void saveHomepageGm(List<HomepageGmDto> homepageGmDtos) {
        for (HomepageGmDto dto : homepageGmDtos) {
            List<String> attachList = new ArrayList<>();

            // String -> LocalDate 변환
            String dateString = dto.getDate();
//            LocalDate date = convertToLocalDateSoft(dateString);
            LocalDate date = convertToLocalDateAll(dateString);

            Post post = new Homepage(dto.getTitle(), dto.getContent(), date, dto.getNum(), dto.getUrl(), dto.getAdmin());
            Post savedPost = postRepository.save(post);

            // 알림 발송
            alarmService.doAlarm(toParameter(dto.getTitle(), dto.getContent(), dto.getAdmin()));

            // 새로운 Post 의 이미지 리스트 저장
            if(dto.getAttach() != null && !dto.getAttach().isEmpty()) {
                Map<String, String> attach = dto.getAttach();
                for (Map.Entry<String, String> entry : attach.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    imageService.saveImage(savedPost, key, value);
                }
            }

        }
    }

    public void saveHomepageAI(List<HomepageAiDto> homepageAiDtos) {
        for (HomepageAiDto dto : homepageAiDtos) {
            List<String> attachList = new ArrayList<>();

            // String -> LocalDate 변환
            String dateString = dto.getDate();
//            LocalDate date = convertToLocalDateUniv(dateString);
            LocalDate date = convertToLocalDateAll(dateString);

            Post post = new Homepage(dto.getTitle(), dto.getContent(), date, dto.getAdmin(), dto.getUrl());
            Post savedPost = postRepository.save(post);

            // 알림 발송
            alarmService.doAlarm(toParameter(dto.getTitle(), dto.getContent(), dto.getAdmin()));
        }
    }


    public void saveHomepageFun(List<HomepageFunDto> homepageFunDtos) {
        for (HomepageFunDto dto : homepageFunDtos) {
            List<String> attachList = new ArrayList<>();

            List<String> applyPeriod = dto.getApplyPeriod();
//            System.out.println("applyPeriod.get(0) = " + applyPeriod.get(0));
            LocalDate applyStartDate = convertToLocalDateAll(applyPeriod.get(0));
            LocalDate applyEndDate = convertToLocalDateAll(applyPeriod.get(1));

            List<String> operatePeriod = dto.getOperatePeriod();
            LocalDate operateStartDate = convertToLocalDateAll(operatePeriod.get(0));
            LocalDate operateEndDate = convertToLocalDateAll(operatePeriod.get(1));

            Post post = new Fun(dto.getTitle(), dto.getAdmin(), dto.getUrl(), dto.getCategory(),
                    dto.getContent(), dto.getCover(), applyStartDate, applyEndDate, operateStartDate, operateEndDate);
            Post savedPost = postRepository.save(post);

            // 알림 발송
            alarmService.doAlarm(toParameter(dto.getTitle(), dto.getContent(), dto.getAdmin()));

            // 새로운 Post 의 이미지 리스트 저장
            if(dto.getAttach() != null && !dto.getAttach().isEmpty()) {
                Map<String, String> attach = dto.getAttach();
                for (Map.Entry<String, String> entry : attach.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    imageService.saveImage(savedPost, key, value);
                }
            }
        }
    }

    public void saveHomepageInsta(List<HomepageInstaDto> homepageInstaDtos) {
        for (HomepageInstaDto dto : homepageInstaDtos) {
            Post post = new Instagram(dto.getAdmin(), dto.getImg(), dto.getUrl());
            Post savedPost = postRepository.save(post);

        }
    }


    public LocalDate convertToLocalDateComputer(String dateString) {
        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" yyyy년 M월 d일", Locale.KOREAN);

        // 문자열을 LocalDate로 변환
        LocalDate date = LocalDate.parse(dateString, formatter);

        return date;
    }


    public LocalDate convertToLocalDateUniv(String dateString) {
        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        // 문자열을 LocalDate로 변환
        LocalDate date = LocalDate.parse(dateString, formatter);

        return date;
    }

    public LocalDate convertToLocalDateElec(String dateString) {
        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. MM. dd");

        // 문자열을 LocalDate로 변환
        LocalDate date = LocalDate.parse(dateString, formatter);

        return date;
    }
    public LocalDate convertToLocalDateSoft(String dateString) {
        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 문자열을 LocalDate로 변환
        LocalDate date = LocalDate.parse(dateString, formatter);

        return date;
    }

    public LocalDate convertToLocalDateSoft2(String dateString) {
        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");

        // 문자열을 LocalDate로 변환
        LocalDate date = LocalDate.parse(dateString, formatter);

        return date;
    }

    public LocalDate convertToLocalDateAll(String dateString) {
        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 문자열을 LocalDate로 변환
        LocalDate date = LocalDate.parse(dateString, formatter);

        return date;

    }

    public String getTotalPagesUniv(Pageable pageable) {
        Page<Unipage> all = unipageRepository.findAll(pageable);
        int totalPages = all.getTotalPages();
        String result = Integer.toString(totalPages);
        return result;
    }
    public String getTotalPagesDepartment(Pageable pageable) {
        Page<Homepage> all = homepageRepository.findAll(pageable);
        int totalPages = all.getTotalPages();
        String result = Integer.toString(totalPages);
        return result;
    }
    public String getTotalPagesInsta(Pageable pageable) {
        Page<Instagram> all = instagramRepository.findAll(pageable);
        int totalPages = all.getTotalPages();
        String result = Integer.toString(totalPages);
        return result;
    }
    public String getTotalPagesFun(Pageable pageable) {
        Page<Fun> all = funRepository.findAll(pageable);
        int totalPages = all.getTotalPages();
        String result = Integer.toString(totalPages);
        return result;
    }

    public SearchParameter toParameter(String title, String content, String admin) {
        return new SearchParameter(title, content, admin);
    }


}

