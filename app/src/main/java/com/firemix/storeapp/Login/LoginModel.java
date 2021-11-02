package com.firemix.storeapp.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.firemix.storeapp.R;
import com.firemix.storeapp.Register.RegisterModel;
import com.firemix.storeapp.Stores.LoadsStores.StoresModel;

import java.util.ArrayList;

import Librarys.DB.DataBaseStores;
import Librarys.DB.Models.Users;
import Librarys.Message;
import Librarys.Utils;

import static Librarys.Globals.LOGIN_ESTATE;
import static Librarys.Globals.LOGIN_USER;

public class LoginModel extends LoginView implements View.OnClickListener {
    ArrayList<EditText> Textos = new ArrayList<>();
    private Message message;
    private Utils utils;
    private DataBaseStores db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DataBaseStores.getInstance(this);
        message = Message.getInstance(this, null);
        utils = Utils.getInstance(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        boolean login1 = sharedData.getBooleanData(LOGIN_ESTATE);
        ver.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                contra.setTransformationMethod(null);
                ver.setText(getString(R.string.not_seen_pass));
            } else {
                contra.setTransformationMethod(new PasswordTransformationMethod());
                ver.setText(getString(R.string.ocultar));
            }
        });
        contra.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                ValidarLogin();
                handled = true;
            }
            return handled;
        });

        if (login1) {
            Intent n = new Intent(this, StoresModel.class);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.login) {
            ValidarLogin();
        }
        if (id == R.id.register) {
            Intent n = new Intent(this, RegisterModel.class);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            finish();
        }
    }

    private void ValidarLogin() {
        Textos.clear();
        Textos.add(usaurio);
        Textos.add(contra);
        String resp = utils.ValidateLogin(Textos);
        if (!resp.equals(getString(R.string.success))) {
            message.MessageBox(resp, 1);
        } else {
            Validate(Textos);
        }
    }

    private void Validate(ArrayList<EditText> datos) {
        String user = datos.get(0).getText().toString().trim().toLowerCase();
        String pass = datos.get(1).getText().toString().trim();
        Users users = new Users();
        users.setUser(user);
        users.setPass(pass);
        if (db.getUsers(users).size() > 0) {
            sharedData.SaveData(LOGIN_USER, user, false, 0, 0);
            sharedData.SaveData(LOGIN_ESTATE, "", true, 0, 1);
            message.MessageBox(String.format(getString(R.string.welcome), usaurio.getText().toString().trim()), 0);
            Intent n = new Intent(this, StoresModel.class);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            finish();
        } else {
            message.MessageBox(utils.ErrorsMessage(10, user), 1);
        }
    }
}
