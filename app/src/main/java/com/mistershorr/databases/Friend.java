package com.mistershorr.databases;

import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable{
    private int clumsiness;
    private double gymFrequency;
    private boolean awesome;
    private double moneyOwed;
    private String name;
    private int trustworthiness;
    //backendless specific fields
    //add the getter and setters
    private String objectId;
    private String ownerId;

    public String getobjectId() {
        return objectId;
    }

    public void setobjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getownerId() {
        return ownerId;
    }

    public void setownerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Friend() {
    }

    public int getClumsiness() {
        return clumsiness;
    }

    public void setClumsiness(int clumsiness) {
        this.clumsiness = clumsiness;
    }

    public double getGymFrequency() {
        return gymFrequency;
    }

    public void setGymFrequency(double gymFrequency) {
        this.gymFrequency = gymFrequency;
    }

    public boolean isAwesome() {
        return awesome;
    }

    public void setAwesome(boolean isawesome) {
        awesome = isawesome;
    }

    public double getMoneyOwed() {
        return moneyOwed;
    }

    public void setMoneyOwed(double moneyOwed) {
        this.moneyOwed = moneyOwed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrustworthiness() {
        return trustworthiness;
    }

    public void setTrustworthiness(int trustworthiness) {
        this.trustworthiness = trustworthiness;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "clumsiness=" + clumsiness +
                ", gymFrequency=" + gymFrequency +
                ", isAwesome=" + awesome +
                ", moneyOwed=" + moneyOwed +
                ", name='" + name + '\'' +
                ", trustworthiness=" + trustworthiness +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.clumsiness);
        dest.writeDouble(this.gymFrequency);
        dest.writeByte(this.awesome ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.moneyOwed);
        dest.writeString(this.name);
        dest.writeInt(this.trustworthiness);
        dest.writeString(this.objectId);
        dest.writeString(this.ownerId);
    }

    protected Friend(Parcel in) {
        this.clumsiness = in.readInt();
        this.gymFrequency = in.readDouble();
        this.awesome = in.readByte() != 0;
        this.moneyOwed = in.readDouble();
        this.name = in.readString();
        this.trustworthiness = in.readInt();
        this.objectId = in.readString();
        this.ownerId = in.readString();
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel source) {
            return new Friend(source);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
}
