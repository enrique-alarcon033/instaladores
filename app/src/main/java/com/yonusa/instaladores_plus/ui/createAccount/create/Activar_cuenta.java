package com.yonusa.instaladores_plus.ui.createAccount.create;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.yonusa.instaladores_plus.R;

public class Activar_cuenta extends AppCompatActivity {
    EditText codigo;
    Button activar;
    LottieAnimationView loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activar_cuenta2);
    }
}