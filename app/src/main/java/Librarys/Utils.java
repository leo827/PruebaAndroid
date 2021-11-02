package Librarys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firemix.storeapp.BaseActivity;
import com.firemix.storeapp.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputEditText;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static Librarys.Globals.FILEPROVIDER;
import static Librarys.Globals.IMAGES_FOLDER;
import static Librarys.Globals.LOGO_APP;
import static Librarys.Globals.MAIN_FOLDER;
import static androidx.core.content.FileProvider.getUriForFile;

public class Utils extends BaseActivity {
    private Context context = null;
    private static Utils utils = null;
    private final ErrorsMsj errorsMsj;

    private Utils(Context ctx){
        this.context = ctx;
        errorsMsj = new ErrorsMsj();
    }

    public static Utils getInstance (Context context){
        if(context != null){
            utils = new Utils(context);
        }
        return utils;
    }

    public String ValidateLogin(ArrayList<EditText> textos){
        String usuario = textos.get(0).getText().toString().trim();
        String contra = textos.get(1).getText().toString().trim();
        if(usuario.equals("") && contra.equals("")){
            errorsMsj.setErrorDescription(context.getString(R.string.missing_data_login));
        } else if( usuario.equals("")){
            errorsMsj.setErrorDescription(context.getString(R.string.user_required));
        } else if( contra.equals("")){
            errorsMsj.setErrorDescription(context.getString(R.string.password_required));
        } else if(contra.length() < 5){
            errorsMsj.setErrorDescription(context.getString(R.string.password_max));
        } else{
            errorsMsj.setErrorDescription(context.getString(R.string.success));
        }
        return errorsMsj.getErrorDescription();
    }

    public String ValidateRegistro(ArrayList<EditText> textos){
        String usuario = textos.get(0).getText().toString().trim();
        String contra = textos.get(1).getText().toString().trim();
        String repetir = textos.get(2).getText().toString().trim();
        if(usuario.equals("") && contra.equals("") && repetir.equals("")){
            errorsMsj.setErrorDescription(context.getString(R.string.missing_data_register));
        } else if( usuario.equals("")){
            errorsMsj.setErrorDescription(context.getString(R.string.user_required));
        } else if( contra.equals("")){
            errorsMsj.setErrorDescription(context.getString(R.string.password_required));
        } else if(contra.length() < 5){
            errorsMsj.setErrorDescription(context.getString(R.string.password_max));
        } else if(!repetir.equals(contra)){
            errorsMsj.setErrorDescription(context.getString(R.string.password_errors));
        } else{
            errorsMsj.setErrorDescription(context.getString(R.string.success));
        }
        return errorsMsj.getErrorDescription();
    }

    public String ValidateRegistroTienda(ArrayList<EditText> textos, String foto, LatLng ubicacion, int hours){
        String nombre = textos.get(0).getText().toString().trim();
        String nit = textos.get(1).getText().toString().trim();
        String descripcion = textos.get(2).getText().toString().trim();
        if(nombre.equals("") && nit.equals("") && descripcion.equals("")){
            errorsMsj.setErrorDescription(context.getString(R.string.missing_data_register));
        } else if( nombre.equals("")){
            errorsMsj.setErrorDescription(context.getString(R.string.empresa_required));
        } else if( nit.equals("")){
            errorsMsj.setErrorDescription(context.getString(R.string.nit_required));
        } else if(descripcion.length() < 30){
            errorsMsj.setErrorDescription(context.getString(R.string.description_max));
        } else if(descripcion.length() > 150){
            errorsMsj.setErrorDescription(context.getString(R.string.limit_text));
        } else if(foto.equals("")){
            errorsMsj.setErrorDescription(context.getString(R.string.foto_required));
        }  else if(ubicacion.latitude == 0 && ubicacion.longitude == 0){
            errorsMsj.setErrorDescription(context.getString(R.string.location_required));
        } else if(hours <= 0){
            errorsMsj.setErrorDescription(String.format(context.getString(R.string.horario_required),nombre));
        } else{
            errorsMsj.setErrorDescription(context.getString(R.string.success));
        }
        return errorsMsj.getErrorDescription();
    }

    public String ErrorsMessage(int Error,String local){
        errorsMsj.setErrorId(Error);
        switch (errorsMsj.getErrorId()){
            case 1:
                errorsMsj.setErrorDescription(String.format(context.getString(R.string.user_registered),local));
                break;
            case 2:
                errorsMsj.setErrorDescription(context.getString(R.string.location_required));
                break;
            case 3:
                errorsMsj.setErrorDescription(context.getString(R.string.schedule_required));
                break;
            case 4:
                errorsMsj.setErrorDescription(context.getString(R.string.image_required));
                break;
            case 5:
                errorsMsj.setErrorDescription(context.getString(R.string.error_register));
                break;
            case 6:
                errorsMsj.setErrorDescription(String.format(context.getString(R.string.not_found_results),local));
                break;
            case 7:
                errorsMsj.setErrorDescription(context.getString(R.string.gps_desactived));
                break;
            case 8:
                errorsMsj.setErrorDescription(context.getString(R.string.delete_ask));
                break;
            case 9:
                errorsMsj.setErrorDescription(context.getString(R.string.internet_error));
                break;
            case 10:
                errorsMsj.setErrorDescription(context.getString(R.string.user_errors));
                break;
            case 11:
                errorsMsj.setErrorDescription(String.format(context.getString(R.string.error_update_schedule),local));
                break;
            case 12:
                errorsMsj.setErrorDescription(context.getString(R.string.error_delete));
                break;
            case 13:
                errorsMsj.setErrorDescription(String.format(context.getString(R.string.store_repited),local));
                break;
            case 14:
                errorsMsj.setErrorDescription(String.format(context.getString(R.string.nit_repited),local));
                break;
            case 15:
                errorsMsj.setErrorDescription(String.format(context.getString(R.string.error_delete_store),local));
                break;
        }
        return errorsMsj.getErrorDescription();
    }

    //Obtiene una lista con las aplicaciones instaladas y verifica que exista Google Maps,Waze, Petal maps o Uber
    public boolean CheckMaps() {
        boolean encontrado = false;
        try {
            final PackageManager pm = context.getPackageManager();
            @SuppressLint("QueryPermissionsNeeded") List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            for (ApplicationInfo packageInfo : packages) {
                if (packageInfo.packageName.contains("com.google.android.apps.maps") ||
                        packageInfo.packageName.contains("com.waze") ||
                        packageInfo.packageName.contains("com.huawei.maps.app") ||
                        packageInfo.packageName.contains("com.ubercab")) {
                    encontrado = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            encontrado = false;
        }
        return encontrado;
    }

    //Carga una imagen en el CircleImageView por String
    public void LoadImage(CircleImageView imagen, String photo) {
        try {
            Glide.with(context)
                    .load(photo)
                    .placeholder(LOGO_APP)
                    .fitCenter()
                    .into(imagen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Carga una imagen en el CircleImageView por una de las imagenes de la app
    public void LoadImageViewDrawable(ImageView imagen, String photo) {
        try {
            Glide.with(context)
                    .load(photo)
                    .placeholder(LOGO_APP)
                    .fitCenter()
                    .into(imagen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Obtiene la imagen tomada por la cámara para usar cómo foto
    public Uri getImageFromPath(String fileName, Context context) {
        File path = new File(context.getExternalFilesDir(""), MAIN_FOLDER + "/" + IMAGES_FOLDER);
        Uri imagens;
        if (!path.exists()) path.mkdirs();
        File image = new File(path, fileName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imagens = getUriForFile(context, FILEPROVIDER, image);
        } else {
            imagens = Uri.fromFile(image);
        }
        return imagens;
    }

    public Drawable getIcons(MaterialDrawableBuilder.IconValue icono, int Colors) {
        return MaterialDrawableBuilder.with(context) // provide a context
                .setIcon(icono) // provide an icon
                .setColor(Colors) // set the icon color
                .setToActionbarSize() // set the icon size
                .build(); // Finally call build
    }

    public boolean DeleteFoto(String nombreFoto, String Folder) {
        File archivo_error;
        String extStorageDirectory;
        boolean Borrado = false;
        if (MemorySDCard()) {
            extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        } else {
            extStorageDirectory = context.getExternalFilesDir("").toString();
        }
        archivo_error = new File(extStorageDirectory + "/" + MAIN_FOLDER + "/" + Folder + "/" + nombreFoto);
        if (archivo_error.exists()) {
            archivo_error.delete();
            Borrado = true;
        }
        return Borrado;
    }

    public boolean DeleteFoto(String nombreFoto) {
        File archivo_error;
        boolean Borrado = false;
        archivo_error = new File(nombreFoto);
        if (archivo_error.exists()) {
            archivo_error.delete();
            Borrado = true;
        }
        return Borrado;
    }

    public boolean MemorySDCard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            String state = Environment.getExternalStorageState();
            return state.equals(Environment.MEDIA_MOUNTED) || state.equals(
                    Environment.MEDIA_MOUNTED_READ_ONLY);
        } else {
            return false;
        }
    }

    public boolean CreateFolderExternal() {
        try {
            File path = new File(Environment.getExternalStorageDirectory(), MAIN_FOLDER);
            File path2 = new File(Environment.getExternalStorageDirectory(), MAIN_FOLDER + "/" + IMAGES_FOLDER);
            if (!path.exists()) {
                path.mkdirs();
            }
            if (!path2.exists()) {
                path2.mkdirs();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean CreateFolderInternalApp(Context context) {
        try {
            File path = new File(context.getExternalFilesDir(""), MAIN_FOLDER);
            File path2 = new File(context.getExternalFilesDir(""), MAIN_FOLDER + "/" + IMAGES_FOLDER);
            if (!path.exists()) {
                path.mkdirs();
            }
            if (!path2.exists()) {
                path2.mkdirs();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String generateKey() {
        return UUID.randomUUID().toString();
    }

}
