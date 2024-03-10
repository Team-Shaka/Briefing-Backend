package com.example.briefinginfra.feign.nickname.hwanmoo.client;

import com.example.briefinginfra.feign.nickname.hwanmoo.dto.NickNameRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "nickNameClient",
        url = "https://nickname.hwanmoo.kr"
)
@Component
public interface NickNameClient {
    @GetMapping(value = "/")
    NickNameRes getNickName(@RequestParam(defaultValue = "json") String format, @RequestParam(defaultValue = "1") int count, @RequestParam(defaultValue = "8") int max_length);
}
