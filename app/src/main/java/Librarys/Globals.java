package Librarys;

import com.firemix.storeapp.R;

public class Globals {
    public static String STORES_DB = "StoresAppDB";
    public static final String FILEPROVIDER = "com.firemix.storeapp.fileprovider";
    public static String MAIN_FOLDER = "StoreApp";
    public static String IMAGES_FOLDER = "Camera";
    public static int LOGO_APP = R.mipmap.ic_launcher;
    public static String HOUR_FORMAT = "hh:mm a";
    public static String HR_FORMAT = "hh";
    public static String MIN_FORMAT = "mm";
    public static boolean SDCARD = false;
    public static String SHARED_APP = "StoreApp";
    public static String LOGIN_USER = "LoginUser";
    public static String LOGIN_ESTATE = "LoginState";

    public static boolean isSDCARD() {
        return SDCARD;
    }

    public static void setSDCARD(boolean SDCARD) {
        Globals.SDCARD = SDCARD;
    }
}
