package com.viktorija.alarmapp.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.viktorija.alarmapp.database.AlarmEntity;
import com.viktorija.alarmapp.database.AlarmRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<AlarmEntity>> mAlarmList;

    private AlarmRepository mRepository;


    // constructor
    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AlarmRepository.getInstance(application.getApplicationContext());
        mAlarmList = mRepository.mAlarmList;
    }

    //Adding sample data automatically
    public LiveData<List<AlarmEntity>> getAlarmList() {
        return mAlarmList;
    }
}
