package com.firemix.storeapp.Register;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firemix.storeapp.BaseActivity;
import com.firemix.storeapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterView extends BaseActivity {
    @BindView(R.id.usuario)
    EditText usaurio;
    @BindView(R.id.contrasena)
    EditText contra;
    @BindView(R.id.repetir_contrasena)
    EditText repetir;
    @BindView(R.id.register)
    Button registrar;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.imageError)
    ImageView imagenError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);
        ButterKnife.bind(this);
    }
}
