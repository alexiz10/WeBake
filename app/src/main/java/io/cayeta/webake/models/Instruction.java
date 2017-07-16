package io.cayeta.webake.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Instruction implements Parcelable {

    private int mId;

    private String mShortDescription;
    private String mDescription;
    private String mVideoURL;
    private String mThumbnailURL;

    public Instruction(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        mId = id;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoURL = videoURL;
        mThumbnailURL = thumbnailURL;
    }

    public Instruction(Parcel parcel) {
        mId = parcel.readInt();
        mShortDescription = parcel.readString();
        mDescription = parcel.readString();
        mVideoURL = parcel.readString();
        mThumbnailURL = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mShortDescription);
        dest.writeString(mDescription);
        dest.writeString(mVideoURL);
        dest.writeString(mThumbnailURL);
    }

    public static Creator<Instruction> CREATOR = new Creator<Instruction>() {
        @Override
        public Instruction createFromParcel(Parcel source) {
            return new Instruction(source);
        }

        @Override
        public Instruction[] newArray(int size) {
            return new Instruction[size];
        }
    };

    public int getId() {
        return mId;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoURL() {
        return mVideoURL;
    }

    public String getThumbnailURL() {
        return mThumbnailURL;
    }

}