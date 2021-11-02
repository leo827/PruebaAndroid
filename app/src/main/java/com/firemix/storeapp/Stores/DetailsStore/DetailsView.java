package com.firemix.storeapp.Stores.DetailsStore;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.firemix.storeapp.BaseActivity;
import com.firemix.storeapp.R;
import com.google.android.gms.maps.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsView extends BaseActivity {

    @BindView(R.id.mapa)
    MapView mapa;
    @BindView(R.id.back)
    Button volver;
    @BindView(R.id.origen)
    TextView origen;
    @BindView(R.id.Nombre)
    TextView nombre;
    @BindView(R.id.descripcion)
    TextView descripcion;
    @BindView(R.id.Nit)
    TextView nit;
    @BindView(R.id.imagen)
    ImageView imagen;
    @BindView(R.id.horarios)
    RecyclerView horarios;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_maps);
        ButterKnife.bind(this);
    }
}
