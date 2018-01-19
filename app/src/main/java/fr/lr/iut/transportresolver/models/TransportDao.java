package fr.lr.iut.transportresolver.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;


/**
 * Data Access Object to transports table
 *
 * @author Jesús Daniel Medina Cruz
 * @since 07/12/2017
 */

@Dao
public interface TransportDao {
    /**
     * Inserts a transport into the table.
     *
     * @param transport A new transport.
     */
    @Insert
    long insert(Transport transport);

    /**
     * Updates a transport.
     *
     * @param transport The transport to update.
     */
    @Update
    int update(Transport transport);

    /**
     * Deletes a transport.
     *
     * @param id The transport id.
     */
    @Query("delete from " + DBC.Transport.TABLE_NAME + " where " + DBC.Transport.ID + " = :id")
    int deleteById(String id);

    /**
     * Deletes all transports
     */
    @Query("delete from " + DBC.Transport.TABLE_NAME)
    int deleteAll();

    /**
     * Selects all transports
     */
    @Query("select * from " + DBC.Transport.TABLE_NAME)
    Cursor selectAll();

    /**
     * Selects a transport by transport id
     *
     * @param id The transport id
     */
    @Query("select * from " + DBC.Transport.TABLE_NAME + " where " + DBC.Transport.ID + " = :id")
    Cursor selectById(int id);
}
