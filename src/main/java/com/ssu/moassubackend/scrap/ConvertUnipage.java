package com.ssu.moassubackend.scrap;

import com.ssu.moassubackend.scrap.dto.UniResponseDto;
import org.python.core.PyDictionary;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertUnipage {

    public void getHomepageUniv() {
        PythonInterpreter interpreter = new PythonInterpreter();

        interpreter.execfile("D:\\moassu\\moassu-backend\\src\\main\\java\\com\\ssu\\moassubackend\\pyfile\\ScrapUnipage.py"); // Python 파일 경로 설정

        PyObject getUnivNotice = interpreter.get("get_univ_notice"); // Python 함수명 설정

        PyObject homepageUnivDict = getUnivNotice.__call__();
        PyList pyList = (PyList) homepageUnivDict;  // PyList로 변환

        List<UniResponseDto> responseList = new ArrayList<>();
        for (Object obj : pyList) {  // PyList 객체를 반복
            PyDictionary pyDict = (PyDictionary) obj;  // 각각의 PyDictionary 객체 추출
            Map<String, Object> data = new HashMap<>();

            // PyDictionary.items() 메소드를 이용하여 (key, value) 튜플 추출
            for (Object item : pyDict.items()) {
                PyObject[] pyItem = (PyObject[]) item;
                String key = pyItem[0].toString();
                Object value = pyItem[1].__tojava__(Object.class);
                data.put(key, value);
            }

            // ResponseDto 객체로 변환하여 리스트에 추가
            UniResponseDto responseDto = new UniResponseDto();
            responseDto.setUrl(data.get("url").toString());
            responseDto.setTitle(data.get("title").toString());
            responseDto.setCategory(data.get("category").toString());
            responseDto.setDate(data.get("date").toString());
            responseDto.setContent(data.get("content").toString());
            responseDto.setAttach((Map<String, String>) data.get("attach"));
            responseList.add(responseDto);

            System.out.println("responseDto.getTitle() = " + responseDto.getTitle());
            System.out.println("responseDto.getUrl() = " + responseDto.getUrl());
            System.out.println("responseDto.getCategory() = " + responseDto.getCategory());
            System.out.println("responseDto.getContent() = " + responseDto.getContent());
            System.out.println("responseDto.getDate() = " + responseDto.getDate());
            Map<String, String> attachMap = responseDto.getAttach();
            for (Map.Entry<String, String> entry : attachMap.entrySet()) {
                System.out.println("responseDto.getAttach: " + entry.getKey() + " - " + entry.getValue());
            }

            System.out.println("=================================================================================");
        }

    }

}
