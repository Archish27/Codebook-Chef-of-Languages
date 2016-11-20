package com.codebook.persistence;

/**
 * Created by Archish on 30/09/2015.
 */
public interface CategoryTable {
    String NAME = "category";

    String CAT_ID = "cat_id";
    String CAT_NAME = "cat_name";

    String[] PROJECTION = new String[]{CAT_ID, CAT_NAME};


    String CREATE = "CREATE TABLE " + NAME + " ( "
            + CAT_ID + " INTEGER PRIMARY KEY , "
            + CAT_NAME + " TEXT NOT NULL ); ";

}
