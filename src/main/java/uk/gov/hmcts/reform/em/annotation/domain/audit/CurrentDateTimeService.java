package uk.gov.hmcts.reform.em.annotation.domain.audit;

import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Service("dateTimeService")
public class CurrentDateTimeService implements DateTimeProvider {

    @Override
    public Calendar getNow() {
        return GregorianCalendar.from(ZonedDateTime.now());
    }
}
