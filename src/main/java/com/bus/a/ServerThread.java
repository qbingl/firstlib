package com.bus.a;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerThread extends Thread {

    private Socket socket;
    private String username;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream in = socket.getInputStream();
            byte[] buf = new byte[1024];
            while (true) {
                int len = in.read(buf);
                String word = new String(buf, 0, len);
                // r:aaa
                if (word.startsWith("r:")) {
                    username = word.substring(2);
                    Server.SOCKETS.put(username, socket);
                    toOne(socket, "welcome " + username);
                }
                // a:haha
                else if (word.startsWith("a:")) {
                    toAll(username + "对大家说:" + word.substring(2));
                }
                // o:aaa:hehe
                else if (word.startsWith("o:")) {
                    String[] temp = word.split(":");
                    String key = temp[1];
                    toOne(Server.SOCKETS.get(key), username + "悄悄地对你说:" + temp[2]);
                }
            }
        } catch (Exception e) {

        }
    }

    private void toOne(Socket socket, String word) throws IOException {
        socket.getOutputStream().write(word.getBytes());
    }

    private void toAll(String word) throws IOException {
        for (Socket socket : Server.SOCKETS.values()) {
            socket.getOutputStream().write(word.getBytes());
        }
    }
}
