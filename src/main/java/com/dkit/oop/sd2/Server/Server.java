package com.dkit.oop.sd2.Server;

import com.dkit.oop.sd2.Server.DaoException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server is running. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Handle client request in a separate thread
                Thread thread = new Thread(() -> {
                    try (ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                         ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {

                        // Read entity ID from client
                        int entityId = (int) inputStream.readObject();

                        // Process request (retrieve entity from DAO)
                        PlayerDAO playerDao = new MySQLPlayerDAO(); // Initialize your DAO
                        PlayerDTO entity = playerDao.getPlayerById(entityId);

                        // Serialize entity to JSON and send to client
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String jsonEntity = gson.toJson(entity);
                        outputStream.writeObject(jsonEntity);

                    } catch (IOException | ClassNotFoundException | DaoException e) {
                        System.out.println("Error handling client request: " + e.getMessage());
                    }
                });

                thread.start(); // Start handling client request
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
