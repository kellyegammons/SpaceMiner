package com.kg.spaceminer.database;

public class PlayerTable {

    public static final String TABLE_PLAYER = "player";
    public static final String COLUMN_ID = "playerId";
    public static final String COLUMN_NAME = "playerName";
    public static final String COLUMN_LEVEL = "playerLevel";
    public static final String COLUMN_CREDITS = "playerCredits";
    public static final String COLUMN_FUEL = "playerFuel";


    public static final String[] ALL_COLUMNS =
            {COLUMN_ID, COLUMN_NAME, COLUMN_LEVEL, COLUMN_CREDITS, COLUMN_FUEL};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_PLAYER + "(" +
                    COLUMN_ID + " INTEGER," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_LEVEL + " INTEGER," +
                    COLUMN_CREDITS + " INTEGER," +
                    COLUMN_FUEL + " INTEGER" + ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_PLAYER;

    public static final String SQL_CREATE_PLAYER_SETTINGS =
            "INSERT INTO " + TABLE_PLAYER + " (" + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_LEVEL + ", " + COLUMN_CREDITS + ", " + COLUMN_FUEL + ") VALUES\n" +
                    "(1, 'Marina', 1, 0.0, 10);";

}
