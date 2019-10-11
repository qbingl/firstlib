package com.bus.a;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    public static final Map<String, Socket> SOCKETS = new HashMap<>();

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(10086);
        System.out.println("server bind " + 10086);
        while (true) {
            Socket socket = server.accept();//阻塞方法
            new ServerThread(socket).start();
        }
        //server.close();
    }
}
