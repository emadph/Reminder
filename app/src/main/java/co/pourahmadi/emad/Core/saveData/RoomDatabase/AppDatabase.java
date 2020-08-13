package co.pourahmadi.emad.Core.saveData.RoomDatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import co.pourahmadi.emad.Models.AlarmList;

@Database(entities = {AlarmList.class}, version = 6, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    @NonNull
    public abstract ProductsDao Dao ();


    @NonNull
    public AppDatabase getDatabase (@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "Products")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();

                }
            }
        }
        return INSTANCE;
    }


}