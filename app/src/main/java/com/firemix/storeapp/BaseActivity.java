package com.firemix.storeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import Librarys.MemoryData.SharedData;
import Librarys.Message;
import Librarys.Utils;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class BaseActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 200;
    public SharedData sharedData;
    private Message message;
    private Utils utils;

    public BaseActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utils = Utils.getInstance(this);
        message = Message.getInstance(this, null);
        sharedData = SharedData.getInstance(this);
    }

    public boolean checkPermission() {
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result12 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result13 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        return result1 == PackageManager.PERMISSION_GRANTED && result12 == PackageManager.PERMISSION_GRANTED && result13 == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA, WRITE_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean ReadWrite = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                boolean ReadGPS = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                if (cameraAccepted && ReadWrite && ReadGPS) {
                    if (utils.MemorySDCard()) {
                        if (utils.CreateFolderExternal())
                            message.MessageBox(getString(R.string.permission_ok), 0);
                    } else {
                        if (utils.CreateFolderInternalApp(this))
                            message.MessageBox(getString(R.string.permission_ok), 0);
                    }
                } else {
                    message.MessageBox(getString(R.string.missing_per), 1);
                    if (shouldShowRequestPermissionRationale(READ_PHONE_STATE)) {
                        RequestPermission(getString(R.string.missing_permission),
                                (dialog, which) -> this.requestPermissions(new String[]{READ_PHONE_STATE, CAMERA, WRITE_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION},
                                        PERMISSION_REQUEST_CODE));
                    }
                }
            }
        }
    }

    public void RequestPermission(String msj, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(msj)
                .setPositiveButton(R.string.ok, okListener)
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }
}
