package com.dkit.oop.sd2.Client;

import com.dkit.oop.sd2.Server.MySQLPlayerDAO;
import com.dkit.oop.sd2.Server.PlayerDAO;
import com.dkit.oop.sd2.Server.PlayerDTO;
import com.dkit.oop.sd2.Server.DaoException;
import com.dkit.oop.sd2.Utilities.JsonConverter;
import com.google.gson.Gson;

import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Comparator;


/**
 * Main author: Michael Atagamen
 * Other contributors:
 *
 */


public class MenuSystem {
    private final PlayerDAO playerDao;
    private final Gson gsonParser;

    public MenuSystem(PlayerDAO playerDao) {
        this.playerDao = playerDao;
        this.gsonParser = new Gson();
    }

    // Method to convert a list of PlayerDTO objects to a JSON string
    public String playersListToJson(List<PlayerDTO> playerList) {
        return gsonParser.toJson(playerList);
    }

    public static void main(String[] args) {
        PlayerDAO playerDao = new MySQLPlayerDAO();
        MenuSystem menuSystem = new MenuSystem(playerDao);
        menuSystem.runMenu();
    }

    public void runMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int choice;
            do {
                System.out.println("\nMenu:");
                System.out.println("1. Display All Players");
                System.out.println("2. Display Player by ID");
                System.out.println("3. Add New Player");
                System.out.println("4. Update Player");
                System.out.println("5. Delete Player");
                System.out.println("6. Filter Players by Age (Ascending)");
                System.out.println("7. Display All Entities");
                System.out.println("8. Add New Entity");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        displayAllPlayers();
                        break;
                    case 2:
                        displayPlayerById(scanner);
                        break;
                    case 3:
                        addNewPlayer(scanner);
                        break;
                    case 4:
                        updatePlayer(scanner);
                        break;
                    case 5:
                        deletePlayer(scanner);
                        break;
                    case 6:
                        filterPlayersByAge();
                        break;
                    case 7:
                        displayAllEntities();
                        break;
                    case 8:
                        addNewEntity(scanner);
                        break;
                    case 0:
                        System.out.println("Exiting the Menu System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } while (choice != 0);
        } catch (DaoException | InputMismatchException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addNewEntity(Scanner scanner) throws DaoException {
        System.out.println("Adding a New Player:");

        System.out.print("Enter Player Name: ");
        String name = scanner.next();

        System.out.print("Enter Player Rating: ");
        float rating = scanner.nextFloat();

        System.out.print("Enter Player Age: ");
        int age = scanner.nextInt();

        // Create a new PlayerDTO object with the entered details
        PlayerDTO newPlayer = new PlayerDTO(name, rating, age);

        // Call the insertPlayer method of the PlayerDAO to add the new player to the database
        playerDao.insertPlayer(newPlayer);

        System.out.println("New Player added successfully.");
    }


    private void displayAllEntities() throws DaoException {
        List<PlayerDTO> entities = playerDao.getAllPlayers(); // Modify this to retrieve all entities
        if (entities.isEmpty()) {
            System.out.println("No entities found.");
        } else {
            System.out.println("All Entities:");
            for (PlayerDTO entity : entities) {
                System.out.println(entity);
            }
        }
    }

    private void displayAllPlayers() throws DaoException {
        List<PlayerDTO> players = playerDao.getAllPlayers();
        if (players.isEmpty()) {
            System.out.println("No players found.");
        } else {
            for (PlayerDTO player : players) {
                System.out.println(player);
            }
        }
    }

    private void displayPlayerById(Scanner scanner) throws DaoException {
        System.out.print("Enter Player ID: ");
        int playerId = scanner.nextInt();
        PlayerDTO player = playerDao.getPlayerById(playerId);
        if (player != null) {
            System.out.println("Player found: " + player);
        } else {
            System.out.println("Player not found with ID: " + playerId);
        }
    }

    private void addNewPlayer(Scanner scanner) throws DaoException {
        System.out.println("Adding a New Player:");

        System.out.print("Enter Player Name: ");
        String name = scanner.next();

        System.out.print("Enter Player Rating: ");
        float rating = scanner.nextFloat();

        System.out.print("Enter Player Age: ");
        int age = scanner.nextInt();

        // Create a new PlayerDTO object with the entered details
        PlayerDTO newPlayer = new PlayerDTO(name, rating, age);

        // Call the insertPlayer method of the PlayerDAO to add the new player to the database
        playerDao.insertPlayer(newPlayer);

        System.out.println("New Player added successfully.");
    }

    private void updatePlayer(Scanner scanner) throws DaoException {
        System.out.println("Updating Player:");

        try {
            // Prompt the user to enter the player ID to update
            System.out.print("Enter Player ID to update: ");
            int playerId = scanner.nextInt();

            // Consume newline character
            scanner.nextLine();

            // Check if the player with the specified ID exists
            PlayerDTO existingPlayer = playerDao.getPlayerById(playerId);
            if (existingPlayer == null) {
                System.out.println("Player not found with ID: " + playerId);
                return; // Exit the method if player not found
            }

            // Prompt the user to enter the new player details
            System.out.print("Enter new Player Name: ");
            String newName = scanner.nextLine();

            System.out.print("Enter new Player Rating: ");
            float newRating = scanner.nextFloat();

            System.out.print("Enter new Player Age: ");
            int newAge = scanner.nextInt();

            // Update the existing player with the new details
            existingPlayer.setName(newName);
            existingPlayer.setRating(newRating);
            existingPlayer.setAge(newAge);

            // Call the updatePlayer method of the PlayerDAO to update the player in the database
            playerDao.updatePlayer(existingPlayer);

            System.out.println("Player updated successfully.");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please enter valid input.");
            scanner.nextLine(); // Consume the invalid input to prevent infinite loop
        }
    }

    private void deletePlayer(Scanner scanner) throws DaoException {
        System.out.println("Deleting Player:");

        // Prompt the user to enter the player ID to delete
        System.out.print("Enter Player ID to delete: ");
        int playerId = scanner.nextInt();

        // Check if the player with the specified ID exists
        PlayerDTO existingPlayer = playerDao.getPlayerById(playerId);
        if (existingPlayer == null) {
            System.out.println("Player not found with ID: " + playerId);
            return; // Exit the method if player not found
        }

        // Confirm player deletion with the user
        System.out.print("Are you sure you want to delete this player? (Y/N): ");
        String confirmation = scanner.next();
        if (confirmation.equalsIgnoreCase("Y")) {
            // Delete the player from the database using the PlayerDAO
            playerDao.deletePlayer(playerId);
            System.out.println("Player deleted successfully.");
        } else {
            System.out.println("Player deletion canceled.");
        }
    }

    private void filterPlayersByAge() throws DaoException {
        // Define a comparator to sort players by age in ascending order
        Comparator<PlayerDTO> playerAgeComparator = Comparator.comparingInt(PlayerDTO::getAge);

        // Call the findPlayersUsingFilter method with the age comparator
        List<PlayerDTO> filteredPlayers = playerDao.findPlayersUsingFilter(playerAgeComparator);

        // Display the filtered list of players
        System.out.println("Filtered Players (sorted by age):");
        for (PlayerDTO player : filteredPlayers) {
            System.out.println(player);
        }
    }

    // Example method demonstrating usage:
    public void displayAllPlayersAsJson() {
        try {
            List<PlayerDTO> players = playerDao.getAllPlayers();
            String playersJson = JsonConverter.playersListToJson(players);
            System.out.println("Players List as JSON:\n" + playersJson);
        } catch (DaoException e) {
            System.out.println("Error retrieving players: " + e.getMessage());
        }
    }

    public void displayPlayerByIdAsJson(int playerId) {
        try {
            PlayerDTO player = playerDao.getPlayerById(playerId);
            if (player != null) {
                String playerJson = JsonConverter.playerToJson(player);
                System.out.println("Player as JSON:\n" + playerJson);
            } else {
                System.out.println("Player not found with ID: " + playerId);
            }
        } catch (DaoException e) {
            System.out.println("Error retrieving player: " + e.getMessage());
        }
    }
}
