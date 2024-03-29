package com.example.briefingcommon.common.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageResponse<T> {
    private final Boolean isLast;
    private final long totalCnt;
    private final T contents;
}
