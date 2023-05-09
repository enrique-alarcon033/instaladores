package com.yonusa.instaladores_plus.utilities.socket;

import com.yonusa.instaladores_plus.utilities.catalogs.CMD_Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class SocketTCP {

    /**
     * Connection Parameters
    */
    private String ipAddress;
    private static final int PORT = 9001;
    private static final int TIMEOUT = 15000;
    private InetAddress serverAddr;

    /**
     * Variables to connect
     */
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private static SocketTCP refSocket;
    private setResultTCP callback;

    public SocketTCP(String ipAddress, setResultTCP callback) throws IOException {
            this.ipAddress = ipAddress;
            this.serverAddr = InetAddress.getByName(ipAddress);
            this.socket = new Socket();
            socket.setTcpNoDelay(true);
            this.callback = callback;
        }

        public static SocketTCP getSocketConnection(String ipAddress, boolean isNew,setResultTCP callback) throws IOException {
            if (isNew) {
                refSocket = new SocketTCP(ipAddress,callback);
            }
            return refSocket;
        }

        public void connectWithServer() {
            try {
                if (socket == null) {
                    System.out.println("Creating socket instance...");
                    socket = new Socket();
                    out = new PrintWriter(socket.getOutputStream());
                    socket.setTcpNoDelay(true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                }
                if (!socket.isConnected()) {
                    System.out.println("---->" + refSocket.serverAddr.getHostAddress() + "--" + ipAddress);
                    System.out.println("Connecting to server..." + serverAddr + ":" + PORT);
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(serverAddr, PORT), TIMEOUT);
                    socket.setTcpNoDelay(true);
                    System.out.println("Connected to server..." + socket.getRemoteSocketAddress());
                    out = new PrintWriter(socket.getOutputStream());
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } else {
                    System.out.println("---->" + refSocket.serverAddr.getHostAddress() + "--" + ipAddress);
                    System.out.println("Already connected to server..." + socket.getRemoteSocketAddress());
                    if (out != null) {
                        refSocket = new SocketTCP(ipAddress, callback);
                    }
                }

            } catch (SocketTimeoutException exception) {
                // Output expected SocketTimeoutExceptions.
                System.out.println("Timeout");
                callback.onResultTCP(false);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void disConnectWithServer() {
            System.out.println("A cerrar el socket");
            if (socket.isConnected()) {
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("El socket no esta conectado");
            }
        }

        public void sendDataWithString(String message) {
            System.out.println("[SOCKET] message to be sent: " + message);
            if (message != null) {
                setTimeout(20000);
                if (out != null) {
                    out.write(message);
                    out.flush();
                    System.out.println("message sent: " + message);
                } else {
                    System.out.println("message not sent: " + message);
                }

            }
        }

        public void setTimeout(int timeout) {
            if (socket != null && socket.isConnected()) {
                try {
                    socket.setSoTimeout(timeout);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
        }

        public String receiveDataFromServer() {
            try {
                String message = "";
                System.out.println("Message received: " + message);
                if (in != null) {
                    while ((message = in.readLine()) != null) {
                        System.out.println("command received");
                        message = message.trim();
                        break;
                    }
                }
                System.out.println("Message received: " + message);
                return message;
            } catch (Exception e) {
                return "Error:  " + e.getMessage();
            }
        }


        public String sendCMD1() {
            String cmdScan = CMD_Socket.CMD_SCAN;
            sendDataWithString(cmdScan);
            return cmdScan;
        }

        public String SendRegister(String cmd) {
            sendDataWithString(cmd+"\r\n");
            return cmd;
        }

       public interface setResultTCP {
            void  onResultTCP(boolean b);
        }
}
