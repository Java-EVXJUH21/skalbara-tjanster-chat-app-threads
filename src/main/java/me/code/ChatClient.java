package me.code;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private Socket socket;
    private InputStream input;
    private OutputStream output;
    private byte[] buffer;

    public ChatClient() throws IOException {
        this.socket = new Socket("localhost", 5000);
        this.input = socket.getInputStream();
        this.output = socket.getOutputStream();
        this.buffer = new byte[1024];
    }

    public void start() {
        var thread = new Thread(() -> {
            try {
                while (true) {
                    int count = input.read(buffer);
                    var s = new String(buffer, 0, count);
                    System.out.println(s);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        thread.start();

        var scanner = new Scanner(System.in);
        while (true) {
            var line = scanner.nextLine();
            try {
                output.write(line.getBytes());
                output.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
