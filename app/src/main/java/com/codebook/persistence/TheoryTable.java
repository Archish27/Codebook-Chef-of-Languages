package com.codebook.persistence;

/**
 * Created by Narayan Acharya on 09/12/2015.
 */
public interface TheoryTable {
    String NAME = "theory";


    String THEORY_ID = "theory_id";
    String TITLE_NAME = "title";
    String THEORY="code";



    String[] PROJECTION = new String[]{THEORY_ID, TITLE_NAME, THEORY};

    String CREATE = "CREATE TABLE " + NAME + " ( "
            + THEORY_ID + " INTEGER NOT NULL, "
            + TITLE_NAME + " TEXT NOT NULL, "
            + THEORY + " TEXT NOT NULL, "
            + " PRIMARY KEY ( " + THEORY_ID + " ) ); ";
}
