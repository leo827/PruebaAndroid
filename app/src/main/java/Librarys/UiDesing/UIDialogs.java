package Librarys.UiDesing;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.firemix.storeapp.BaseActivity;
import com.firemix.storeapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UIDialogs extends BaseActivity {
    @Nullable
    @BindView(R.id.camera)
    LinearLayout camera;
    @Nullable
    @BindView(R.id.gallery)
    LinearLayout gallery;
    private Context ctx;
    private static UIDialogs uiDialogs;
    private UIActions uiActions;

    public interface UIActions{
        public void LoadAction(int op);
    }

    private UIDialogs(Context context,UIActions actions) {
        this.ctx = context;
        this.uiActions = actions;
    }

    //Inicializador de la clase
    public static UIDialogs getInstance(Context context,UIActions actions) {
        if (context != null) {
            uiDialogs = new UIDialogs(context,actions);
        }
        return uiDialogs;
    }

    //Cantidad
    public void ShowFotoSelect(Activity activity) {
        try {
            Dialog fotos = new Dialog(activity);
            fotos.setCancelable(true);
            fotos.setContentView(R.layout.select_image);
            ButterKnife.bind(this, fotos);
            camera.setOnClickListener(v -> {
                uiActions.LoadAction(0);
                fotos.dismiss();
            });

            gallery.setOnClickListener(v -> {
                uiActions.LoadAction(1);
                fotos.dismiss();
            });

            fotos.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            fotos.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
