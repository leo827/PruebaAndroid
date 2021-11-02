package com.firemix.storeapp.Register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.firemix.storeapp.Login.LoginModel;
import com.firemix.storeapp.R;

import java.util.ArrayList;

import Librarys.DB.DataBaseStores;
import Librarys.DB.Models.Users;
import Librarys.Message;
import Librarys.Utils;

public class RegisterModel extends RegisterView implements View.OnClickListener {
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
        registrar.setOnClickListener(this);
        contra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String Contra = contra.getText().toString().trim();
                String Comparar = repetir.getText().toString().trim();
                if (Contra.equals("")) {
                    imagenError.setBackground(getResources().getDrawable(R.drawable.ic_error));
                } else if (!Comparar.equals(Contra)) {
                    imagenError.setBackground(getResources().getDrawable(R.drawable.ic_error));
                } else if (contra.length() < 5) {
                    imagenError.setBackground(getResources().getDrawable(R.drawable.ic_error));
                } else {
                    imagenError.setBackground(getResources().getDrawable(R.drawable.ic_ok));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        repetir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String Contra = contra.getText().toString().trim();
                String Comparar = repetir.getText().toString().trim();
                if (Contra.equals("")) {
                    imagenError.setBackground(getResources().getDrawable(R.drawable.ic_error));
                } else if (!Comparar.equals(Contra)) {
                    imagenError.setBackground(getResources().getDrawable(R.drawable.ic_error));
                } else if (repetir.length() < 5) {
                    imagenError.setBackground(getResources().getDrawable(R.drawable.ic_error));
                } else {
                    imagenError.setBackground(getResources().getDrawable(R.drawable.ic_ok));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        repetir.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                ValidateRegister();
                handled = true;
            }
            return handled;
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.register) {
            ValidateRegister();
        }
        if (id == R.id.login) {
            Intent n = new Intent(this, LoginModel.class);
            n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(n);
            finish();
        }
    }

    private void ValidateRegister() {
        Textos.clear();
        Textos.add(usaurio);
        Textos.add(contra);
        Textos.add(repetir);
        String resp = utils.ValidateRegistro(Textos);
        if (!resp.equals(getString(R.string.success))) {
            message.MessageBox(resp, 1);
        } else {
            Validate(Textos);
        }
    }

    private void Validate(ArrayList<EditText> datos) {
        int cuentas = db.CountUsers(datos.get(0).getText().toString().trim());
        String user = datos.get(0).getText().toString().trim().toLowerCase();
        String pass = datos.get(1).getText().toString().trim();
        if (cuentas > 0) {
            message.MessageBox(utils.ErrorsMessage(1, user), 1);
        } else {
            Users users = new Users();
            users.setUser(user);
            users.setPass(pass);
            if (db.InsertUser(users)) {
                message.MessageBox(getString(R.string.register_success), 0);
                Intent n = new Intent(this, LoginModel.class);
                n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(n);
                finish();
            } else {
                message.MessageBox(utils.ErrorsMessage(5, user), 1);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent n = new Intent(this, LoginModel.class);
        n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(n);
        finish();
    }
}
