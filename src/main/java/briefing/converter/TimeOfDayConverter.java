package briefing.converter;

import org.springframework.core.convert.converter.Converter;

import briefing.briefing.domain.TimeOfDay;
import lombok.NonNull;

public class TimeOfDayConverter implements Converter<String, TimeOfDay> {
@Override
public TimeOfDay convert(@NonNull final String source) {
	return TimeOfDay.findByValue(source);
}
}
