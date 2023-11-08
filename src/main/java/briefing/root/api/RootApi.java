package briefing.root.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "01-Root \uD83C\uDF10",description = "루트 API")
@RestController
@RequestMapping("/")
@RequiredArgsConstructor

public class RootApi {

    @Operation(summary = "01-01 Root\uD83C\uDF10 헬스 체크 #FRAME", description = "헬스 체크 API입니다.")
    @GetMapping("/")
    public String healthCheck(){
        return "I'm healthy!!!!";
    }
}

