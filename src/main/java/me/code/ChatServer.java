package me.code;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ChatServer {

    private ReentrantReadWriteLock lock;
    private List<ServerClient> clients;
    private ServerSocket serverSocket;

    public ChatServer() throws IOException {
        this.lock = new ReentrantReadWriteLock();
        this.serverSocket = new ServerSocket(5000);
        this.clients = new ArrayList<>();
    }

    public void start() {
        try {
            while (true) {
                var clientSocket = serverSocket.accept();
                var client = new ServerClient(this, clientSocket);
                client.start();

                lock.writeLock().lock();
                clients.add(client);
                lock.writeLock().unlock();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ServerClient> getClients() {
        return clients;
    }

    public ReentrantReadWriteLock getLock() {
        return lock;
    }
}
