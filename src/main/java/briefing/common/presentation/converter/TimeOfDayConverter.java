package briefing.common.presentation.converter;

import org.springframework.core.convert.converter.Converter;

import briefing.common.enums.TimeOfDay;
import lombok.NonNull;

public class TimeOfDayConverter implements Converter<String, TimeOfDay> {
    @Override
    public TimeOfDay convert(@NonNull final String source) {
        return TimeOfDay.findByValue(source);
    }
}
