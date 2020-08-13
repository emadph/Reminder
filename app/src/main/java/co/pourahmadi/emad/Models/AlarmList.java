package co.pourahmadi.emad.Models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Parcel(Parcel.Serialization.BEAN)
@Entity(tableName = "AlarmList")
public class AlarmList {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "hour")
    private int hour;
    @ColumnInfo(name = "minute")
    private int minute;
    @ColumnInfo(name = "engDate")
    private Date engDate;
    @ColumnInfo(name = "perDate")
    private String perDate;
    @ColumnInfo(name = "repeatOnce")
    private boolean repeatOnce;

    @ParcelConstructor
    public AlarmList ( String name, int hour, int minute, Date engDate, String perDate, boolean repeatOnce) {
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.engDate = engDate;
        this.perDate = perDate;
        this.repeatOnce = repeatOnce;
    }

    public boolean isRepeatOnce () {
        return repeatOnce;
    }

    public void setRepeatOnce (boolean repeatOnce) {
        this.repeatOnce = repeatOnce;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getHour () {
        return hour;
    }

    public void setHour (int hour) {
        this.hour = hour;
    }

    public int getMinute () {
        return minute;
    }

    public void setMinute (int minute) {
        this.minute = minute;
    }

    public Date getEngDate () {
        return engDate;
    }

    public void setEngDate (Date engDate) {
        this.engDate = engDate;
    }

    public String getPerDate () {
        return perDate;
    }

    public void setPerDate (String perDate) {
        this.perDate = perDate;
    }
}