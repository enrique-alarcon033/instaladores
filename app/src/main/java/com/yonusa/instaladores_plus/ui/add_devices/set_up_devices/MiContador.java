package com.yonusa.instaladores_plus.ui.add_devices.set_up_devices;

import android.os.CountDownTimer;

public class MiContador extends CountDownTimer {

    public MiContador(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onFinish() {
        //Lo que quieras hacer al finalizar

    }

    @Override
    public void onTick(long millisUntilFinished) {
        //texto a mostrar en cuenta regresiva en un textview
        //countdownText.setText((millisUntilFinished/1000+""));

    }
}