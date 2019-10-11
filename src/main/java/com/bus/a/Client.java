package com.bus.a;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 10086);

        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        Scanner scan = new Scanner(System.in);

        class ClientThread extends Thread {
            public void run() {
                byte[] buf = new byte[1024];
                try {
                    while (true) {
                        int len = in.read(buf);
                        System.out.println("服务器说:" + new String(buf, 0, len));
                    }
                } catch (Exception e) {

                }
            }
        }

        new ClientThread().start();

        // r:aaa
        // a:haha
        // o:aaa:hehe
        while (true) {
            String word = scan.next();
            out.write(word.getBytes());
            if (word.equals("bye")) {
                break;
            }
        }

    }
}
