package com.example.briefinginfra.feign.nickname.hwanmoo.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class NickNameRes {
    private List<String> words;
    private String seed;
}
