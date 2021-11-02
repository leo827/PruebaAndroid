package com.firemix.storeapp.Stores.Registers;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firemix.storeapp.BaseActivity;
import com.firemix.storeapp.R;
import com.google.android.gms.maps.MapView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterStoreView extends BaseActivity {
    @BindView(R.id.mapa)
    MapView mapa;
    @BindView(R.id.name)
    EditText Name;
    @BindView(R.id.nit)
    EditText Nit;
    @BindView(R.id.desription)
    EditText Descripcion;
    @BindView(R.id.LoadLocation)
    LinearLayout locationMap;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.load)
    FloatingActionButton load;
    @BindView(R.id.lunes)
    CheckBox Lunes;
    @BindView(R.id.martes)
    CheckBox Martes;
    @BindView(R.id.miercoles)
    CheckBox Miercoles;
    @BindView(R.id.jueves)
    CheckBox Jueves;
    @BindView(R.id.viernes)
    CheckBox Viernes;
    @BindView(R.id.sabado)
    CheckBox Sabado;
    @BindView(R.id.domingo)
    CheckBox Domingo;
    @BindView(R.id.days)
    RecyclerView horas;
    @BindView(R.id.foto)
    CircleImageView foto;
    @BindView(R.id.alerts)
    TextView alertas;
    @BindView(R.id.nombre)
    TextView info_name;
    @BindView(R.id.image_info)
    ImageView img_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_store);
        ButterKnife.bind(this);
    }
}
