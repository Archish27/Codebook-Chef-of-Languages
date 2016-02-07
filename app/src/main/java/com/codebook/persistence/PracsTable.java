package com.codebook.persistence;

/**
 * Created by Narayan Acharya on 09/12/2015.
 */
public interface PracsTable {
    String NAME = "practicals";


    String PRACS_ID = "pracs_id";
    String TITLE_NAME = "title";
    String CODE="code";
    String OUTPUT="output";


    String[] PROJECTION = new String[]{PRACS_ID, TITLE_NAME, CODE, OUTPUT};

    String CREATE = "CREATE TABLE " + NAME + " ( "
            + PRACS_ID + " INTEGER NOT NULL, "
            + TITLE_NAME + " TEXT NOT NULL, "
            + CODE + " TEXT NOT NULL, "
            + OUTPUT +" TEXT NOT NULL, "
            + " PRIMARY KEY ( " + PRACS_ID + " ) ); ";
}
