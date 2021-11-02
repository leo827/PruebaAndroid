package com.firemix.storeapp.Adapters.Stores;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firemix.storeapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolderStores extends RecyclerView.ViewHolder {
    @BindView(R.id.imagen)
    ImageView foto;

    @BindView(R.id.Nombre)
    TextView nombre;

    @BindView(R.id.descripcion)
    TextView descripcion;

    @BindView(R.id.card)
    CardView cards;

    @BindView(R.id.eliminar)
    LinearLayout eliminar;

    @BindView(R.id.editar)
    LinearLayout editar;

    public HolderStores(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
