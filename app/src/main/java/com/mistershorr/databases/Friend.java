package com.mistershorr.databases;

import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable {
    private int clumsiness;
    private double gymFrequency;
    private boolean isAwesome;
    private double moneyOwed;
    private String name;
    private int trustworthiness;

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
        return isAwesome;
    }

    public void setAwesome(boolean awesome) {
        isAwesome = awesome;
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
                ", isAwesome=" + isAwesome +
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
        dest.writeByte(this.isAwesome ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.moneyOwed);
        dest.writeString(this.name);
        dest.writeInt(this.trustworthiness);
    }

    protected Friend(Parcel in) {
        this.clumsiness = in.readInt();
        this.gymFrequency = in.readDouble();
        this.isAwesome = in.readByte() != 0;
        this.moneyOwed = in.readDouble();
        this.name = in.readString();
        this.trustworthiness = in.readInt();
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
