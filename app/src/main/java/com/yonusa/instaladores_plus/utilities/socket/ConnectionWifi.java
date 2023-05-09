package com.yonusa.instaladores_plus.utilities.socket;

import com.yonusa.instaladores_plus.api.ApiConstants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionWifi {

    public String sendData(String datos) {
        String ip = ApiConstants.CONFIG_WI_FI; //"172.16.0.54";
        String respuesta = "";
        int puerto = ApiConstants.PORT_WI_FI;
        try {
            Socket sk = new Socket(ip, puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            PrintWriter salida = new PrintWriter(new OutputStreamWriter(sk.getOutputStream()), true);

            salida.println(datos);
            salida.flush();

            Thread.sleep(5000);

            respuesta = entrada.readLine();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }

}
