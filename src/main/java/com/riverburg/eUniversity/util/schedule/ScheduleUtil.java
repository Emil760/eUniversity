package com.riverburg.eUniversity.util.schedule;

import com.riverburg.eUniversity.model.constant.ScheduleType;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.temporal.ChronoField;
import java.util.Date;


@Component
public class ScheduleUtil {

    public LocalDateTime findNextDate(LocalDateTime now, LocalDateTime beginDate, LocalDateTime endDate, ScheduleType scheduleType) {
        switch (scheduleType) {
            case OneTime -> {
                return beginDate.compareTo(now) >= 0 ? beginDate : null;
            }
            case OnceWeek -> {
                if (beginDate.compareTo(now) >= 0)
                    return beginDate;

                if (endDate.compareTo(now) < 0)
                    return null;

                var timeNow = now.toLocalTime();
                var timeBegin = beginDate.toLocalTime();

                var daysBetween = beginDate.get(ChronoField.DAY_OF_WEEK) - now.get(ChronoField.DAY_OF_WEEK);

                if (daysBetween == 0 && timeNow.compareTo(timeBegin) > 0)
                    daysBetween = 7;
                else if (daysBetween < 0)
                    daysBetween += 7;

                var nextDate = now.plusDays(daysBetween);

                return nextDate.compareTo(endDate) > 0 ? null : nextDate;
            }
            case OnceTwoWeek -> {
                if (beginDate.compareTo(now) >= 0)
                    return beginDate;

                if (endDate.compareTo(now) < 0)
                    return null;

                long weekRange = now.get(ChronoField.ALIGNED_WEEK_OF_YEAR)
                        - beginDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR);

                var timeNow = now.toLocalTime();
                var timeBegin = beginDate.toLocalTime();

                var daysBetween = beginDate.get(ChronoField.DAY_OF_WEEK) - now.get(ChronoField.DAY_OF_WEEK);

                if (weekRange % 2 == 0) {
                    if (daysBetween == 0 && timeNow.compareTo(timeBegin) > 0)
                        daysBetween = 14;
                    else if (daysBetween < 0)
                        daysBetween += 14;

                    var nextDate = now.plusDays(daysBetween);

                    return nextDate.compareTo(endDate) > 0 ? null : nextDate;
                } else {
                    daysBetween += 7;
                    var nextDate = now.plusDays(daysBetween);

                    return nextDate.compareTo(endDate) > 0 ? null : nextDate;
                }
            }
        }
        return null;
    }

    //TODO can write attendance only for today
    public static boolean isLessonNow(Date beginDate, Date endDate, LocalTime from, int type, Date date) {

        var a = isLessonToday(beginDate, endDate, type, date);

        var b = date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

        if (date.after(new Date()))
            return false;

        return a && b.isAfter(from);
    }

    public static boolean isLessonToday(Date beginDate, Date endDate, int type, Date date) {

        if ((!date.after(beginDate) || !date.before(endDate))
                && (beginDate.compareTo(date) != 0)
                && (endDate.compareTo(date) != 0))
            return false;


        var a = beginDate.toInstant().atZone(ZoneId.systemDefault());

        var b = date.toInstant().atZone(ZoneId.systemDefault());

        var daysBetween = Duration.between(a, b).toDays();

        switch (type) {
            case 0 -> {
                return daysBetween == 0;
            }
            case 1 -> {
                return daysBetween % 7 == 0;
            }
            case 2 -> {
                return daysBetween % 14 == 0;
            }
        }

        return true;
    }

}
