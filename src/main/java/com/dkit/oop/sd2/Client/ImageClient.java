package com.dkit.oop.sd2.Client;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ImageClient {
    private static final int SERVER_PORT = 5001;
    private static final String SERVER_HOST = "localhost";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            // Request list of available image file names from server
            List<String> imageFiles = (List<String>) inputStream.readObject();
            System.out.println("Available Image Files:");
            for (int i = 0; i < imageFiles.size(); i++) {
                System.out.println((i + 1) + ". " + imageFiles.get(i));
            }

            // Prompt user to choose an image file to download
            System.out.print("Enter the number of the image file to download: ");
            int choice = scanner.nextInt();
            String selectedImage = imageFiles.get(choice - 1);

            // Send the selected image file name to the server
            outputStream.writeObject(selectedImage);

            // Receive image file data from server and save to local file
            int imageSize = inputStream.readInt();
            byte[] imageData = new byte[imageSize];
            inputStream.readFully(imageData);

            // Save image data to a local file
            String savePath = "downloaded_images/" + selectedImage;
            FileOutputStream fileOutputStream = new FileOutputStream(savePath);
            fileOutputStream.write(imageData);
            fileOutputStream.close();

            System.out.println("Image downloaded successfully: " + savePath);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error communicating with server: " + e.getMessage());
        }
    }
}

