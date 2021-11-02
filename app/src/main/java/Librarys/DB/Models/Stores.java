package Librarys.DB.Models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stores")
public class Stores {
    @PrimaryKey(autoGenerate = true)
    private int storeId;

    @ColumnInfo(name = "StoreIdSchedule")
    private String StoreIdSchedule;

    @ColumnInfo(name = "user_store")
    private String user_store;

    @ColumnInfo(name = "name")
    private String local_name;

    @ColumnInfo(name = "nit")
    private String nit;

    @ColumnInfo(name = "description_store")
    private String description_store;

    @ColumnInfo(name = "image_store")
    private String image_store;

    @ColumnInfo(name = "location_latitud")
    private double location_latitud;

    @ColumnInfo(name = "location_longitud")
    private double location_longitud;

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreIdSchedule() {
        return StoreIdSchedule;
    }

    public void setStoreIdSchedule(String storeIdSchedule) {
        StoreIdSchedule = storeIdSchedule;
    }

    public String getUser_store() {
        return user_store;
    }

    public void setUser_store(String user_store) {
        this.user_store = user_store;
    }

    public String getLocal_name() {
        return local_name;
    }

    public void setLocal_name(String local_name) {
        this.local_name = local_name;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDescription_store() {
        return description_store;
    }

    public void setDescription_store(String description_store) {
        this.description_store = description_store;
    }

    public String getImage_store() {
        return image_store;
    }

    public void setImage_store(String image_store) {
        this.image_store = image_store;
    }

    public double getLocation_latitud() {
        return location_latitud;
    }

    public void setLocation_latitud(double location_latitud) {
        this.location_latitud = location_latitud;
    }

    public double getLocation_longitud() {
        return location_longitud;
    }

    public void setLocation_longitud(double location_longitud) {
        this.location_longitud = location_longitud;
    }
}
