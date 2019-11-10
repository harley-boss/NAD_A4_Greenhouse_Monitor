/**
 * Project: NAD A4
 * File: User.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Shallow class representing a user. Parcelable to allow passing between
 *  activities
 */

package com.example.greenhousemonitor;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String userId; // UUID
    private String userName;
    private String password;
    private String token;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    private User(Parcel in) {
        this.userId = in.readString();
        this.userName = in.readString();
        this.password = in.readString();
        this.token = in.readString();
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void updateUser(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.password);
        dest.writeString(this.token);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
