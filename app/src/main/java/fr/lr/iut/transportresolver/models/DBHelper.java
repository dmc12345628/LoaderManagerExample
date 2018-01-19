package fr.lr.iut.transportresolver.models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * The database creator
 *
 * @author Jesús Daniel Medina Cruz
 * @since 07/12/2017
 */

@Database(entities = {Transport.class}, version = 1)
public abstract class DBHelper extends RoomDatabase {
    @SuppressWarnings("WeakerAccess")
    public abstract TransportDao transportDao();

    // The only instance
    private static DBHelper instance;

    /**
     * Gets the singleton instance of SampleDatabase.
     *
     * @param context The context.
     * @return The singleton instance of SampleDatabase.
     */
    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), DBHelper.class, DBC.DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
}
