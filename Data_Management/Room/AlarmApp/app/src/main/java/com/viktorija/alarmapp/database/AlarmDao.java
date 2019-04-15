package com.viktorija.alarmapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AlarmDao {

    //  todo: pochemu replace? a kogda ignore?
    // https://developer.android.com/reference/android/arch/persistence/room/OnConflictStrategy
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAlarm(AlarmEntity alarmEntity);

    // todo: sjuda nado onConflict?
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AlarmEntity> alarmList);

    @Delete
    void deleteAlarm(AlarmEntity alarmEntity);

    @Query("SELECT * FROM alarms WHERE id = :id")
    LiveData<AlarmEntity> getAlarmById(int id);

    @Query ("SELECT * FROM alarms ORDER BY hours ASC")
    LiveData<List<AlarmEntity>> getAll();

    // delete all items
    @Query("DELETE FROM alarms")
    void deleteAll();
}
