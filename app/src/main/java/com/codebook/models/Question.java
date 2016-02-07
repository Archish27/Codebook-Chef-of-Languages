package com.codebook.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Narayan Acharya on 30/10/2015.
 */
public class Question implements Parcelable {
    public static final String TAG = "Question";

    private int categoryId;
    private int levelId;
    private int questionId;
    private String questionText;
    private String questionPrgmText;
    private String optA;
    private String optB;
    private String optC;
    private String optD;



    public Question(int categoryId, int levelId, int questionId, String questionText,String questionPrgmText,
                    String optA, String optB, String optC, String optD) {
        this.categoryId = categoryId;
        this.levelId = levelId;
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionPrgmText=questionPrgmText;
        this.optA = optA;
        this.optB = optB;
        this.optC = optC;
        this.optD = optD;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getCategoryId());
        dest.writeInt(this.getLevelId());
        dest.writeInt(this.getQuestionId());
        dest.writeString(this.getQuestionText());
        dest.writeString(this.getPrgmText());
        dest.writeString(this.getOptA());
        dest.writeString(this.getOptB());
        dest.writeString(this.getOptC());
        dest.writeString(this.getOptD());

    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    private Question(Parcel in) {
        this.setCategoryId(in.readInt());
        this.setLevelId(in.readInt());
        this.setQuestionId(in.readInt());
        this.setQuestionText(in.readString());
        this.setPrgmText(in.readString());
        this.setOptA(in.readString());
        this.setOptB(in.readString());
        this.setOptC(in.readString());
        this.setOptD(in.readString());

    }


    public int getCategoryId() {
        return categoryId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOptA() {
        return optA;
    }

    public String getOptB() {
        return optB;
    }

    public String getOptC() {
        return optC;
    }

    public String getOptD() {
        return optD;
    }


    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setOptA(String optA) {
        this.optA = optA;
    }

    public void setOptB(String optB) {
        this.optB = optB;
    }

    public void setOptC(String optC) {
        this.optC = optC;
    }

    public void setOptD(String optD) {
        this.optD = optD;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }
    public void setPrgmText(String questionPrgmText){
        this.questionPrgmText=questionPrgmText;
    }
    public String  getPrgmText(){
        return questionPrgmText;
    }
}
