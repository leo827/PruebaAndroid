package Librarys.DB.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import Librarys.DB.Models.Users;


@Dao
public interface UsersDao {
    @Insert
    void InsertUsers(Users users);

    @Query("select * from users where username = :username and password = :password")
    List<Users> getUsers(String username, String password);

    @Query("select count(*) from users where username = :username")
    int countUsers(String username);

}
