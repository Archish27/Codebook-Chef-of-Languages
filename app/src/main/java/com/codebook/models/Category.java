package com.codebook.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Narayan Acharya on 30/10/2015.
 */
public class Category implements Parcelable {
    public static final String TAG = "Category";

    private int id;
    private String name;


    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getId());
        dest.writeString(this.getName());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static Creator CREATOR = new Creator() {
        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }

        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }
    };

    private Category(Parcel source) {
        this.setId(source.readInt());
        this.setName(source.readString());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getId() + " : " + this.getName();
    }

}
