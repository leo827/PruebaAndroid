package Librarys.DB.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Librarys.DB.Models.Stores;

@Dao
public interface StoresDao {
    @Insert
    void InsertStore(Stores stores);

    @Query("select * from stores where name LIKE '%' || :nombre || '%' " +
            "OR nit LIKE '%' || :nombre || '%' " +
            "OR description_store LIKE '%' || :nombre || '%' order by name asc")
    List<Stores> getStores(String nombre);

    @Query("select * from stores where storeId = :ID")
    List<Stores> getStoresId(int ID);

    @Query("select count(*) from stores where name = :name")
    int countStores(String name);

    @Query("select count(*) from stores where name = :name and storeId != :StoreId")
    int countStoresEQ(int StoreId,String name);

    @Query("select count(*) from stores where nit = :name and storeId != :StoreId")
    int countStoresEQNit(int StoreId,String name);

    @Query("select count(*) from stores where nit = :nit")
    int countStoresNit(String nit);

    @Query("select storeId from stores order by storeId desc limit 1")
    int getStoreId();


    @Query("select count(*) from stores where user_store = :username and storeId = :StoreId")
    int verifyStore(String username,int StoreId);

    @Query("update stores set image_store = :image where storeId = :storeId")
    void updateImageStore(String image, int storeId);

    @Query("update stores set location_latitud = :latitud and location_longitud = :longitud where storeId = :storeId")
    void updateLocationStore(double latitud, double longitud, int storeId);

    @Query("update stores set name = :name where storeId = :storeId")
    void updateNameStore(String name, int storeId);

    @Query("update stores set nit = :nit where storeId = :storeId")
    void updateNitStore(String nit, int storeId);

    @Query("update stores set description_store = :description where storeId = :storeId")
    void updateDescriptionStore(String description, int storeId);

    @Update()
    void updateStore(Stores stores);

    @Query("delete from stores where storeId = :storeId")
    void deleteStore(int storeId);
}
