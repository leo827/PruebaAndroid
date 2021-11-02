package com.firemix.storeapp.Login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.firemix.storeapp.BaseActivity;
import com.firemix.storeapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginView extends BaseActivity {

    @BindView(R.id.usuario)
    EditText usaurio;
    @BindView(R.id.contrasena)
    EditText contra;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.contra)
    CheckBox ver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
    }
}
