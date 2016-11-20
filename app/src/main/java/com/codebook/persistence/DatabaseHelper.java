package com.codebook.persistence;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.codebook.R;
import com.codebook.models.Answer;
import com.codebook.models.Category;
import com.codebook.models.Level;
import com.codebook.models.Question;
import com.codebook.models.Score;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Created by Archish on 30/10/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";

    private static final String DB_NAME = "quiz";
    private static final String DB_SUFFIX = ".db";
    private static final int DB_VERSION = 1;

    private Resources resources;

    private static DatabaseHelper dbInstance;

    /**
     * Get DatabaseHelper instance to carry out Database operations/transactions
     *
     * @param context Context of the requesting component
     * @return DatabaseHelper instance
     */
    public static DatabaseHelper getDbInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new DatabaseHelper(context);
        }
        return dbInstance;
    }

    /**
     * Singleton Constructor
     *
     * @param context Context of the requesting component
     */
    private DatabaseHelper(Context context) {
        super(context, DB_NAME + DB_SUFFIX, null, DB_VERSION);
        resources = context.getResources();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CategoryTable.CREATE);
        db.execSQL(LevelTable.CREATE);
        db.execSQL(QuestionTable.CREATE);
        db.execSQL(AnswerTable.CREATE);
        db.execSQL(PracsTable.CREATE);
        db.execSQL(TheoryTable.CREATE);

        db.execSQL(ScoreTable.CREATE);
        preFillDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoryTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LevelTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AnswerTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PracsTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TheoryTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ScoreTable.NAME);
        onCreate(db);
    }

    private void preFillDatabase(SQLiteDatabase db) {
        // Get All Quiz Data from JSON file in the raw folder
        db.beginTransaction();
        try {
            fillCategoriesLevelsQuestionsTable(db);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

    }


    private void fillCategoriesLevelsQuestionsTable(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        try {
            JSONArray jsonArray = new JSONArray(readDataFromResources());
            JSONObject category;
            for (int i = 0; i < jsonArray.length(); i++) {
                category = jsonArray.getJSONObject(i);
                final int catId = category.getInt(JSONAttributes.CAT_ID);
                fillCategory(db, values, category);

                final JSONArray levels = category.getJSONArray(JSONAttributes.LEVELS);
                fillLevelsForCategory(db, values, levels, catId);
                JSONObject level;
                for (int j = 0; j < levels.length(); j++) {
                    level = levels.getJSONObject(j);
                    final int levelId = level.getInt(JSONAttributes.LEVEL_ID);
                    final JSONArray questions = level.getJSONArray(JSONAttributes.QUESTIONS);
                    fillQuestionsForLevel(db, values, questions, levelId, catId);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readDataFromResources() throws IOException {
        StringBuilder modulesJSON = new StringBuilder();
        InputStream rawCategories = resources.openRawResource(R.raw.leveltest);
        BufferedReader reader = new BufferedReader(new InputStreamReader(rawCategories));
        String line;

        while ((line = reader.readLine()) != null) {
            modulesJSON.append(line);
        }
        return modulesJSON.toString();
    }

    private void fillCategory(SQLiteDatabase db, ContentValues values, JSONObject category) throws JSONException {
        values.clear();
        values.put(CategoryTable.CAT_ID, category.getInt(JSONAttributes.CAT_ID));
        values.put(CategoryTable.CAT_NAME, category.getString(JSONAttributes.CAT_NAME));
        db.insert(CategoryTable.NAME, null, values);

    }

    private void fillLevelsForCategory(SQLiteDatabase db, ContentValues values, JSONArray levels, int catId) throws JSONException {
        JSONObject level;
        for (int i = 0; i < levels.length(); i++) {
            level = levels.getJSONObject(i);
            values.clear();
            values.put(LevelTable.CAT_ID, catId);
            values.put(LevelTable.LEVEL_ID, level.getInt(JSONAttributes.LEVEL_ID));
            values.put(LevelTable.LEVEL_NAME, level.getString(JSONAttributes.LEVEL_NAME));
            values.put(LevelTable.LOCK_STATUS, level.getInt(JSONAttributes.LOCK_STATUS));
            db.insert(LevelTable.NAME, null, values);
            fillScore(db, values, catId, level.getInt(JSONAttributes.LEVEL_ID));
        }
    }

    private void fillQuestionsForLevel(SQLiteDatabase db, ContentValues values, JSONArray questions, int levelId, int catId)
            throws JSONException {
        JSONObject question;
        for (int i = 0; i < questions.length(); i++) {
            question = questions.getJSONObject(i);
            values.clear();
            values.put(QuestionTable.CAT_ID, catId);
            values.put(QuestionTable.LEVEL_ID, levelId);
            values.put(QuestionTable.Q_ID, question.getInt(JSONAttributes.Q_ID));
            values.put(QuestionTable.Q_TEXT, question.getString(JSONAttributes.Q_TEXT));
            values.put(QuestionTable.Q_PRGM, question.getString(JSONAttributes.Q_PRGM));
            values.put(QuestionTable.OPT_A, question.getString(JSONAttributes.OPT_A));
            values.put(QuestionTable.OPT_B, question.getString(JSONAttributes.OPT_B));
            values.put(QuestionTable.OPT_C, question.getString(JSONAttributes.OPT_C));
            values.put(QuestionTable.OPT_D, question.getString(JSONAttributes.OPT_D));
            db.insert(QuestionTable.NAME, null, values);
            fillAnswersForQuestions(db, values, question, catId, levelId, question.getInt(JSONAttributes.Q_ID));
        }
    }

    private void fillAnswersForQuestions(SQLiteDatabase db, ContentValues values, JSONObject questions, int cat_id, int level_id, int quizId)
            throws JSONException {
        values.clear();
        values.put(AnswerTable.CAT_ID, cat_id);
        values.put(AnswerTable.LEVEL_ID, level_id);
        values.put(AnswerTable.QUESTION_ID, quizId);
        values.put(AnswerTable.ANSWER, questions.getString(JSONAttributes.ANSWER));
        values.put(AnswerTable.ATTEMPTED, 0);
        values.put(AnswerTable.CORRECT, 0);
        values.put(AnswerTable.TIME, -1);
        db.insert(AnswerTable.NAME, null, values);
    }

    private void fillScore(SQLiteDatabase db, ContentValues values, int cat_id, int level_id)
            throws JSONException {
        values.clear();
        values.put(ScoreTable.CAT_ID, cat_id);
        values.put(ScoreTable.LEVEL_ID, level_id);
        values.put(ScoreTable.POINTS, 0);
        values.put(ScoreTable.COINS, 0);
        db.insert(ScoreTable.NAME, null, values);
    }

    /**
     * Get a list of all the categories in the database
     *
     * @return List of Categories
     */

    public ArrayList<Category> getAllCategories() {
        Cursor cursor = getCategoryCursor();
        ArrayList<Category> categories = new ArrayList<>(cursor.getCount());
        do {
            final Category category = getCategory(cursor);
            categories.add(category);
        } while (cursor.moveToNext());
        return categories;
    }

    private Cursor getCategoryCursor() {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        Cursor data = readableDatabase
                .query(CategoryTable.NAME, CategoryTable.PROJECTION, null, null, null, null, null);
        data.moveToFirst();
        return data;
    }

    private Category getCategory(Cursor cursor) {
        final int cat_id = cursor.getInt(0);
        final String cat_name = cursor.getString(1);
        return new Category(cat_id, cat_name);
    }

    /**
     * Get a Category based on its id
     *
     * @param catgoryId - Unique Identifier for the category
     * @return
     */
    public Category getCategoryFromId(int catgoryId) {
        Cursor cursor = getCategoryFromIdCursor(catgoryId);
        return getCategory(cursor);
    }

    private Cursor getCategoryFromIdCursor(int categoryId) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        String[] args = new String[1];
        args[0] = categoryId + "";
        Cursor data = readableDatabase
                .query(CategoryTable.NAME, CategoryTable.PROJECTION, "CAST(" + CategoryTable.CAT_ID + " as TEXT) = ?", args, null, null, null);
        data.moveToFirst();
        return data;
    }

    /**
     * Get a list of all the levels in a particular category
     *
     * @return List of Categories
     */
    public ArrayList<Level> getAllLevelsOfCategory(int categoryId) {
        Cursor cursor = getLevelCursor(categoryId);
        ArrayList<Level> levels = new ArrayList<>(cursor.getCount());
        do {
            final Level level = getLevel(cursor);
            levels.add(level);
        } while (cursor.moveToNext());
        return levels;
    }

    private Cursor getLevelCursor(int categoryId) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        String[] args = new String[1];
        args[0] = categoryId + "";
        Cursor data = readableDatabase
                .query(LevelTable.NAME, LevelTable.PROJECTION, "CAST(" + LevelTable.CAT_ID + " as TEXT) = ?", args, null, null, null);
        data.moveToFirst();
        return data;
    }

    private Level getLevel(Cursor cursor) {
        final int cat_id = cursor.getInt(0);
        final int level_id = cursor.getInt(1);
        final String level_name = cursor.getString(2);
        final int lock_status = cursor.getInt(3);
        return new Level(cat_id, level_id, level_name, lock_status);
    }

    /**
     * Get a random list of question of a particular category (Current limit 10)
     *
     * @param catgoryId - Unique Identifier for the category
     * @return
     */
    public ArrayList<Question> getQuestionsOfLevelAndCategory(int levelId, int catgoryId) {
        Cursor cursor = getQuestionCursor(levelId, catgoryId);
        ArrayList<Question> questions = new ArrayList<>(cursor.getCount());
        do {
            Question question = getQuestion(cursor);
            questions.add(question);
        } while (cursor.moveToNext());
        return questions;
    }

    public ArrayList<Question> getPracsQuestions(int position, int categoryId, int levelId) {
        Cursor cursor = getPracsQuestionCursor(position,categoryId, levelId);
        ArrayList<Question> questions = new ArrayList<>(cursor.getCount());
        do {
            Question question = getQuestion(cursor);
            questions.add(question);
        } while (cursor.moveToNext());
        return questions;
    }

    public Answer getAnswerOfQuestion(Question question) {
        Cursor cursor = getAnswerCursor(question);
        return getAnswer(cursor);
    }

    private Cursor getQuestionCursor(int levelId, int categoryId) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        String[] args = new String[2];
        args[0] = levelId + "";
        args[1] = categoryId + "";
        Cursor data = readableDatabase
                .query(QuestionTable.NAME, QuestionTable.PROJECTION, "CAST(" + QuestionTable.LEVEL_ID + " as TEXT) = ?" +
                        " AND CAST(" + QuestionTable.CAT_ID + " AS TEXT) = ?", args, null, null, "RANDOM() LIMIT 10");
        data.moveToFirst();
        return data;
    }

    private Cursor getPracsQuestionCursor(int position, int catid, int levelid) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        String[] args = new String[3];
        args[0] = position + "";
        args[1] = catid + "";
        args[2] = levelid + "";
        Cursor data = readableDatabase
                .query(QuestionTable.NAME, QuestionTable.PROJECTION, "CAST(" + QuestionTable.Q_ID + " as TEXT) = ?" +
                        " AND CAST(" + QuestionTable.CAT_ID + " AS TEXT) = ?" +
                        " AND CAST(" + QuestionTable.LEVEL_ID + " AS TEXT) = ?", args, null, null, null);
        data.moveToFirst();
        return data;
    }

    private Cursor getAnswerCursor(Question question) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        String[] args = new String[3];
        args[0] = question.getCategoryId() + "";
        args[1] = question.getLevelId() + "";
        args[2] = question.getQuestionId() + "";


        Cursor data = readableDatabase
                .query(AnswerTable.NAME, AnswerTable.PROJECTION, "CAST ( " + AnswerTable.CAT_ID + " AS TEXT ) = ? " + " AND CAST ( " + AnswerTable.LEVEL_ID + " AS TEXT ) = ? " + " AND CAST ( " + AnswerTable.QUESTION_ID + " AS TEXT ) = ? "
                        , args, null, null,
                        null);
        data.moveToFirst();
        return data;
    }

    public ArrayList<Question> getTotalQuestionsFromCategory(int catgoryId) {
        Cursor cursor = getTotalQuestionCursor(catgoryId);
        ArrayList<Question> questions = new ArrayList<>(cursor.getCount());
        do {
            Question question = getQuestion(cursor);
            questions.add(question);
        } while (cursor.moveToNext());
        return questions;
    }

    private Cursor getTotalQuestionCursor(int categoryId) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        String[] args = new String[1];
        args[0] = categoryId + "";
        Cursor data = readableDatabase
                .query(QuestionTable.NAME, QuestionTable.PROJECTION,
                        "CAST(" + QuestionTable.CAT_ID + " as TEXT) = ?", args, null, null, null);
        data.moveToFirst();

        return data;
    }

    private Question getQuestion(Cursor cursor) {
        final int catId = cursor.getInt(0);
        final int levelId = cursor.getInt(1);
        final int questionId = cursor.getInt(2);
        final String questionText = cursor.getString(3);
        final String questionPrgm = cursor.getString(4);
        final String optA = cursor.getString(5);
        final String optB = cursor.getString(6);
        final String optC = cursor.getString(7);
        final String optD = cursor.getString(8);
        return new Question(catId, levelId, questionId, questionText, questionPrgm, optA, optB, optC, optD);
    }

    private Answer getAnswer(Cursor cursor) {
        final int cat_id = cursor.getInt(0);
        final int level_id = cursor.getInt(1);
        final int question_id = cursor.getInt(2);
        final int answer = cursor.getInt(3);
        final int attempted = cursor.getInt(4);
        final int correct = cursor.getInt(5);
        final float time = cursor.getFloat(6);
        return new Answer(cat_id, level_id, question_id, answer, attempted, correct, time);
    }

    /**
     * Update the solved status of a question
     *
     * @param answer
     */
    public void setCorrectStatus(Answer answer) {
        ContentValues questionValues = new ContentValues();
        questionValues.clear();
        questionValues.put(AnswerTable.CORRECT, answer.getCorrect());

        String[] questionArgs = new String[3];
        questionArgs[0] = answer.getCatId() + "";
        questionArgs[1] = answer.getLevelId() + "";
        questionArgs[2] = answer.getQuestionId() + "";

        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        writableDatabase.update(AnswerTable.NAME, questionValues, "CAST ( " + AnswerTable.CAT_ID + " AS TEXT ) = ? " + " AND CAST ( " + AnswerTable.LEVEL_ID + " AS TEXT ) = ? " + " AND CAST ( " + AnswerTable.QUESTION_ID + " AS TEXT ) = ? ", questionArgs);

    }

    /**
     * Update the attempt count of a question
     *
     * @param answer
     */
    public void setAttemptedCountAndSolveTime(Answer answer) {
        ContentValues questionValues = new ContentValues();
        questionValues.clear();
        questionValues.put(AnswerTable.ATTEMPTED, answer.getAttempted());
        questionValues.put(AnswerTable.TIME, answer.getTime());

        String[] questionArgs = new String[]{answer.getCatId() + "", answer.getLevelId() + "", answer.getQuestionId() + ""};

        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        writableDatabase.update(AnswerTable.NAME, questionValues, "CAST ( " + AnswerTable.CAT_ID + " AS TEXT ) = ? " + " AND CAST ( " + AnswerTable.LEVEL_ID + " AS TEXT ) = ? " + " AND CAST ( " + AnswerTable.QUESTION_ID + " AS TEXT ) = ? ", questionArgs);
    }

    public void setPointsCount(Score score) {
        ContentValues questionValues = new ContentValues();
        questionValues.clear();
        questionValues.put(ScoreTable.POINTS, score.getPoints());

        String[] questionArgs = new String[2];
        questionArgs[0] = score.getCatId() + "";
        questionArgs[1] = score.getLevelId() + "";

        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        Cursor cursor = writableDatabase.rawQuery("Select * from " + ScoreTable.NAME + " where "
                + "CAST ( " + ScoreTable.CAT_ID + " AS TEXT ) = ? " + " AND CAST ( " + ScoreTable.LEVEL_ID + " AS TEXT ) = ? ", questionArgs);
        cursor.moveToFirst();
        int dbpoints = cursor.getInt(cursor.getColumnIndex(ScoreTable.POINTS));
        cursor.close();
        if (dbpoints < score.getPoints()) {
            writableDatabase.update(ScoreTable.NAME, questionValues, "CAST ( " + ScoreTable.CAT_ID + " AS TEXT ) = ? " + " AND CAST ( " + ScoreTable.LEVEL_ID + " AS TEXT ) = ? ", questionArgs);
        }

        increaseCoins(score);
    }

    public int getPointsCount(int catId, int levelId) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] questionArgs = new String[2];
        questionArgs[0] = catId + "";
        questionArgs[1] = levelId + "";
        Cursor c = database.query(ScoreTable.NAME, ScoreTable.PROJECTION,
                "CAST ( " + ScoreTable.CAT_ID + " AS TEXT ) = ? " + " AND CAST ( " + ScoreTable.LEVEL_ID + " AS TEXT ) = ? ",
                questionArgs, null, null, null);
        c.moveToFirst();
        int a = c.getInt(c.getColumnIndex(ScoreTable.POINTS));
        c.close();
        return a;
    }

    private void increaseCoins(Score score) {
        String[] questionArgs = new String[2];
        questionArgs[0] = score.getCatId() + "";
        questionArgs[1] = score.getLevelId() + "";

        SQLiteDatabase writableDatabase = this.getWritableDatabase();

        String query = "Select * from " + ScoreTable.NAME + " where " +
                "CAST ( " + ScoreTable.CAT_ID + " AS TEXT ) = ? " + " AND CAST ( " + ScoreTable.LEVEL_ID + " AS TEXT ) = ? ";
        Cursor cursor = writableDatabase.rawQuery(query, questionArgs);
        cursor.moveToFirst();

        int coins = cursor.getInt(cursor.getColumnIndex(ScoreTable.COINS));
        ++coins;

        ContentValues questionValues = new ContentValues();
        questionValues.clear();
        questionValues.put(ScoreTable.COINS, coins);
        Log.d("Coins", String.valueOf(coins));
        writableDatabase.update(ScoreTable.NAME, questionValues,
                "CAST ( " + ScoreTable.CAT_ID + " AS TEXT ) = ? " + " AND CAST ( " + ScoreTable.LEVEL_ID + " AS TEXT ) = ? ",
                questionArgs);

    }

    public void updateSolveTime(Answer answer) {
        ContentValues questionValues = new ContentValues();
        questionValues.clear();
        questionValues.put(AnswerTable.TIME, answer.getTime());

        String[] questionArgs = new String[]{answer.getCatId() + "", answer.getLevelId() + "", answer.getQuestionId() + ""};

        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        writableDatabase.update(AnswerTable.NAME, questionValues,
                "CAST ( " + AnswerTable.CAT_ID + " AS TEXT ) = ? " +
                        " AND CAST ( " + AnswerTable.LEVEL_ID + " AS TEXT ) = ? " +
                        " AND CAST ( " + AnswerTable.QUESTION_ID + " AS TEXT ) = ? ",
                questionArgs);
    }

    /**
     * Get total number of question that have been attempted
     *
     * @return
     */
    public int getAttemptedQuestionCount() {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        String[] params = new String[]{"1"};
        String selection = AnswerTable.ATTEMPTED + " >= ? ";
        Cursor c = readableDatabase
                .query(AnswerTable.NAME, AnswerTable.PROJECTION, selection, params, null, null, null);
        c.moveToFirst();
        int count = c.getCount();
        c.close();
        return count;
    }

    public int getAttemptedQuestionCountInCategory(int categoryId) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        Cursor c = readableDatabase.rawQuery("Select " + AnswerTable.ATTEMPTED + " from " + AnswerTable.NAME + " where "
                + AnswerTable.CAT_ID + " = " + categoryId + " AND " + AnswerTable.ATTEMPTED + " >= 1 ", null);
        c.moveToFirst();
        int count = c.getCount();
        c.close();
        return count;
    }

    public void unlockNextLevel(int catId, int levelid) {
        ContentValues questionValues = new ContentValues();
        questionValues.clear();
        questionValues.put(LevelTable.LOCK_STATUS, 0);

        String[] questionArgs = new String[2];
        questionArgs[0] = catId + "";
        questionArgs[1] = ++levelid + "";

        if (levelid <= getAllLevelsOfCategory(catId).size()) {
            SQLiteDatabase writableDatabase = this.getWritableDatabase();
            writableDatabase.update(LevelTable.NAME, questionValues, "CAST ( " + LevelTable.CAT_ID + " AS TEXT ) = ? " + " AND CAST ( " + LevelTable.LEVEL_ID + " AS TEXT ) = ? ", questionArgs);
        }
    }

    public int getCorrectQuestionCount() {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        String rawQuery = "SELECT * FROM " + AnswerTable.NAME + " WHERE " + AnswerTable.CORRECT + " = '1' ";
        Cursor c = readableDatabase.rawQuery(rawQuery, null);
        c.moveToFirst();
        int count = c.getCount();
        c.close();
        return count;
    }


    public int getCorrectQuestionCountInCategory(int categoryId) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        String[] params = new String[1];
        params[0] = categoryId + "";
        Cursor cursor = readableDatabase.rawQuery("Select " + AnswerTable.ATTEMPTED + " from " + AnswerTable.NAME + " where "
                + AnswerTable.CAT_ID + " = " + categoryId + " AND " + AnswerTable.CORRECT + " = 1 ", null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void clearAndRefreshDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + ScoreTable.NAME);
        db.execSQL("DELETE FROM " + QuestionTable.NAME);
        db.execSQL("DELETE FROM " + LevelTable.NAME);
        db.execSQL("DELETE FROM " + CategoryTable.NAME);
        preFillDatabase(db);
    }

    /*
    Debugging methods below here
     */
    public void printTableContent(SQLiteDatabase database, String tableName) {
        String query = "SELECT * FROM " + tableName;
        Cursor c = database.rawQuery(query, null);
        c.moveToFirst();
        for (int i = 0; i < c.getColumnCount(); i++) {
            System.out.print(c.getColumnName(i) + "    ");
        }
        System.out.println();
        do {
            for (int i = 0; i < c.getColumnCount(); i++) {
                System.out.print(printColumnContent(c, i) + "    ");
            }
            System.out.println();
        } while (c.moveToNext());
        c.close();
    }

    private String printColumnContent(Cursor c, int colIndex) {
        if (c.getType(colIndex) == Cursor.FIELD_TYPE_STRING)
            return c.getString(colIndex);
        else if (c.getType(colIndex) == Cursor.FIELD_TYPE_INTEGER)
            return c.getInt(colIndex) + "";
        else if (c.getType(colIndex) == Cursor.FIELD_TYPE_FLOAT) {
            return c.getFloat(colIndex) + "";
        } else return null;
    }

    public Cursor getQuestionData(String[] args) {
        SQLiteDatabase database = getReadableDatabase();
        String query = "SELECT " + QuestionTable.Q_TEXT + " FROM " + QuestionTable.NAME + " WHERE " + "CAST ( "+ QuestionTable.CAT_ID + " AS TEXT ) =? AND " + "CAST ( "+ QuestionTable.LEVEL_ID + " AS TEXT ) = ?";
        Log.d("Query",query);
        Cursor c = database.rawQuery(query, args);

        Log.d("Args",args[0]+" "+ args[1]);

        return c;
    }


}
