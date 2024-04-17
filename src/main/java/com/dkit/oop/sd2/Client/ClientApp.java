package com.dkit.oop.sd2.Client;

import com.dkit.oop.sd2.Server.MySQLPlayerDAO;
import com.dkit.oop.sd2.Server.PlayerDAO;
import com.dkit.oop.sd2.Server.PlayerDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.List;

public class ClientApp {
    private static final int SERVER_PORT = 5000; // Assuming server port is 5000

    public static void main(String[] args) {
        try {
            // Instantiate MenuSystem with appropriate DAO (e.g., MySQLPlayerDAO)
            PlayerDAO playerDao = new MySQLPlayerDAO(); // Use appropriate DAO implementation
            MenuSystem menuSystem = new MenuSystem(playerDao);

            // Run the menu system
            menuSystem.runMenu();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void displayAllEntities() {
        try (Socket socket = new Socket("localhost", SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            // Send request type to server (e.g., 7 for displaying all entities)
            outputStream.writeObject(7);

            // Receive JSON string representing all entities
            String entitiesJson = (String) inputStream.readObject();

            // Parse JSON string back into a list of entities
            Gson gson = new Gson();
            Type listType = new TypeToken<List<PlayerDTO>>() {}.getType();
            List<PlayerDTO> allEntities = gson.fromJson(entitiesJson, listType);

            // Display all entities
            System.out.println("All Entities:");
            for (PlayerDTO entity : allEntities) {
                System.out.println(entity);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error communicating with server: " + e.getMessage());
        }
    }
}
