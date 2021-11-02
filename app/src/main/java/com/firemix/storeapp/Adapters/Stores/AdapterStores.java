package com.firemix.storeapp.Adapters.Stores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firemix.storeapp.R;

import java.util.List;

import Librarys.DB.Models.Stores;
import Librarys.MemoryData.SharedData;
import Librarys.Utils;

import static Librarys.Globals.LOGIN_USER;

public class AdapterStores extends RecyclerView.Adapter<HolderStores> {
    private final List<Stores> tiendas;
    private final StoreAction storeAction;
    private final Utils utils;
    private final SharedData sharedData;
    private String Usuario = "";

    public AdapterStores(Context ctx, List<Stores> horario, StoreAction action) {
        this.tiendas = horario;
        this.storeAction = action;
        utils = Utils.getInstance(ctx);
        sharedData = SharedData.getInstance(ctx);
        Usuario = sharedData.getStringData(LOGIN_USER);
    }

    @NonNull
    @Override
    public HolderStores onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_design, null);
        return new HolderStores(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderStores holder, int position) {
        final Stores datos = tiendas.get(position);
        String name = datos.getLocal_name();
        String[] nombres = name.split(" ");
        if (nombres.length > 1) {
            holder.nombre.setText(String.format("%s %s", nombres[0], nombres[1]));
        } else {
            holder.nombre.setText(name);
        }
        holder.descripcion.setText(datos.getDescription_store());
        utils.LoadImageViewDrawable(holder.foto, datos.getImage_store());
        if (datos.getUser_store().equals(Usuario)) {
            holder.editar.setVisibility(View.VISIBLE);
            holder.eliminar.setVisibility(View.VISIBLE);
        } else {
            holder.editar.setVisibility(View.GONE);
            holder.eliminar.setVisibility(View.GONE);
        }
        holder.editar.setOnClickListener(v -> storeAction.editStore(datos.getStoreId(), position));
        holder.eliminar.setOnClickListener(v -> storeAction.deleteStore(datos.getStoreId(), position));
        holder.cards.setOnClickListener(v -> storeAction.loadDetails(datos.getStoreId()));
    }

    public String Nombre(int pos) {
        return tiendas.get(pos).getLocal_name();
    }

    public String Imagen(int pos) {
        return tiendas.get(pos).getImage_store();
    }

    public String StoresSchedule(int pos) {
        return tiendas.get(pos).getStoreIdSchedule();
    }

    @Override
    public int getItemCount() {
        return tiendas.size();
    }

    public interface StoreAction {
        void editStore(int Id, int position);

        void deleteStore(int Id, int position);

        void loadDetails(int Id);
    }
}
