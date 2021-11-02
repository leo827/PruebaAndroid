package com.firemix.storeapp.Stores.MapsStore;

import android.Manifest;
import android.app.Activity;
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

import com.firemix.storeapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import Librarys.Message;

public class MapStoreModel extends MapStoreView implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMapClickListener {
    private FusedLocationProviderClient client;
    private MarkerOptions markersp;
    private Message message;
    private LatLng longs;
    private boolean loads = false;
    private GoogleMap maps;
    private double Lats = 0;
    private double Longs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapa.onCreate(savedInstanceState);
        mapa.onResume();
        message = Message.getInstance(this, null);
        client = LocationServices.getFusedLocationProviderClient(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent d = getIntent();
        if (d != null) {
            Lats = d.getDoubleExtra("Latitud", 0);
            Longs = d.getDoubleExtra("Longitud", 0);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mapa.onCreate(savedInstanceState);
            mapa.onResume();
            mapa.getMapAsync(this);
            if (!loads) {
                Loads(3000);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
        }
        center.setOnClickListener(this);
        save.setOnClickListener(this);
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

    private void getCurrentLocation() {
        mapa.getMapAsync(this);
    }

    private void Loads(long time) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (!loads)
                mapa.getMapAsync(this);
        }, time);
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
    public void onMapReady(GoogleMap googleMap) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (googleMap != null) {
            maps = googleMap;
            googleMap.getUiSettings().setRotateGesturesEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(true);
            googleMap.getUiSettings().setAllGesturesEnabled(true);

            Task<Location> task = client.getLastLocation();
            task.addOnCompleteListener(location -> {
                if (location.isComplete()) {
                    if (location.getResult() != null) {
                        if (Lats != 0 && Longs != 0) {
                            longs = new LatLng(Lats, Longs);
                            message.MessageBox(getString(R.string.recover_location), 0);
                        } else {
                            longs = new LatLng(location.getResult().getLatitude(), location.getResult().getLongitude());
                        }
                        markersp = new MarkerOptions().position(longs).title("Ubicación");
                        markersp.draggable(true);
                        googleMap.clear();
                        center.setEnabled(true);
                        loads = true;
                        googleMap.addMarker(markersp);
                        NombreDireccion(longs.latitude, longs.longitude);
                        googleMap.setOnMapClickListener(this);
                        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                            @Override
                            public void onMarkerDragStart(Marker marker) {
                            }

                            @Override
                            public void onMarkerDrag(Marker marker) {
                            }

                            @Override
                            public void onMarkerDragEnd(Marker marker) {
                                longs = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                                markersp = new MarkerOptions().position(longs).title("Ubicación");
                                googleMap.clear();
                                markersp.draggable(true);
                                googleMap.addMarker(markersp);
                                NombreDireccion(longs.latitude, longs.longitude);
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(longs, 20));
                            }
                        });
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(longs, 20));
                        iconos.setVisibility(View.GONE);
                    }
                }
            }).addOnFailureListener(e -> {
                iconos.setVisibility(View.GONE);
                message.MessageBox(e.getMessage(), 1);
            });
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.center) {
            getCurrentLocation();
        }
        if (id == R.id.Confirmar) {
            if (longs != null) {
                Intent returnIntent = new Intent();
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                returnIntent.putExtra("Latitud", longs.latitude);
                returnIntent.putExtra("Longitud", longs.longitude);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            } else {
                message.MessageBox(getString(R.string.missin_coordinates), 1);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        longs = latLng;
        markersp = new MarkerOptions().position(longs).title("Ubicación");
        markersp.draggable(true);
        maps.clear();
        maps.addMarker(markersp);
        NombreDireccion(longs.latitude, longs.longitude);
        maps.animateCamera(CameraUpdateFactory.newLatLngZoom(longs, 20));
    }
}
