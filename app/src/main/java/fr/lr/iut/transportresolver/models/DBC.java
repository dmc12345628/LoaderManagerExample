package fr.lr.iut.transportresolver.models;

/**
 * The Database constants
 *
 * @author Jesús Daniel Medina Cruz
 * @since 07/12/2017
 */

public abstract class DBC {
    public static final String DATABASE_NAME = "Transports";

    public static abstract class Transport {
        public static final String
                TABLE_NAME = "transports",
                ID = "_id",
                NUMBER = "number",
                CAPACITY = "capacity",
                COLOR = "color",
                CREATE_TABLE = "create table "
                        + TABLE_NAME
                        + " (" + ID + " integer primary key, "
                        + NUMBER + " text unique not null, "
                        + CAPACITY + " number not null, "
                        + COLOR + " text not null);",
                SELECT_ALL = "select * from transports;",
                DROP_TABLE = "drop table if exists " + TABLE_NAME;
        public static final String[] PROJECTION = {
                ID, NUMBER, CAPACITY, COLOR
        };
    }
}
