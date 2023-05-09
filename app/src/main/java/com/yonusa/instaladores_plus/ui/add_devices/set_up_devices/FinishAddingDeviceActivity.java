package com.yonusa.instaladores_plus.ui.add_devices.set_up_devices;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.gusakov.library.PulseCountDown;
import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.ui.cercas.Lista_cercas;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FinishAddingDeviceActivity extends AppCompatActivity {
    private static final String TAG = "variable";
    private final String FROM_FINISH_ADD = "com.yonusa.instaladores_plus.ui.login.view.FROM_FINISH_ADD";
    Context context;
    TextView contador,texto;
    Button btnFinish;
    LottieAnimationView loader,success;

    private MiContador timer;
    private long lastCountDown = 30000; //Milliseconds for view ad
    private Boolean isCountDown = false;

    private Button buttonStart;
    private PulseCountDown pulseCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device_step4);
        getSupportActionBar().hide();

        loader = findViewById(R.id.loader_step4);
        success = findViewById(R.id.succes_lottie);
        btnFinish = findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(v -> toHomeScreen());
        contador= (TextView) findViewById(R.id.contador);
        texto = (TextView) findViewById(R.id.texto_conteo);

        // start countdown and add OnCountdownCompleted

        new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {

                // Used for formatting digit to be in 2 digits only

                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                contador.setText( f.format(sec));
            }

            // When the task is over it will print 00:00:00 there

            public void onFinish() {

                contador.setText("00");

            }

        }.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             //   mProgressDialog.dismiss();
           btnFinish.setVisibility(View.VISIBLE);
           texto.setText("Si tu Wifi no se conecto, favor de verificacar la contraseña del wifi que ingresaste, será necesario repetir el proceso de instalación");
                //finish();
            }
        }, 19000);


    }

    private void toHomeScreen() {
        btnFinish.setText("Espera...");
        runOnUiThread(()-> {
            try {
                Thread.sleep(2000);
                Intent i = new Intent(FinishAddingDeviceActivity.this, Lista_cercas.class);
                i.putExtra(FROM_FINISH_ADD,true);
                startActivity(i);
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }



}