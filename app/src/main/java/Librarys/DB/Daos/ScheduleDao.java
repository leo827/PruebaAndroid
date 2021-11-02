package Librarys.DB.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import Librarys.DB.Models.Schedules;

@Dao
public interface ScheduleDao {
    @Insert
    void InsertSchedule(Schedules schedules);

    @Query("select * from schedules where storeId = :StoreId order by day_count asc")
    List<Schedules> getSchedule(String StoreId);

    @Query("select count(*) from schedules where day_count = :day and storeId = :StoreId")
    int countDays(int day, String StoreId);

    @Query("update schedules set open_hour = :open, close_hour = :close where scheduleId = :ScheduleId")
    void updateScheduleStore(int ScheduleId,long open, long close);

    @Query("delete from schedules where storeId = :storeId")
    void deleteSchedule(String storeId);

    @Query("delete from schedules where storeId = :storeId and day_count = :day")
    void deleteScheduleDay(String storeId,int day);
}
