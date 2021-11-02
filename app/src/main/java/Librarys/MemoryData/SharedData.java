package Librarys.MemoryData;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import static Librarys.Globals.SHARED_APP;

public class SharedData extends AppCompatActivity {
    private static SharedData sharedData;
    private SharedPreferences sharedPreferences;

    private SharedData(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_APP, Context.MODE_PRIVATE);
    }

    //Inicalizador de la clase
    public static SharedData getInstance(Context context) {
        if (context != null) {
            sharedData = new SharedData(context);
        }
        return sharedData;
    }

    /* Guarda elementos en las preferencias
     **  1 - Clave de la llave Preferencia
     **  2 - Valor de la preferencia si es String
     **  3 - Valor de la preferencia si es boolean
     **  4 - Valor de la preferencia si es int
     **  5 - Tipo de valor a guardar (0 = String, 1 = boolean, 2 = int)
     */
    public boolean SaveData(String key, String value, boolean estado, int numero, int opcion) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            switch (opcion) {
                case 0:
                    editor.putString(key, value);
                    break;

                case 1:
                    editor.putBoolean(key, estado);
                    break;

                case 2:
                    editor.putInt(key, numero);
                    break;
            }
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //Obtiene los valores de la preferencia de tipo boolean
    public boolean getBooleanData(String key) {
        boolean res;
        try {
            res = sharedPreferences.getBoolean(key, false);
        } catch (Exception e) {
            res = false;
        }
        return res;
    }

    //Obtiene valores de la preferencia de tipo int
    public int getIntData(String key) {
        int t;
        try {
            t = sharedPreferences.getInt(key, 0);
        } catch (Exception e) {
            t = 0;
        }
        return t;
    }

    //Obtiene valores de la preferencia de tipo String
    public String getStringData(String key) {
        String data;
        try {
            data = sharedPreferences.getString(key, "");
        } catch (Exception e) {
            data = "";
        }
        return data;
    }

}
