package Librarys;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.firemix.storeapp.BaseActivity;
import com.firemix.storeapp.R;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import java.util.Objects;

public class Message extends BaseActivity {

    private static Message message;
    private final Context context;
    private final MessageActions messageActions;

    private Message(Context context, MessageActions actions) {
        this.context = context;
        this.messageActions = actions;
    }

    public static Message getInstance(Context context, MessageActions actions) {
        if (context != null) {
            message = new Message(context, actions);
        }
        return message;
    }

    public void MessageBox(String mensaje, @NonNull int res) {
        int raws;
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
        TextView text = view.findViewById(R.id.mensaje);
        LottieAnimationView anims = view.findViewById(R.id.icon);
        if(res == 0){
            raws = R.raw.ok;
        } else{
            raws = R.raw.infos;
        }
        anims.setAnimation(raws);
        text.setText(mensaje);
        toast.setGravity(Gravity.BOTTOM, 0, 80);
        if(mensaje.length() >= 30) {
            toast.setDuration(Toast.LENGTH_LONG);
        } else{
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.setView(view);
        toast.show();
    }

    public void BottomDialogGps(String title, String message){
        new BottomDialog.Builder(context)
                .setTitle(title)
                .setContent(message)
                .setPositiveText(context.getString(R.string.ok))
                .setNegativeText(context.getString(R.string.cancel))
                .setCancelable(false)
                .setPositiveBackgroundColorResource(R.color.primaryColor)
                .setPositiveTextColorResource(android.R.color.white)
                .onPositive(dialog -> {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(intent);
                })
                .setNegativeTextColorResource(R.color.material_drawer_primary_text)
                .onNegative(dialog -> {

                })
                .show();
    }

    public void BottomDialog(String title, String message){
        new BottomDialog.Builder(context)
                .setTitle(title)
                .setContent(message)
                .setPositiveText(context.getString(R.string.ok))
                .setNegativeText(context.getString(R.string.cancel))
                .setCancelable(false)
                .setPositiveBackgroundColorResource(R.color.primaryColor)
                .setPositiveTextColorResource(android.R.color.white)
                .onPositive(dialog -> {
                    messageActions.AcceptMessage();
                })
                .setNegativeTextColorResource(R.color.material_drawer_primary_text)
                .onNegative(dialog -> {
                    messageActions.CancelMessage();
                })
                .show();
    }

    public void BottomDialogDelete(String title, String message,int StoreId, int Position){
        new BottomDialog.Builder(context)
                .setTitle(title)
                .setContent(message)
                .setPositiveText(context.getString(R.string.ok))
                .setNegativeText(context.getString(R.string.cancel))
                .setCancelable(false)
                .setPositiveBackgroundColorResource(R.color.primaryColor)
                .setPositiveTextColorResource(android.R.color.white)
                .onPositive(dialog -> {
                    messageActions.DeleteTienda(StoreId,Position);
                })
                .setNegativeTextColorResource(R.color.material_drawer_primary_text)
                .onNegative(dialog -> {
                    messageActions.CancelMessage();
                })
                .show();
    }

    public void BottomDialogUpdate(String title, String message,int StoreId, int Position){
        new BottomDialog.Builder(context)
                .setTitle(title)
                .setContent(message)
                .setPositiveText(context.getString(R.string.ok))
                .setNegativeText(context.getString(R.string.cancel))
                .setCancelable(false)
                .setPositiveBackgroundColorResource(R.color.primaryColor)
                .setPositiveTextColorResource(android.R.color.white)
                .onPositive(dialog -> {
                    messageActions.UpdateTienda(StoreId,Position);
                })
                .setNegativeTextColorResource(R.color.material_drawer_primary_text)
                .onNegative(dialog -> {
                    messageActions.CancelMessage();
                })
                .show();
    }

    public interface MessageActions {
        void AcceptMessage();

        void CancelMessage();

        void DeleteTienda(int EmpresaId, int position);

        void UpdateTienda(int EmpresaId, int position);

    }
}
