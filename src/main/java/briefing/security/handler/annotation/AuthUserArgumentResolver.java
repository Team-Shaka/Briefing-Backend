package briefing.security.handler.annotation;

import briefing.exception.ErrorCode;
import briefing.exception.handler.MemberException;
import briefing.member.api.MemberConverter;
import briefing.member.application.MemberQueryService;
import briefing.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {


    private final MemberQueryService memberQueryService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        AuthMember authUser = parameter.getParameterAnnotation(AuthMember.class);
        if (authUser == null) return false;
        if (parameter.getParameterType().equals(Member.class) == false) {
            return false;
        }
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object principal = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            principal = authentication.getPrincipal();
        }
        if (principal == null || principal.getClass() == String.class) {
            throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);
        }

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
        Member member = memberQueryService.findById(Long.valueOf(authenticationToken.getName()));
        return member;
    }
}
