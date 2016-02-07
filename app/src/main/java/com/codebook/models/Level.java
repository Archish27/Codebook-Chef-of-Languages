package com.codebook.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Narayan Acharya on 09/12/2015.
 */
public class Level implements Parcelable {

    public static final String TAG = "Level";

    private int categoryId;
    private int levelId;
    private String levelName;
    private int lockStatus;

    public Level(int categoryId, int levelId, String levelName, int lockStatus) {
        this.categoryId = categoryId;
        this.levelId = levelId;
        this.levelName = levelName;
        this.lockStatus = lockStatus;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getCategoryId());
        dest.writeInt(this.getLevelId());
        dest.writeString(this.getLevelName());
        dest.writeInt(this.getLockStatus());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static Creator CREATOR = new Creator() {
        @Override
        public Level[] newArray(int size) {
            return new Level[size];
        }

        @Override
        public Level createFromParcel(Parcel source) {
            return new Level(source);
        }
    };

    private Level(Parcel source) {
        this.setCategoryId(source.readInt());
        this.setLevelId(source.readInt());
        this.setLevelName(source.readString());
        this.setLockStatus(source.readInt());
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(int lockStatus) {
        this.lockStatus = lockStatus;
    }
}
