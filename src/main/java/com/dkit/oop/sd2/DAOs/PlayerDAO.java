package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.PlayerDTO;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.util.Comparator;
import java.util.List;
/**
 * Main author: Michael Atagamen
 * Other contributors:
 *
 */
public interface PlayerDAO {
    List<PlayerDTO> getAllPlayers() throws DaoException;
    PlayerDTO getPlayerById(int playerId) throws DaoException;
    void insertPlayer(PlayerDTO player) throws DaoException;
    void updatePlayer(PlayerDTO player) throws DaoException;
    void deletePlayer(int playerId) throws DaoException;

    List<PlayerDTO> findPlayersUsingFilter(Comparator<PlayerDTO> comparator) throws DaoException;
}
