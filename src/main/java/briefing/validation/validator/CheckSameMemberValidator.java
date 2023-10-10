package briefing.validation.validator;

import briefing.exception.ErrorCode;
import briefing.exception.handler.MemberException;
import briefing.member.application.MemberQueryService;
import briefing.member.domain.Member;
import briefing.validation.annotation.CheckSameMember;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.concurrent.RecursiveTask;

@Component
@RequiredArgsConstructor
public class CheckSameMemberValidator implements ConstraintValidator<CheckSameMember, Long>{


    @Override
    public void initialize(CheckSameMember constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        Object principal = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            principal = authentication.getPrincipal();
        }
        if (principal == null || principal.getClass() == String.class) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.MEMBER_NOT_FOUND.toString()).addConstraintViolation();
            return false;
        }

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
        // 로그인 한 사용자가 어드민인지 나중에 추가
        if(!value.equals(Long.valueOf(authenticationToken.getName()))){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.MEMBER_NOT_SAME.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
