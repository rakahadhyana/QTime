package com.hmm.q_time;

import android.os.Parcel;
import android.os.Parcelable;

public class Doctor implements Parcelable {
    private String mId;
    private String mName;
    private String mType;
    private String mLocation;
    private String mImageUrl;

    public Doctor(String id, String name, String type, String location, String imageUrl) {
        mId = id;
        mName = name;
        mType = type;
        mLocation = location;
        mImageUrl = imageUrl;
    }

    protected Doctor(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mType = in.readString();
        mLocation = in.readString();
        mImageUrl = in.readString();
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mType);
        dest.writeString(mLocation);
        dest.writeString(mImageUrl);
    }
}
