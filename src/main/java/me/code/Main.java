package me.code;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
//            var server = new ChatServer();
//            server.start();

            var client = new ChatClient();
            client.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}