package com.viktorija.alarmapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "alarms")
public class AlarmEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int hours;

    private int minutes;

    public AlarmEntity(int id, int hours, int minutes) {
        this.id = id;
        this.hours = hours;
        this.minutes = minutes;
    }

    @Ignore
    public AlarmEntity(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
