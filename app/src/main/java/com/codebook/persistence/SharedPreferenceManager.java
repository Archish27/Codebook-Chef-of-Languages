package com.codebook.persistence;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Archish on 12/4/2015.
 */
public class SharedPreferenceManager {
    private SharedPreferences settings;

    private static final String PREFS_NAME = "VCPrefs";
    private static final String PREFS_KEY_MUTE = "mute";
    private static final String PREFS_KEY_SEEKBAR = "seekValue";
    private static final String PREFS_KEY_SEEKNUM = "seekNum";
    private static final String PREFS_KEY_TIME = "time";
    private static final String PREFS_KEY_LANGUAGE = "language";

    private static final String PREFS_KEY_PLAYER_SIGNED_IN = "player_sign_in";
    private static final String PREFS_KEY_PLAYER_NAME = "player_name";
    private static final String PREFS_KEY_PLAYER_ID = "player_id";

    public SharedPreferenceManager(Context mContext) {
        settings = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void savePlayerName(String name) {
        settings.edit().putString(PREFS_KEY_PLAYER_NAME, name).apply();
    }

    public String getPlayerName() {
        return settings.getString(PREFS_KEY_PLAYER_NAME, "Player");
    }

    public void savePlayerId(String id) {
        settings.edit().putString(PREFS_KEY_PLAYER_ID, id).apply();
    }

    public String getPlayerId() {
        return settings.getString(PREFS_KEY_PLAYER_ID, "");
    }

    public void savePlayerSignInStatus(boolean status) {
        settings.edit().putBoolean(PREFS_KEY_PLAYER_SIGNED_IN, status).apply();
    }

    public boolean getPlayerSignInStatus() {
        return settings.getBoolean(PREFS_KEY_PLAYER_SIGNED_IN, false);
    }

    public void saveMuteState(Boolean m) {
        settings.edit().putBoolean(PREFS_KEY_MUTE, m).apply();
    }

    public Boolean getValueMute() {
        return settings.getBoolean(PREFS_KEY_MUTE, false);
    }

    public void saveLanguage(String s) {
        settings.edit().putString(PREFS_KEY_LANGUAGE, s).apply();
    }



    public void saveTime(String m) {
        settings.edit().putString(PREFS_KEY_TIME, m).apply();
    }

    public void saveSeekValue(Boolean m) {
        settings.edit().putBoolean(PREFS_KEY_SEEKBAR, m).apply();
    }

    public void saveSeekNum(int value) {
        settings.edit().putInt(PREFS_KEY_SEEKNUM, value).apply();
    }

    public void removePreference() {
        settings.edit().remove(PREFS_KEY_TIME).apply();
    }

    public Boolean getBooleanFalseSeekBar() {
        return settings.getBoolean(PREFS_KEY_SEEKBAR, false);
    }

    public Boolean getBooleanTrueSeekBar() {
        return settings.getBoolean(PREFS_KEY_SEEKBAR, true);
    }

    public String getStartTime() {
        return settings.getString(PREFS_KEY_TIME, null);
    }

    public int getValueSeekBar() {
        int demo = 50;
        return settings.getInt(PREFS_KEY_SEEKNUM, demo);
    }

}
