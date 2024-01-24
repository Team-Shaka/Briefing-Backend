package com.example.briefingapi.aop.aspect;

import java.util.Optional;

import com.example.briefingapi.aop.annotation.CacheEvictByBriefingId;
import com.example.briefingcommon.domain.repository.article.BriefingRepository;
import com.example.briefingcommon.entity.Briefing;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;


import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class CacheEvictByBriefingIdAspect {

    private final BriefingRepository briefingRepository;
    private final CacheManager cacheManager;
    private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    private StandardEvaluationContext evaluationContext = new StandardEvaluationContext();

    @After(value = "@annotation(cacheEvictByBriefingId)")
    public void evictCache(JoinPoint joinPoint, CacheEvictByBriefingId cacheEvictByBriefingId) {
        System.out.println("AOP CALL!!!");

        String briefingIdExpression = cacheEvictByBriefingId.briefingId();
        Long briefingId = evaluateExpression(joinPoint, briefingIdExpression, Long.class);

        Optional<Briefing> optionalBriefing = briefingRepository.findById(briefingId);

        if (optionalBriefing.isPresent()) {
            Briefing briefing = optionalBriefing.get();
            String cacheKey = briefing.getType().name();
            Cache cache = cacheManager.getCache(cacheEvictByBriefingId.value());
            System.out.println(cacheEvictByBriefingId.value());
            System.out.println(cacheKey);
            System.out.println(cache);
            if (cache != null) {
                cache.evict(cacheKey);
            }
        }
    }

    private <T> T evaluateExpression(
            JoinPoint joinPoint, String expression, Class<T> desiredResultType) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = methodSignature.getParameterNames();

        for (int i = 0; i < parameterNames.length; i++) {
            evaluationContext.setVariable(parameterNames[i], args[i]);
        }

        Expression parsedExpression = spelExpressionParser.parseExpression(expression);
        return parsedExpression.getValue(evaluationContext, desiredResultType);
    }
}
