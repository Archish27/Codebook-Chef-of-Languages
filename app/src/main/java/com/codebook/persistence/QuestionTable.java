package com.codebook.persistence;

/**
 * Created by Narayan Acharya on 30/09/2015.
 */
public interface QuestionTable {

    String NAME = "question";

    String CAT_ID = "cat_id";
    String LEVEL_ID = "level_id";
    String Q_ID = "question_id";
    String Q_TEXT = "question_text";
    String Q_PRGM = "question_prgm";
    String OPT_A = "option_a";
    String OPT_B = "option_b";
    String OPT_C = "option_c";
    String OPT_D = "option_d";

    String[] PROJECTION = new String[]{CAT_ID, LEVEL_ID, Q_ID, Q_TEXT, Q_PRGM, OPT_A, OPT_B, OPT_C, OPT_D};

    String CREATE = "CREATE TABLE " + NAME + " ( "
            + CAT_ID + " REFERENCES  "
            + CategoryTable.NAME + " ( " + CategoryTable.CAT_ID + " ), "
            + LEVEL_ID + " REFERENCES  "
            + LevelTable.NAME + " ( " + LevelTable.LEVEL_ID + " ), "
            + Q_ID + " INTEGER NOT NULL , "
            + Q_TEXT + " TEXT NOT NULL, "
            + Q_PRGM + " TEXT NOT NULL, "
            + OPT_A + " TEXT NOT NULL, "
            + OPT_B + " TEXT NOT NULL, "
            + OPT_C + " TEXT NOT NULL, "
            + OPT_D + " TEXT NOT NULL, "
            + " PRIMARY KEY ( " + CAT_ID + " , " + LEVEL_ID + " , " + Q_ID + " ) ); ";

}
