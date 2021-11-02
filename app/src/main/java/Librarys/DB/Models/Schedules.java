package Librarys.DB.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "schedules")
public class Schedules {
    @PrimaryKey(autoGenerate = true)
    private int scheduleId;

    @ColumnInfo(name = "day_count")
    private int day_count;

    @ColumnInfo(name = "storeId")
    private String storeId;

    @ColumnInfo(name = "day")
    private String day;

    @ColumnInfo(name = "open_hour")
    private long open_hour;

    @ColumnInfo(name = "close_hour")
    private long close_hour;

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getDay_count() {
        return day_count;
    }

    public void setDay_count(int day_count) {
        this.day_count = day_count;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public long getOpen_hour() {
        return open_hour;
    }

    public void setOpen_hour(long open_hour) {
        this.open_hour = open_hour;
    }

    public long getClose_hour() {
        return close_hour;
    }

    public void setClose_hour(long close_hour) {
        this.close_hour = close_hour;
    }
}
