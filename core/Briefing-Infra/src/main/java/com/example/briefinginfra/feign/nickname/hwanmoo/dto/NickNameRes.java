package com.example.briefinginfra.feign.nickname.hwanmoo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NickNameRes {
    private List<String> words = new ArrayList<>();
    private String seed;
}
