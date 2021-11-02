package com.firemix.storeapp.Stores.MapsStore;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.firemix.storeapp.BaseActivity;
import com.firemix.storeapp.R;
import com.google.android.gms.maps.MapView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapStoreView extends BaseActivity {
    @BindView(R.id.mapa)
    MapView mapa;
    @BindView(R.id.Confirmar)
    Button save;
    @BindView(R.id.center)
    FloatingActionButton center;
    @BindView(R.id.origen)
    TextView origen;
    @BindView(R.id.icons)
    CoordinatorLayout iconos;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_location);
        ButterKnife.bind(this);
    }
}
