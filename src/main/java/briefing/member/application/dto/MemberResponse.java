package briefing.member.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberResponse {

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public static class LoginDTO {
	private Long memberId;
	private String accessToken;
	private String refreshToken;
}

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public static class ReIssueTokenDTO {
	private Long memberId;
	private String accessToken;
	private String refreshToken;
}

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public static class ListReIssueTokenDTO {
	List<ReIssueTokenDTO> ReIssueTokenList;
}

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public static class QuitDTO {
	private LocalDateTime quitAt;
}
}
