package Librarys.DB;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.google.gson.Gson;

import java.util.List;

import Librarys.DB.DataBase.StoreDB;
import Librarys.DB.Models.Schedules;
import Librarys.DB.Models.Stores;
import Librarys.DB.Models.Users;
import Librarys.Security.SecurityK;

import static Librarys.Globals.STORES_DB;

public class DataBaseStores {
    public static DataBaseStores dataBaseStores;
    public static StoreDB storeDB;
    private SecurityK securityK;

    private DataBaseStores(Context contexto) {
        securityK = SecurityK.getInstance(contexto);
        storeDB = Room.databaseBuilder(contexto, StoreDB.class, STORES_DB).fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }

    public static DataBaseStores getInstance(Context contexto) {
        if (contexto != null) {
            dataBaseStores = new DataBaseStores(contexto);
        }
        return dataBaseStores;
    }

    //Insertar
    public boolean InsertUser(Users users){
        try{
            Users user = new Users();
            user.setUser( users.getUser());
            user.setPass(securityK.EncriptData(users.getPass()));
            storeDB.usersDao().InsertUsers(user);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean InsertStore(Stores store){
        try{
            storeDB.storesDao().InsertStore(store);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean InsertSchedule(Schedules schedules){
        try{
            storeDB.scheduleDao().InsertSchedule(schedules);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    //Listar información
    public List<Users> getUsers(Users users){
        Users user = new Users();
        user.setUser( users.getUser());
        user.setPass(securityK.EncriptData(users.getPass()));
        return storeDB.usersDao().getUsers(user.getUser(),user.getPass());
    }

    public List<Stores> getStores(String name){
        return storeDB.storesDao().getStores(name);
    }

    public List<Stores> getStoresId(int ID){
        return storeDB.storesDao().getStoresId(ID);
    }

    public List<Schedules> getSchedules(String StoreId){
        return storeDB.scheduleDao().getSchedule(StoreId);
    }

    public int CountUsers(String user){
        return storeDB.usersDao().countUsers(user);
    }

    public int CountStores(String name){
        return storeDB.storesDao().countStores(name);
    }

    public int CountStoresEq(int StoreId,String name){
        return storeDB.storesDao().countStoresEQ(StoreId,name);
    }

    public int CountStoresEqNit(int StoreId,String name){
        return storeDB.storesDao().countStoresEQNit(StoreId,name);
    }

    public int CountDays(int day, String StoreSchedule){
        return storeDB.scheduleDao().countDays(day,StoreSchedule);
    }

    public int CountStoresNit(String nit){
        return storeDB.storesDao().countStoresNit(nit);
    }

    public int VerifyStore(String user,int StoreId){
        return storeDB.storesDao().verifyStore(user,StoreId);
    }

    public int getStoreId(){
        return storeDB.storesDao().getStoreId();
    }

    //Eliminar
    public boolean DeleteStore(int StoreId){
        try{
            storeDB.storesDao().deleteStore(StoreId);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean DeleteSchedule(String StoreId){
        try{
            storeDB.scheduleDao().deleteSchedule(StoreId);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean DeleteScheduleDay(String StoreId,int Days){
        try{
            storeDB.scheduleDao().deleteScheduleDay(StoreId,Days);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    //Actualizar
    public boolean UpdateStore(Stores stores){
        try {
            storeDB.storesDao().updateStore(stores);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean UpdateSchedule(int ScheduleId, long desde, long hasta){
        try{
            storeDB.scheduleDao().updateScheduleStore(ScheduleId,desde,hasta);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
