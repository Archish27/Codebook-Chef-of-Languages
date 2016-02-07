package com.codebook.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Archish on 12/22/2015.
 */
public class Answer implements Parcelable{
    public static String TAG = "Answers";
    private int cat_id;
    private int level_id;
    private int question_id;
    private int answer;
    private int attempted;
    private int correct;
    private float time;

    public Answer(int cat_id, int level_id, int question_id, int answer, int attempted, int correct, float time){
        this.cat_id=cat_id;
        this.level_id=level_id;
        this.question_id=question_id;
        this.answer = answer;
        this.attempted = attempted;
        this.correct = correct;
        this.time = time;
    }

    protected Answer(Parcel in) {
        this.setCatId(in.readInt());
        this.setLevelId(in.readInt());
        this.setQuestionId(in.readInt());
        this.setAnswer(in.readInt());
        this.setAttempted(in.readInt());
        this.setCorrect(in.readInt());
        this.setTime(in.readFloat());

    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
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
        dest.writeInt(this.getQuestionId());
        dest.writeInt(this.getAnswer());
        dest.writeInt(this.getAttempted());
        dest.writeInt(this.getCorrect());
        dest.writeFloat(this.getTime());
    }

    public int getAnswer() {
        return answer;
    }

    public int getAttempted() {
        return attempted;
    }

    public int getCorrect() {
        return correct;
    }

    public float getTime() {
        return time;
    }

    public int getQuestionId() {
        return question_id;
    }

    public void setQuestionId(int question_id) {
        this.question_id = question_id;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public void setAttempted(int attempted) {
        this.attempted = attempted;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public int getLevelId() {
        return level_id;
    }

    public int getCatId() {
        return cat_id;
    }

    public void setCatId(int cat_id) {
        this.cat_id = cat_id;
    }

    public void setLevelId(int level_id) {
        this.level_id = level_id;
    }
}
