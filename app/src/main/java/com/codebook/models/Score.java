package com.codebook.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Archish on 12/23/2015.
 */
public class Score implements Parcelable {
    public static final String TAG = "Score";
    private int cat_id;
    private int level_id;
    private int points;
    private int coins;

    public Score(int cat_id, int level_id, int points, int coins) {
        this.cat_id = cat_id;
        this.level_id = level_id;
        this.points = points;
        this.coins = coins;

    }

    protected Score(Parcel in) {
        this.setCatId(in.readInt());
        this.setLevelId(in.readInt());
        this.setPoints(in.readInt());
        this.setCoins(in.readInt());
    }

    public static final Creator<Score> CREATOR = new Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getCatId());
        dest.writeInt(this.getLevelId());
        dest.writeInt(this.getPoints());
        dest.writeInt(this.getCoins());
    }

    public int getCatId() {
        return cat_id;
    }

    public int getLevelId() {
        return level_id;
    }

    public int getPoints() {
        return points;
    }

    public int getCoins() {
        return coins;
    }

    public void setCatId(int catId) {
        this.cat_id = catId;
    }

    public void setLevelId(int levelId) {
        this.level_id = levelId;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
