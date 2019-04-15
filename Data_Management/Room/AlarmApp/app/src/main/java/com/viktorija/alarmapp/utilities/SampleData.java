package com.viktorija.alarmapp.utilities;

import com.viktorija.alarmapp.database.AlarmEntity;

import java.util.ArrayList;
import java.util.List;

public class SampleData {

    public static List <AlarmEntity> getAlarmItems() {
        List <AlarmEntity> alarmList = new ArrayList<>();
        alarmList.add(new AlarmEntity(9, 0));
        alarmList.add(new AlarmEntity(10, 0));
        alarmList.add(new AlarmEntity(23, 0));
        return alarmList;
    }
}
