package com.codebook.persistence;

/**
 * Created by Archish on 12/18/2015.
 */
public interface ScoreTable {
    String NAME="score";
    String CAT_ID="cat_id";
    String LEVEL_ID="level_id";
    String POINTS="points";
    String COINS="coins";


    String[] PROJECTION = new String[]{CAT_ID,LEVEL_ID,POINTS,COINS};
     String CREATE = "CREATE TABLE "+NAME+" ( "
             +CAT_ID + " REFERENCES "
             +CategoryTable.NAME + " ( "+CategoryTable.CAT_ID+" ),"
             +LEVEL_ID + " REFERENCES "
             +LevelTable.NAME + " ( "+LevelTable.LEVEL_ID+" ),"
             +POINTS+ " INTEGER NOT NULL ,"
             +COINS + " INTEGER NOT NULL );";

}
