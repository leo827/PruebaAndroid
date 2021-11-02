package com.firemix.storeapp.Adapters.Schedules;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firemix.storeapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import Librarys.DB.Models.Schedules;

import static Librarys.Globals.HOUR_FORMAT;

public class AdaptersSchedules extends RecyclerView.Adapter<HolderSchedules> {
    private final Context context;
    private final List<Schedules> horarios;
    private final ScheduleAction scheduleAction;

    public AdaptersSchedules(Context ctx, List<Schedules> horario, ScheduleAction action) {
        this.context = ctx;
        this.horarios = horario;
        this.scheduleAction = action;
    }

    @NonNull
    @Override
    public HolderSchedules onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_design, null);
        return new HolderSchedules(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderSchedules holder, int position) {
        final Schedules datos = horarios.get(position);
        holder.dia.setText(datos.getDay());
        @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat(HOUR_FORMAT);
        String desde = formatter.format(datos.getOpen_hour());
        desde = desde.replace("a. m.", "AM");
        desde = desde.replace("p. m.", "PM");
        String hasta = formatter.format(datos.getClose_hour());
        hasta = hasta.replace("a. m.", "AM");
        hasta = hasta.replace("p. m.", "PM");
        holder.inicio.setText(desde);
        holder.fin.setText(hasta);
        holder.cards.setOnClickListener(v -> scheduleAction.EditHour(datos.getOpen_hour(), datos.getClose_hour(), datos.getScheduleId()));
    }

    @Override
    public int getItemCount() {
        return horarios.size();
    }

    public interface ScheduleAction {
        void EditHour(long desde, long hasta, int Id);
    }
}
