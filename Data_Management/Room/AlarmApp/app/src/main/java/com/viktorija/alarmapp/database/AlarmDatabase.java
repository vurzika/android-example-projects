package com.viktorija.alarmapp.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.viktorija.alarmapp.utilities.SampleData;

import java.util.concurrent.Executors;

@Database(entities = {AlarmEntity.class}, version = 1, exportSchema = false)
public abstract class AlarmDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "AppDatabase.db";

    // Make the database a singleton to prevent having multiple instances of the database opened at the same time.
    // volatile means it can only be referenced from main memory
    private static volatile AlarmDatabase instance;

    //Define the DAOs that work with the database.
    public abstract AlarmDao alarmDao();

    public static AlarmDatabase getInstance(Context context) {
        if (instance == null) {
            // synchronization - to make sure that two parts of the application do not try to create it at the same time
            // we need object to synchronize on
            synchronized (RoomDatabase.class) {
                if (instance == null) {
                    // Create database here
                    // This code uses Room's database builder to create a RoomDatabase object
                    // in the application context from the AppDatabase class and names it "app_database".
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AlarmDatabase.class, DATABASE_NAME)
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    importInitialData();
                                }
                            })
                            .build();
                }
            }
        }
        return instance;
    }

    private static void importInitialData() {
        Executors.newSingleThreadExecutor()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        instance.alarmDao().deleteAll();
                        instance.alarmDao().insertAll(SampleData.getAlarmItems());
                    }
                });
    }
}
