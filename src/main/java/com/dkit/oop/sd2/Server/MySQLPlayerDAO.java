package com.dkit.oop.sd2.Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 * Main author: Michael Atagamen
 * Other contributors:
 *
 */
public class MySQLPlayerDAO extends MySqlDao implements PlayerDAO {

    @Override
    public List<PlayerDTO> getAllPlayers() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PlayerDTO> playersList = new ArrayList<>();

        try {
            connection = this.getConnection();
            String query = "SELECT * FROM Player";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int playerId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                float rating = resultSet.getFloat("rating");
                int age = resultSet.getInt("age");

                PlayerDTO player = new PlayerDTO(playerId, name, rating, age);
                playersList.add(player);
            }
        } catch (SQLException e) {
            throw new DaoException("getAllPlayers() " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("Error closing resources: " + e.getMessage());
            }
        }

        return playersList;
    }

    @Override
    public PlayerDTO getPlayerById(int playerId) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Player WHERE id = ?")) {

            preparedStatement.setInt(1, playerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    float rating = resultSet.getFloat("rating");
                    int age = resultSet.getInt("age");
                    return new PlayerDTO(playerId, name, rating, age);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("getPlayerById() error: " + e.getMessage());
        }
        return null; // Player not found
    }


    @Override
    public void insertPlayer(PlayerDTO player) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.getConnection();
            String query = "INSERT INTO Player (name, rating, age) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, player.getName());
            preparedStatement.setFloat(2, player.getRating());
            preparedStatement.setInt(3, player.getAge());

            // Execute the SQL statement to insert the player into the database
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DaoException("Insert player failed, no rows affected.");
            }

            System.out.println("Player inserted successfully.");

        } catch (SQLException e) {
            throw new DaoException("insertPlayer() error: " + e.getMessage());
        } finally {
            // Close the PreparedStatement first
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new DaoException("Error closing PreparedStatement: " + e.getMessage());
            }

            // Then close the Connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new DaoException("Error closing Connection: " + e.getMessage());
            }
        }
    }


    @Override
    public void updatePlayer(PlayerDTO player) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE Player SET name = ?, rating = ?, age = ? WHERE id = ?")) {

            preparedStatement.setString(1, player.getName());
            preparedStatement.setFloat(2, player.getRating());
            preparedStatement.setInt(3, player.getAge());
            preparedStatement.setInt(4, player.getId()); // Assuming PlayerDTO has an 'id' field

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DaoException("Update player failed, no rows affected.");
            }

            System.out.println("Player updated successfully.");

        } catch (SQLException e) {
            throw new DaoException("updatePlayer() error: " + e.getMessage());
        }
    }


    @Override
    public void deletePlayer(int playerId) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Player WHERE id = ?")) {

            preparedStatement.setInt(1, playerId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DaoException("Delete player failed, no rows affected.");
            }

            System.out.println("Player deleted successfully.");

        } catch (SQLException e) {
            throw new DaoException("deletePlayer() error: " + e.getMessage());
        }
    }

    @Override
    public List<PlayerDTO> findPlayersUsingFilter(Comparator<PlayerDTO> comparator) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PlayerDTO> filteredPlayers = new ArrayList<>();

        try {
            connection = this.getConnection();
            String query = "SELECT * FROM Player";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int playerId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                float rating = resultSet.getFloat("rating");
                int age = resultSet.getInt("age");

                PlayerDTO player = new PlayerDTO(playerId, name, rating, age);
                filteredPlayers.add(player);
            }

            // Apply the comparator to sort the list
            filteredPlayers.sort(comparator);

        } catch (SQLException e) {
            throw new DaoException("findPlayersUsingFilter() error: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("Error closing resources: " + e.getMessage());
            }
        }

        return filteredPlayers;
    }
}


