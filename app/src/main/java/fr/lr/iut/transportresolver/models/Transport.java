package fr.lr.iut.transportresolver.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

/**
 * The transports table structure
 *
 * @author Jesús Daniel Medina Cruz
 * @since 07/12/2017
 */
@Entity(tableName = DBC.Transport.TABLE_NAME)
public class Transport {
    // attributes
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DBC.Transport.ID)
    public int id;

    public String number;
    public int capacity;
    public String color;

    public Transport() {
        super();
    }

    @Ignore
    public Transport(int id, String number, int capacity, String color) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
        this.color = color;
    }

    @Ignore
    public Transport(ContentValues contentValues) {

        try {
            id = contentValues.getAsInteger(DBC.Transport.ID);
        } catch (NullPointerException e) {
        }
        number = contentValues.getAsString(DBC.Transport.NUMBER);
        capacity = contentValues.getAsInteger(DBC.Transport.CAPACITY);
        color = contentValues.getAsString(DBC.Transport.COLOR);
    }

    @Ignore
    public Transport(Cursor c) {
        this.id = c.getInt(c.getColumnIndexOrThrow(DBC.Transport.ID));
        this.number = c.getString(c.getColumnIndexOrThrow(DBC.Transport.NUMBER));
        this.capacity = c.getInt(c.getColumnIndexOrThrow(DBC.Transport.CAPACITY));
        this.color = c.getString(c.getColumnIndexOrThrow(DBC.Transport.COLOR));
    }
}
