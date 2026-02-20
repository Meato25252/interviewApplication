package com.example.interviewapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Question implements Parcelable {
    private String id;
    private String text;
    private String type;
    private List<String> followUpIds;

    public Question(String id,String text,String type,List<String> followUpIds){
        this.id=id;
        this.text=text;
        this.type=type;
        this.followUpIds=followUpIds;
    }

    public String getId(){
        return id;
    }

    public String getText(){
        return text;
    }

    protected Question(Parcel in){
        this.id=in.readString();
        this.text=in.readString();
        this.type=in.readString();
        this.followUpIds=in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(text);
        dest.writeString(type);
        dest.writeStringList(followUpIds);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
