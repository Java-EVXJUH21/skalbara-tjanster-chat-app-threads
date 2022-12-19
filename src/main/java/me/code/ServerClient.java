package me.code;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerClient implements Runnable {

    private ChatServer server;
    private Socket socket;
    private InputStream input;
    private byte[] buffer;

    public ServerClient(ChatServer server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.input = socket.getInputStream();
        this.buffer = new byte[1024];
    }

    public void start() {
        var thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                int count = input.read(buffer);

                server.getLock().readLock().lock();
                for (var client : server.getClients()) {
                    var output = client.socket.getOutputStream();
                    output.write(buffer, 0, count);
                    output.flush();
                }
                server.getLock().readLock().unlock();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
