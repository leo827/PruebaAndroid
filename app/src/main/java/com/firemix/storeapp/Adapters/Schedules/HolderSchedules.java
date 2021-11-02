package com.firemix.storeapp.Adapters.Schedules;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firemix.storeapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolderSchedules extends RecyclerView.ViewHolder {
    @BindView(R.id.dia)
    TextView dia;

    @BindView(R.id.desde)
    TextView inicio;

    @BindView(R.id.hasta)
    TextView fin;

    @BindView(R.id.card)
    CardView cards;

    public HolderSchedules(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
