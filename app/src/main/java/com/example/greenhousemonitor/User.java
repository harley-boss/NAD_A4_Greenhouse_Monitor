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

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Class: User
 * Description: Represents a user of the application. Holds all the relevant
 * data pertaining to that user such as their id and any greenhouses they
 * have access to
 */
public class User implements Parcelable {
    private String userId;
    private String userName;
    private String password;
    private String token;
    private ArrayList<Greenhouse> greenhouses;

    /**
     * Method: User - Constructor
     * @param userName Username of the user
     * @param password Password of the user
     * returns: n/a
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Method: User - private constructor used to parcel the object
     * @param in - parceled data
     * returns: n/a
     */
    private User(Parcel in) {
        this.userId = in.readString();
        this.userName = in.readString();
        this.password = in.readString();
        this.token = in.readString();
    }

    /**
     * Method: setUserId
     * @param userId String userid obtained from Google upon sign in
     * returns: void
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Method: setUserName
     * @param userName
     * returns: void
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Method: setPassword
     * @param password
     * returns: void
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method: setToken
     * @param token token obtained from Google
     * returns: void
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Method: setGreenhouses
     * @param greenhouses List of greenhouse objects
     * returns: void
     */
    public void setGreenhouses(ArrayList<Greenhouse> greenhouses) {
        this.greenhouses = greenhouses;
    }

    /**
     * Method: getGreenhouses
     * @return returns a list of all greenhouses the user has access to
     */
    public ArrayList<Greenhouse> getGreenhouses() {
        return greenhouses;
    }

    /**
     * Method: getUserId
     * @return returns the string value of the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Method: getUserName
     * @return returns the users username (email)
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Method: getPassword
     * @return the string password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method: getToken
     * @return the string token obtained from Google
     */
    public String getToken() {
        return token;
    }

    /**
     * Method: describe contents
     * Descr: Overriden method that must be implemented when extending Parcelable
     * @return int
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Method: writeToParcel
     * @param dest
     * @param flags
     * Descr: Overridden method that allows an object to be parcelled and sent between activities
     */
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
