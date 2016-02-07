package com.codebook.persistence;

/**
 * Created by Narayan Acharya on 09/12/2015.
 */
public interface LevelTable {
    String NAME = "level";

    String CAT_ID = "cat_id";
    String LEVEL_ID = "level_id";
    String LEVEL_NAME = "level_name";
    String LOCK_STATUS = "lock_status";

    String[] PROJECTION = new String[]{CAT_ID, LEVEL_ID, LEVEL_NAME, LOCK_STATUS};

    String CREATE = "CREATE TABLE " + NAME + " ( "
            + CAT_ID + " REFERENCES "
            + CategoryTable.NAME + " ( " + CategoryTable.CAT_ID + " ), "
            + LEVEL_ID + " INTEGER NOT NULL, "
            + LEVEL_NAME + " TEXT NOT NULL, "
            + LOCK_STATUS + " INTEGER NOT NULL, "
            + " PRIMARY KEY ( " + CAT_ID + " , " + LEVEL_ID + " ) ); ";
}
