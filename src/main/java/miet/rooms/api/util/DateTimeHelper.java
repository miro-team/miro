package miet.rooms.api.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeHelper {
    public static final LocalDate MIN_DATE = LocalDate.of(1901, 1, 1);
    public static final LocalDate MAX_DATE = LocalDate.of(2999, 12, 31);
    public static final LocalDateTime MIN_DATE_TIME = LocalDateTime.of(1901, 1, 1, 0, 0, 0, 0);
    public static final LocalDateTime MAX_DATE_TIME = LocalDateTime.of(2999, 12, 31, 23, 59, 59, 999999999);
    private static final DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final DateTimeFormatter formatterId = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");

    private static final String DEFAULT_ZONE = "Europe/Moscow";
    private static Pattern ddMMyyyy = Pattern.compile("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}");
    private static Pattern yyyyMMdd = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");

    public static ZoneId getDefaultZoneId() {
        return ZoneId.of(DEFAULT_ZONE);
    }

    public static LocalDateTime asLocalDateTime(Long val) {
        return Instant.ofEpochMilli(val).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime asLocalDateTime(String val) {
        return Instant.ofEpochMilli(Long.parseLong(val)).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime asDefaultLocalDateTime(String val) {
        return Instant.ofEpochMilli(Long.parseLong(val)).atZone(ZoneId.of(DEFAULT_ZONE)).toLocalDateTime();
    }

    public static String dateToString(LocalDate date) {
        return date.format(formatterDate);
    }

    public static String dateToString(LocalDateTime date) {
        return date.format(formatterDate);
    }

    public static String dateTimeToString(LocalDateTime date) {
        return date.format(formatterDateTime);
    }

    public static String dateToId(LocalDateTime date) {
        return date.format(formatterId);
    }

    public static LocalDate asLocalDate(String val) {
        return asLocalDate(val, ZoneId.systemDefault());
    }

    public static LocalDate asDefaultLocalDate(String val) {
        return asLocalDate(val, ZoneId.of(DEFAULT_ZONE));
    }

    private static LocalDate asLocalDate(String val, ZoneId zone) {
        try {
            DateTimeFormatter formatter = null;

            Matcher matcher = ddMMyyyy.matcher(val);
            if (matcher.find()) {
                val = matcher.group();
                formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            } else {
                matcher = yyyyMMdd.matcher(val);
                if (matcher.find()) {
                    val = matcher.group();
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                }
            }

            if (formatter == null) {
                throw new IllegalArgumentException("Don't know format for text: " + val);
            }
            return LocalDate.parse(val, formatter);
        } catch (Exception ex) {
            return Instant.ofEpochMilli(Long.parseLong(val)).atZone(zone).toLocalDate();
        }
    }

    public static DateTimeFormatter getFormatter() {
        return formatterDate;
    }

    public static List<String> getEduDates(LocalDateTime startDate, int eduDaysAmount) {
        List<String> dates = new ArrayList<>();
        for (int day = 0; day < eduDaysAmount; day++) {
            dates.add(DateTimeHelper.dateToString(startDate.plusDays(day)));
        }
        dates.add(DateTimeHelper.dateToString(startDate.plusDays(7)));
        for (int week = 1; week < 23; week++) {
            for (int day = 1; day < eduDaysAmount; day++) {
                int plusDay = day + week * 7;
                dates.add(DateTimeHelper.dateToString(startDate.plusDays(plusDay)));
            }
            int pause = 7 * (week + 1);
            dates.add(DateTimeHelper.dateToString(startDate.plusDays(pause)));
        }
        return dates;
    }
}
