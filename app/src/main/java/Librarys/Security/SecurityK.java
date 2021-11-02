package Librarys.Security;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

public class SecurityK extends AppCompatActivity {
    private static SecurityK securityK;

    private SecurityK(Context context) {
    }

    //Inicializa la clase
    public static SecurityK getInstance(Context context) {
        if (context != null) {
            securityK = new SecurityK(context);
        }
        return securityK;
    }

    //Encripta los datos para guardar en la BD
    public String EncriptData(String data) {
        String encode_text = "";
        try {
            encode_text = RSASecurity.encrypt(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encode_text;

    }

    //Encripta multiples datos

    //Desencripta los datos para mostrar
    public String DecriptData(String data) {
        String decode_text = "";
        try {
            decode_text = RSASecurity.decrypt(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decode_text;
    }
}
