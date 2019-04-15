package com.viktorija.alarmapp.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.viktorija.alarmapp.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AlarmRepository {

    private static AlarmRepository ourInstance;

    public LiveData<List<AlarmEntity>> mAlarmList;

    // declaring an instance of the class AppDatabase.
    private AlarmDatabase mDb;

    // All Room database operations must be executed on the background thread.
    // Creating a single executor object
    private Executor executor = Executors.newSingleThreadExecutor();

    // Repository - singleton pattern
    public static AlarmRepository getInstance(Context context) {
        if (ourInstance == null) {
            // if ourInstance is null - initialize it by calling a private constructor
            ourInstance = new AlarmRepository(context);
        }
        return ourInstance;
    }

    public AlarmRepository(Context context) {
        mDb = AlarmDatabase.getInstance(context);
        mAlarmList = getAllAlarmItems();
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.alarmDao().insertAll(SampleData.getAlarmItems());
            }
        });
    }

    // when you read information through dao you do not need executor
    private LiveData<List<AlarmEntity>> getAllAlarmItems(){
        // content of the database table
        return mDb.alarmDao().getAll();
    }

    public void insertAlarm(final AlarmEntity alarmItem){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.alarmDao().insertAlarm(alarmItem);
            }
        });
    }

    public void deleteAlarm(final AlarmEntity alarmItem){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.alarmDao().deleteAlarm(alarmItem);
            }
        });
    }

    public LiveData<AlarmEntity> loadAlarmById(int alarmId){
        return mDb.alarmDao().getAlarmById(alarmId);
    }
}
