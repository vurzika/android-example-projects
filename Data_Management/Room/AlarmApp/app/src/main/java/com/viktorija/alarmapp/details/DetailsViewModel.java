package com.viktorija.alarmapp.details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.viktorija.alarmapp.database.AlarmEntity;
import com.viktorija.alarmapp.database.AlarmRepository;

public class DetailsViewModel extends AndroidViewModel {

    private AlarmRepository mRepository;

    // Add a constructor that gets a reference to the repository.
    public DetailsViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AlarmRepository(application);
    }

    public void insert(AlarmEntity alarmItem) {
        mRepository.insertAlarm(alarmItem);
    }

    public LiveData<AlarmEntity> loadAlarmById(int alarmId) {
        return mRepository.loadAlarmById(alarmId);
    }
}
