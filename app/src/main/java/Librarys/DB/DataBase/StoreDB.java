package Librarys.DB.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import Librarys.DB.Daos.ScheduleDao;
import Librarys.DB.Daos.StoresDao;
import Librarys.DB.Daos.UsersDao;
import Librarys.DB.Models.Schedules;
import Librarys.DB.Models.Stores;
import Librarys.DB.Models.Users;

@Database(entities = {Users.class, Stores.class, Schedules.class}, version = 1, exportSchema = false)
public abstract class StoreDB extends RoomDatabase {

    public abstract UsersDao usersDao();
    public abstract StoresDao storesDao();
    public abstract ScheduleDao scheduleDao();
}
