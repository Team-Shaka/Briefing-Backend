package briefing.converter;

import briefing.briefing.domain.TimeOfDay;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

public class TimeOfDayConverter implements Converter<String, TimeOfDay> {
    @Override
    public TimeOfDay convert(@NonNull final String source) {
        return TimeOfDay.findByValue(source);
    }
}
