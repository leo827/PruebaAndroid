package com.firemix.storeapp.Stores.DetailsStore;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firemix.storeapp.Adapters.Schedules.AdaptersSchedules;
import com.firemix.storeapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Librarys.DB.DataBaseStores;
import Librarys.DB.Models.Schedules;
import Librarys.DB.Models.Stores;
import Librarys.Message;
import Librarys.Utils;

public class DetailsModel extends DetailsView implements OnMapReadyCallback, View.OnClickListener, AdaptersSchedules.ScheduleAction {
    Bundle savedInstanceStates;
    private FusedLocationProviderClient client;
    private MarkerOptions markersp;
    private Message message;
    private LatLng longs;
    private boolean loads = false;
    private double Lats = 0;
    private double Longs = 0;
    private DataBaseStores db;
    private int ID = 0;
    private Utils utils;
    private AdaptersSchedules adapter;
    private String tiendaN;
    private final ArrayList<Schedules> horas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceStates = savedInstanceState;
        utils = Utils.getInstance(this);
        db = DataBaseStores.getInstance(this);
        client = LocationServices.getFusedLocationProviderClient(this);
        mapa.onCreate(savedInstanceState);
        mapa.onResume();
        adapter = new AdaptersSchedules(this, horas, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        horarios.setHasFixedSize(true);
        horarios.setLayoutManager(linearLayoutManager);
        horarios.setAdapter(adapter);
        message = Message.getInstance(this, null);
        Intent d = getIntent();
        volver.setOnClickListener(this);
        if (d != null) {
            ID = d.getIntExtra("ID", 0);
            LoadInformation(ID);
        }
        volver.setOnClickListener(this);
    }

    private void LoadInformation(int Id) {
        List<Stores> tienda = db.getStoresId(Id);
        for (Stores st : tienda) {
            nombre.setText(st.getLocal_name());
            descripcion.setText(st.getDescription_store());
            nit.setText(st.getNit());
            utils.LoadImageViewDrawable(imagen, st.getImage_store());
            String IdStore = st.getStoreIdSchedule();
            List<Schedules> schedules = db.getSchedules(IdStore);
            horas.addAll(schedules);
            tiendaN = st.getLocal_name();
            adapter.notifyDataSetChanged();
            Lats = st.getLocation_latitud();
            Longs = st.getLocation_longitud();
            toolbar.setTitle("Detalles de: " + tiendaN);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mapa.onCreate(savedInstanceStates);
            mapa.onResume();
            mapa.getMapAsync(this);
            if (!loads) {
                Loads(3000);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
        }
    }

    private void Loads(long time) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            mapa.getMapAsync(this);
        }, time);
    }

    private String NombreDireccion(double Lat, double Long) {
        String partida = null;
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> origens = geocoder.getFromLocation(Lat, Long, 1);
            if (origens.size() > 0) {
                partida = origens.get(0).getAddressLine(0);
                origen.setText(partida);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return partida;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (googleMap != null) {
            googleMap.getUiSettings().setRotateGesturesEnabled(false);
            googleMap.getUiSettings().setZoomControlsEnabled(false);
            googleMap.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(false);
            googleMap.getUiSettings().setAllGesturesEnabled(false);

            Task<Location> task = client.getLastLocation();
            task.addOnCompleteListener(location -> {
                if (location.isComplete()) {
                    if (location.getResult() != null) {
                        if (Lats != 0 && Longs != 0) {
                            longs = new LatLng(Lats, Longs);
                        } else {
                            longs = new LatLng(location.getResult().getLatitude(), location.getResult().getLongitude());
                        }
                        markersp = new MarkerOptions().position(longs).title(tiendaN);
                        markersp.draggable(false);
                        googleMap.clear();
                        loads = true;
                        googleMap.addMarker(markersp);
                        NombreDireccion(longs.latitude, longs.longitude);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(longs, 20));
                    }
                }
            }).addOnFailureListener(e -> {
                message.MessageBox(e.getMessage(), 1);
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapa != null) {
            mapa.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapa != null) {
            mapa.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapa != null) {
            mapa.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapa != null) {
            mapa.onLowMemory();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void EditHour(long desde, long hasta, int Id) {

    }
}
