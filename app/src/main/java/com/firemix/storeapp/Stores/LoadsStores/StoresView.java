package com.firemix.storeapp.Stores.LoadsStores;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.ferfalk.simplesearchview.SimpleSearchView;
import com.firemix.storeapp.BaseActivity;
import com.firemix.storeapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skyfishjy.library.RippleBackground;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoresView extends BaseActivity {

    @BindView(R.id.add)
    FloatingActionButton agregar;
    @BindView(R.id.agregar)
    Button nuevo;
    @BindView(R.id.subtittle)
    TextView subtitle;
    @BindView(R.id.stores)
    RecyclerView stores;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.searchView)
    SimpleSearchView search;
    @BindView(R.id.mensajeC)
    RelativeLayout mensajes;
    @BindView(R.id.content)
    RippleBackground content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_stores);
        ButterKnife.bind(this);
    }
}
