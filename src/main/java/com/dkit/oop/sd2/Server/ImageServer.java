package com.dkit.oop.sd2.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImageServer {
    private static final int SERVER_PORT = 5001;
    private static final String IMAGE_DIRECTORY = "images/";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Image Server is running. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Handle client request in a separate thread
                Thread thread = new Thread(() -> {
                    try (ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                         ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())) {

                        // Send list of available image file names
                        List<String> imageFiles = getImageFileList();
                        outputStream.writeObject(imageFiles);

                        // Receive client's chosen image file name
                        String selectedImage = (String) inputStream.readObject();

                        // Send the selected image file to the client
                        sendImageFile(outputStream, selectedImage);

                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error handling client request: " + e.getMessage());
                    }
                });

                thread.start(); // Start handling client request
            }

        } catch (IOException e) {
            System.out.println("Image Server error: " + e.getMessage());
        }
    }

    private static List<String> getImageFileList() {
        List<String> imageFiles = new ArrayList<>();
        try {
            Path imageDirectory = Paths.get(IMAGE_DIRECTORY);
            Files.list(imageDirectory)
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .forEach(imageFiles::add);
        } catch (IOException e) {
            System.out.println("Error listing image files: " + e.getMessage());
        }
        return imageFiles;
    }

    private static void sendImageFile(ObjectOutputStream outputStream, String fileName) {
        try {
            Path imagePath = Paths.get(IMAGE_DIRECTORY + fileName);
            byte[] imageData = Files.readAllBytes(imagePath);

            // Send image file size and data to the client
            outputStream.writeInt(imageData.length);
            outputStream.write(imageData);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Error sending image file: " + e.getMessage());
        }
    }
}

