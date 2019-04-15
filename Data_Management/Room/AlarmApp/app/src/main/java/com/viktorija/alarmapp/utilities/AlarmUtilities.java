package com.viktorija.alarmapp.utilities;

import com.viktorija.alarmapp.database.AlarmEntity;

public class AlarmUtilities {

    public static String getFormattedTime(AlarmEntity alarmEntity, boolean amPmMode) {
        int hours = alarmEntity.getHours();
        if (amPmMode && hours > 12) {
            hours -= 12;
        }
        return addZeroIfNeeded(hours) + ":" + addZeroIfNeeded(alarmEntity.getMinutes());
    }

    private static String addZeroIfNeeded(int number) {
        if (number < 10) {
            return "0" + number;
        } else {
            return "" + number;
        }
    }

    public static String getAmPm(AlarmEntity alarm) {
        if (alarm.getHours() < 12){
            return "AM";
        } else {
            return "PM";
        }
    }
}
