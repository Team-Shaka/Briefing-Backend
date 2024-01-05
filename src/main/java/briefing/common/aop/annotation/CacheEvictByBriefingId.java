package briefing.common.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheEvictByBriefingId {
    String value(); // 캐시 이름

    String briefingId(); // 캐시 키를 위한 briefingId
}
