package com.Marvel.stackquestion.Utils;

import android.content.Context;

import com.Marvel.stackquestion.Interface.OfflineDataDoa;
import com.Marvel.stackquestion.model.OfflineData;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {OfflineData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {


    private static AppDatabase INSTANCE;

    public abstract OfflineDataDoa userDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "OFFLINEDATA").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
