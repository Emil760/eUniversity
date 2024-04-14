package com.riverburg.eUniversity.model.constant;

public enum ScheduleType {
    OneTime,
    OnceWeek,
    OnceTwoWeek;

    public static ScheduleType fromShort(short s) {
        return switch (s) {
            case 1 -> OneTime;
            case 2 -> OnceWeek;
            case 3 -> OnceTwoWeek;
            default -> null;
        };
    }
}
