package com.firemix.storeapp.Stores.Registers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.firemix.storeapp.Adapters.Schedules.AdaptersSchedules;
import com.firemix.storeapp.R;
import com.firemix.storeapp.Stores.MapsStore.MapStoreModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Librarys.DB.DataBaseStores;
import Librarys.DB.Models.Schedules;
import Librarys.DB.Models.Stores;
import Librarys.Message;
import Librarys.UiDesing.UIDialogs;
import Librarys.Utils;

import static Librarys.Globals.HR_FORMAT;
import static Librarys.Globals.IMAGES_FOLDER;
import static Librarys.Globals.LOGIN_USER;
import static Librarys.Globals.MAIN_FOLDER;
import static Librarys.Globals.MIN_FORMAT;

public class RegisterStoreModel extends RegisterStoreView implements View.OnClickListener, UIDialogs.UIActions, TimePickerDialog.OnTimeSetListener, AdaptersSchedules.ScheduleAction {
    private final int REQUEST_MAP = 124;
    private final int REQUEST_CAMERA = 125;
    private final int REQUEST_GALLERY = 126;
    private final Calendar desde = Calendar.getInstance();
    private final Calendar hasta = Calendar.getInstance();
    private LocationManager locationManager;
    private String name_file = null;
    private Uri file;
    private Message message;
    private Utils utils;
    private DataBaseStores db;
    private boolean LoadMap = false;
    private double Latitud = 0;
    private double Longitud = 0;
    private UIDialogs uiDialogs;
    private final ArrayList<Schedules> horarios = new ArrayList<>();
    private String Dia = "";
    private String StoreId = "";
    private int DayC = 0;
    private boolean editTime = false;
    private int SheduleIdS = 0;
    private AdaptersSchedules adapter;
    private String ImagenFinal = "";
    private String usuario = "";
    private final ArrayList<EditText> datos = new ArrayList<>();
    private int StoreIdFined = 0;
    private boolean editAll = false;
    private boolean editTimes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = DataBaseStores.getInstance(this);
        message = Message.getInstance(this, null);
        uiDialogs = UIDialogs.getInstance(this, this);
        utils = Utils.getInstance(this);
        mapa.onCreate(savedInstanceState);
        mapa.onResume();
        locationMap.setOnClickListener(this);
        load.setOnClickListener(this);
        save.setOnClickListener(this);
        StoreId = utils.generateKey();
        Intent d = getIntent();
        adapter = new AdaptersSchedules(this, horarios, this);
        usuario = sharedData.getStringData(LOGIN_USER);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        horas.setHasFixedSize(true);
        horas.setLayoutManager(linearLayoutManager);
        horas.setAdapter(adapter);

        Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = Name.getText().toString().trim();
                String[] nombres = name.split(" ");
                if (nombres.length > 1) {
                    info_name.setText(String.format("%s %s", nombres[0], nombres[1]));
                } else {
                    info_name.setText(name);
                }
                if (name.equals("")) {
                    info_name.setText(getString(R.string.wait));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Descripcion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int limit = 151;
                int cantidad = Descripcion.getText().toString().trim().length();
                String info = String.format(getString(R.string.cantidad), cantidad, limit);
                if (cantidad < limit) {
                    alertas.setText(info);
                    img_info.setBackground((getResources().getDrawable(R.drawable.ic_ok)));
                } else {
                    alertas.setText(getString(R.string.limit_text));
                    img_info.setBackground((getResources().getDrawable(R.drawable.ic_error)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (d != null) {
            StoreIdFined = d.getIntExtra("ID", 0);
            if (StoreIdFined != 0) {
                editAll = true;
                LoadData(StoreIdFined);
            }
        }

        Lunes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!editTime)
                    showTime("Lunes", Lunes);
            } else {
                if (DeleteSchedule(1)) {
                    LoadSchedules();
                } else {
                    message.MessageBox(utils.ErrorsMessage(12, ""), 1);
                }
            }
        });

        Martes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!editTime)
                    showTime("Martes", Martes);
            } else {
                if (DeleteSchedule(2)) {
                    LoadSchedules();
                } else {
                    message.MessageBox(utils.ErrorsMessage(12, ""), 1);
                }
            }
        });

        Miercoles.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!editTime)
                    showTime("Miércoles", Miercoles);
            } else {
                if (DeleteSchedule(3)) {
                    LoadSchedules();
                } else {
                    message.MessageBox(utils.ErrorsMessage(12, ""), 1);
                }
            }
        });

        Jueves.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!editTime)
                    showTime("Jueves", Jueves);
            } else {
                if (DeleteSchedule(4)) {
                    LoadSchedules();
                } else {
                    message.MessageBox(utils.ErrorsMessage(12, ""), 1);
                }
            }
        });

        Viernes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!editTime)
                    showTime("Viernes", Viernes);
            } else {
                if (DeleteSchedule(5)) {
                    LoadSchedules();
                } else {
                    message.MessageBox(utils.ErrorsMessage(12, ""), 1);
                }
            }
        });

        Sabado.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!editTime)
                    showTime("Sábado", Sabado);
            } else {
                if (DeleteSchedule(6)) {
                    LoadSchedules();
                } else {
                    message.MessageBox(utils.ErrorsMessage(12, ""), 1);
                }
            }
        });

        Domingo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!editTime)
                    showTime("Domingo", Domingo);
            } else {
                if (DeleteSchedule(7)) {
                    LoadSchedules();
                } else {
                    message.MessageBox(utils.ErrorsMessage(12, ""), 1);
                }
            }
        });
    }

    private void LoadData(int ID) {
        List<Stores> shop = db.getStoresId(ID);
        for (Stores st : shop) {
            Name.setText(st.getLocal_name());
            Descripcion.setText(st.getDescription_store());
            Nit.setText(st.getNit());
            ImagenFinal = st.getImage_store();
            utils.LoadImageViewDrawable(foto, st.getImage_store());
            StoreId = st.getStoreIdSchedule();
            Latitud = st.getLocation_latitud();
            Longitud = st.getLocation_longitud();
            editTimes = true;
            save.setText(getString(R.string.update));
        }

        Lunes.setChecked(db.CountDays(1, StoreId) > 0);
        Martes.setChecked(db.CountDays(2, StoreId) > 0);
        Miercoles.setChecked(db.CountDays(3, StoreId) > 0);
        Jueves.setChecked(db.CountDays(4, StoreId) > 0);
        Viernes.setChecked(db.CountDays(5, StoreId) > 0);
        Sabado.setChecked(db.CountDays(6, StoreId) > 0);
        Domingo.setChecked(db.CountDays(7, StoreId) > 0);

        LoadSchedules();
        String name = Name.getText().toString().trim();
        String[] nombres = name.split(" ");
        if (nombres.length > 1) {
            info_name.setText(String.format("%s %s", nombres[0], nombres[1]));
        } else {
            info_name.setText(name);
        }
        if (name.equals("")) {
            info_name.setText(getString(R.string.wait));
        }

        LoadMapa(Latitud, Longitud);
    }

    private void showTime(String day, CheckBox check) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setTabIndicators(getString(R.string.from), getString(R.string.to));
        tpd.setAccentColor(getResources().getColor(R.color.primaryColor));
        tpd.setOnCancelListener(dialogInterface -> {
            if (!editTime) {
                check.setChecked(false);
            }
        });
        Dia = day;
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    private void editTime(int hr, int min, int endhr, int minend, int scheduleId) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(hr),
                now.get(min),
                false
        );
        tpd.setStartTime(hr, min);
        tpd.setEndTime(endhr, minend);
        tpd.setTabIndicators(getString(R.string.from), getString(R.string.to));
        tpd.setAccentColor(getResources().getColor(R.color.floating_btn_edit));
        tpd.setOnCancelListener(dialogInterface -> {
        });
        editTime = true;
        SheduleIdS = scheduleId;
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    private void LoadMapa(double latitud, double longitud) {
        String local = Name.getText().toString().trim();
        mapa.getMapAsync(googleMap -> {
            googleMap.clear();
            googleMap.getUiSettings().setRotateGesturesEnabled(false);
            googleMap.getUiSettings().setZoomControlsEnabled(false);
            googleMap.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(false);
            googleMap.getUiSettings().setAllGesturesEnabled(false);
            LatLng kurla = new LatLng(latitud, longitud);
            googleMap.addMarker(new MarkerOptions().position(kurla).title(local));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(kurla, 15));
        });
    }

    private boolean getEstatusGPS() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void GetStateGPS() {
        if (!getEstatusGPS()) {
            message.BottomDialogGps(getString(R.string.gps_off), getString(R.string.msjgps));
        } else {
            if (LoadMap) {
                Intent locationm = new Intent(this, MapStoreModel.class);
                if (Latitud != 0 && Longitud != 0) {
                    locationm.putExtra("Latitud", Latitud);
                    locationm.putExtra("Longitud", Longitud);
                }
                startActivityForResult(locationm, REQUEST_MAP);
                LoadMap = false;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MAP) {
            if (resultCode == RESULT_OK) {
                Latitud = data.getDoubleExtra("Latitud", 0);
                Longitud = data.getDoubleExtra("Longitud", 0);
                LoadMapa(Latitud, Longitud);
            }
        }
        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            String extStorageDirectory;
            if (utils.MemorySDCard()) {
                extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            } else {
                extStorageDirectory = getExternalFilesDir("").toString();
            }
            ImagenFinal = extStorageDirectory + "/" + MAIN_FOLDER + "/" + IMAGES_FOLDER + "/" + name_file;
            LoadImagen(ImagenFinal);
        }
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            file = data.getData();
            Uri newUri = file;
            String uris = file.toString();
            if (uris.contains("content://com.google.android.apps.photos.contentprovider")) {
                String unusablePath = file.getPath();
                int startIndex = unusablePath.indexOf("external/");
                int endIndex = unusablePath.indexOf("/ORIGINAL/NONE/");
                String embeddedPath = unusablePath.substring(startIndex, endIndex);
                Uri.Builder builder = file.buildUpon();
                builder.path(embeddedPath);
                builder.authority("media");
                newUri = builder.build();
            }
            if (!ImagenFinal.equals("")) {
                utils.DeleteFoto(ImagenFinal, IMAGES_FOLDER);
            }
            ImagenFinal = newUri.toString();
            LoadImagen(ImagenFinal);
        }
    }

    private void LoadImagen(String image) {
        utils.LoadImage(foto, image);
    }

    private boolean DeleteSchedule(int days) {
        return db.DeleteScheduleDay(StoreId, days);
    }

    private void LoadSchedules() {
        List<Schedules> hrs = db.getSchedules(StoreId);
        horarios.clear();
        horarios.addAll(hrs);
        adapter.notifyDataSetChanged();
        editTime = false;
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            file = FileProvider.getUriForFile(this, getPackageName() + ".provider", getOutputMediaFile());
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            file = Uri.fromFile(getOutputMediaFile());
        }
        if (!ImagenFinal.equals("")) {
            utils.DeleteFoto(ImagenFinal, IMAGES_FOLDER);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private File getOutputMediaFile() {
        File mediaStorageDir = null;
        try {
            String extStorageDirectory;
            if (utils.MemorySDCard()) {
                extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            } else {
                extStorageDirectory = getExternalFilesDir("").toString();
            }
            mediaStorageDir = new File(extStorageDirectory + "/" + MAIN_FOLDER + "/" + IMAGES_FOLDER);
            name_file = System.currentTimeMillis() + ".jpg";
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert mediaStorageDir != null;
        return new File(mediaStorageDir.getPath() + File.separator + name_file);
    }

    private void LoadFromGallery() {
        if (!ImagenFinal.equals("")) {
            utils.DeleteFoto(ImagenFinal, IMAGES_FOLDER);
        }
        name_file = System.currentTimeMillis() + ".jpg";
        ImagenFinal = name_file;
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_GALLERY);
    }

    private void SaveData() {
        String Nombre = Name.getText().toString().trim();
        String Desc = Descripcion.getText().toString().trim();
        String NIT = Nit.getText().toString().trim();
        Stores stores = new Stores();
        stores.setLocal_name(Nombre);
        stores.setDescription_store(Desc);
        stores.setNit(NIT);
        stores.setImage_store(ImagenFinal);
        stores.setLocation_latitud(Latitud);
        stores.setLocation_longitud(Longitud);
        stores.setStoreIdSchedule(StoreId);
        stores.setUser_store(usuario);
        if (db.InsertStore(stores)) {
            Intent returnIntent = new Intent();
            returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            message.MessageBox(utils.ErrorsMessage(5, ""), 1);
        }
    }

    private void EditData() {
        String Nombre = Name.getText().toString().trim();
        String Desc = Descripcion.getText().toString().trim();
        String NIT = Nit.getText().toString().trim();
        Stores stores = new Stores();
        stores.setLocal_name(Nombre);
        stores.setDescription_store(Desc);
        stores.setNit(NIT);
        stores.setImage_store(ImagenFinal);
        stores.setLocation_latitud(Latitud);
        stores.setLocation_longitud(Longitud);
        stores.setStoreIdSchedule(StoreId);
        stores.setStoreId(StoreIdFined);
        stores.setUser_store(usuario);
        if (db.UpdateStore(stores)) {
            Intent returnIntent = new Intent();
            returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            message.MessageBox(utils.ErrorsMessage(11, Nombre), 1);
        }
    }

    private void ValidateRegister() {
        datos.clear();
        datos.add(Name);
        datos.add(Nit);
        datos.add(Descripcion);
        LatLng ubicacion = new LatLng(Latitud, Longitud);
        String resp = utils.ValidateRegistroTienda(datos, ImagenFinal, ubicacion, horarios.size());
        if (!resp.equals(getString(R.string.success))) {
            message.MessageBox(resp, 1);
        } else {
            if (editAll) {
                ValidateEdit(datos);
            } else {
                Validate(datos);
            }
        }
    }

    private void Validate(ArrayList<EditText> datos) {
        int cuentas = db.CountStores(datos.get(0).getText().toString().trim());
        int cuentas2 = db.CountStoresNit(datos.get(1).getText().toString().trim());
        String tienda = datos.get(0).getText().toString().trim().toLowerCase();
        String nit = datos.get(1).getText().toString().trim().toLowerCase();
        if (cuentas > 0) {
            message.MessageBox(utils.ErrorsMessage(13, tienda), 1);
        } else if (cuentas2 > 0) {
            message.MessageBox(utils.ErrorsMessage(14, nit), 1);
        } else {
            SaveData();
        }
    }

    private void ValidateEdit(ArrayList<EditText> datos) {
        int cuentas = db.CountStoresEq(StoreIdFined, datos.get(0).getText().toString().trim());
        int cuentas2 = db.CountStoresEqNit(StoreIdFined, (datos.get(1).getText().toString().trim()));
        String tienda = datos.get(0).getText().toString().trim().toLowerCase();
        String nit = datos.get(1).getText().toString().trim().toLowerCase();
        if (cuentas > 0) {
            message.MessageBox(utils.ErrorsMessage(13, tienda), 1);
        } else if (cuentas2 > 0) {
            message.MessageBox(utils.ErrorsMessage(14, nit), 1);
        } else {
            EditData();
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
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.LoadLocation) {
            if (!checkPermission()) {
                requestPermission();
            } else {
                LoadMap = true;
                GetStateGPS();
            }
        }

        if (id == R.id.load) {
            if (!checkPermission()) {
                requestPermission();
            } else {
                uiDialogs.ShowFotoSelect(this);
            }
        }

        if (id == R.id.save) {
            ValidateRegister();
        }
    }

    @Override
    public void LoadAction(int op) {
        if (op == 0) {
            takePicture();
        } else {
            LoadFromGallery();
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        desde.set(Calendar.HOUR_OF_DAY, hourOfDay);
        desde.set(Calendar.MINUTE, minute);
        desde.set(Calendar.SECOND, 0);
        desde.set(Calendar.MILLISECOND, 0);
        hasta.set(Calendar.HOUR_OF_DAY, hourOfDayEnd);
        hasta.set(Calendar.MINUTE, minuteEnd);
        hasta.set(Calendar.SECOND, 0);
        hasta.set(Calendar.MILLISECOND, 0);

        Schedules schedules = new Schedules();
        schedules.setDay(Dia);
        switch (Dia) {
            case "Lunes":
                DayC = 1;
                break;
            case "Martes":
                DayC = 2;
                break;
            case "Miércoles":
                DayC = 3;
                break;
            case "Jueves":
                DayC = 4;
                break;
            case "Viernes":
                DayC = 5;
                break;
            case "Sábado":
                DayC = 6;
                break;
            case "Domingo":
                DayC = 7;
                break;
        }
        schedules.setDay_count(DayC);
        schedules.setOpen_hour(desde.getTimeInMillis());
        schedules.setClose_hour(hasta.getTimeInMillis());
        schedules.setStoreId(StoreId);
        if (editTime) {
            schedules.setScheduleId(SheduleIdS);
            if (db.UpdateSchedule(schedules.getScheduleId(), schedules.getOpen_hour(), schedules.getClose_hour())) {
                LoadSchedules();
            } else {
                String Nombre = Name.getText().toString().trim();
                message.MessageBox(utils.ErrorsMessage(11, Nombre), 1);
            }
            editTime = false;
        } else {
            if (db.InsertSchedule(schedules)) {
                LoadSchedules();
            } else {
                message.MessageBox(utils.ErrorsMessage(5, ""), 1);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!editAll) {
            db.DeleteSchedule(StoreId);
        }
    }

    @Override
    public void EditHour(long desde, long hasta, int Id) {
        try {
            @SuppressLint("SimpleDateFormat") DateFormat hr_format = new SimpleDateFormat(HR_FORMAT);
            @SuppressLint("SimpleDateFormat") DateFormat min_format = new SimpleDateFormat(MIN_FORMAT);
            int hr_in = Integer.parseInt(hr_format.format(desde));
            int min_in = Integer.parseInt(min_format.format(desde));
            int hr_fin = Integer.parseInt(hr_format.format(hasta));
            int min_fin = Integer.parseInt(min_format.format(hasta));
            editTime = false;
            editTime(hr_in, min_in, hr_fin, min_fin, Id);
        } catch (Exception e) {
            message.MessageBox(e.getMessage(), 1);
        }
    }
}
