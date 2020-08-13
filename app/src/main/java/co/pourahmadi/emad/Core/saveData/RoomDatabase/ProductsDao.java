package co.pourahmadi.emad.Core.saveData.RoomDatabase;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import co.pourahmadi.emad.Models.AlarmList;
import io.reactivex.Observable;

@Dao
public interface ProductsDao {

    @NonNull
    @Query("SELECT * FROM AlarmList")
    Observable <List <AlarmList>> getAll ();


    @Query("DELETE FROM AlarmList WHERE id = :ID")
    void deleteById (int ID);

    @Query("UPDATE AlarmList SET name=:name , engDate=:date , perDate=:perDate,hour=:hour , minute=:min  , repeatOnce=:repeatOnce   WHERE id = :id")
    void updateAlarm (int id, String name, Date date, String perDate, int hour, int min, boolean repeatOnce);


    @Query("Delete  FROM AlarmList")
    void deleteProduct ();

    @Delete
    void deleteAlarmList (@NonNull AlarmList... products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (@NonNull AlarmList model);
}