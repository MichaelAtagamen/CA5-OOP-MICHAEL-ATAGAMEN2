package com.dkit.oop.sd2.Server;

/**
 * Main author: Michael Atagamen
 * Other contributors:
 *
 */
import java.sql.SQLException;

public class DaoException extends SQLException
{
    public DaoException()
    {
        // not used
    }

    public DaoException(String aMessage)
    {
        super(aMessage);
    }
}
