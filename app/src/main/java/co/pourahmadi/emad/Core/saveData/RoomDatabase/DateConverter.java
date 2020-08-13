package co.pourahmadi.emad.Core.saveData.RoomDatabase;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

public class DateConverter {

    @NonNull
    @TypeConverter
    public String string (@NonNull Date data) {
        Gson gson = new Gson();
        Type type = new TypeToken <Date>() {
        }.getType();
        return gson.toJson(data, type);
    }

    @NonNull
    @TypeConverter
    public Date date (@NonNull String data) {
        Gson gson = new Gson();
        Type type = new TypeToken <Date>() {
        }.getType();
        return gson.fromJson(data, type);
    }
}
