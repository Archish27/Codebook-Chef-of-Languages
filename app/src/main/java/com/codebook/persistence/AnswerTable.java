package com.codebook.persistence;

/**
 * Created by Archish on 12/18/2015.
 */
public interface AnswerTable {
    String NAME="answer";
    String CAT_ID="cat_id";
    String LEVEL_ID="level_id";
    String QUESTION_ID = "q_id";
    String ANSWER = "answer";
    String ATTEMPTED = "attempted";
    String CORRECT = "correct";
    String TIME = "time";
    String[] PROJECTION = new String[]{CAT_ID,LEVEL_ID,QUESTION_ID,ANSWER, ATTEMPTED,CORRECT,TIME};

    String CREATE = "CREATE TABLE " + NAME + " ( "
            +CAT_ID + " REFERENCES "
            +QuestionTable.NAME+ " ( "+QuestionTable.CAT_ID + " ),"
            +LEVEL_ID + " REFERENCES "
            +QuestionTable.NAME+ " ( "+QuestionTable.LEVEL_ID+" ),"
            + QUESTION_ID + " REFERENCES "
            + QuestionTable.NAME + " ( "+ QuestionTable.Q_ID + " ), "
            + ANSWER + " INT NOT NULL , "
            + ATTEMPTED + " INTEGER NOT NULL ,"
            + CORRECT +" INTEGER NOT NULL ,"
            + TIME + " REAL NOT NULL );";
}
