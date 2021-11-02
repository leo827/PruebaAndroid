package com.firemix.storeapp;

import android.app.Application;

import Librarys.Globals;
import Librarys.Utils;


public class StoreApplication extends Application {
    private Utils utils;

    @Override
    public void onCreate() {
        super.onCreate();
        utils = Utils.getInstance(this);
        Globals.setSDCARD(utils.MemorySDCard());
    }
}