package controller;

import java.io.Serializable;

/**
 *
 * @author Samuel
 */
public class User implements Serializable {

    private String firstName; //User's first name
    private String lastName; //User's last name
    private String username; //User's username to the website
    private String password; //User's password to the website
    private int userID; //User's unique ID
    private float checkingBal; //User's checking balance
    private float savingsBal; //User's savings balance

    public User() {
        //If no information is given, just initialize everything to default [empty] values
        this.firstName = "";
        this.lastName = "";
        this.username = "";
        this.password = "";
        this.userID = -1;
        this.checkingBal = 0.0f;
        this.savingsBal = 0.0f;
    }

    public User(int userID /*in, User's unique id*/,
            String username /*in, user's username*/,
            String password /*in, user's password*/,
            String firstName /*in, user's first name*/,
            String lastName /*in, user's last name*/,
            float checkingBal /*in, user's checking balance*/,
            float savingsBal /*in, user's savings balance*/) {
        this.userID = userID; //Store the user's user ID
        this.username = ((username == null) ? "" : username); //Store the user's username, Prevent null pointer exception
        this.firstName = ((firstName == null) ? "" : firstName); //Store the user's first name, Prevent null pointer exception
        this.lastName = ((lastName == null) ? "" : lastName); //Store the user's last name, Prevent null pointer exception
        this.password = ((password == null) ? "" : password); //Store the user's password, Prevent null pointer exception
        this.checkingBal = checkingBal; //Store the user's checking balance
        this.savingsBal = savingsBal; //Store the user's savings balance
    }

    /*Set the user's unique ID*/
    public void SetUserID(int id /*in, user's unique ID*/) {
        this.userID = id; //Set the user's unique ID
    }

    /*Return the user's unique ID*/
    public int GetUserID() {
        return userID; //Return the user's unique ID
    }

    /*Set the user's username*/
    public void SetUsername(String name /*in, user's username*/) {
        this.username = name; //Set the user's username
    }

    /*Return the user's username*/
    public String GetUsername() {
        return username; //Return the user's username
    }

    /*Set the user's password*/
    public void SetPassword(String pwd /*in, password*/) {
        this.password = pwd; //Set the user's password
    }

    /*Return the user's password*/
    public String GetPassword() {
        return password; //Return the user's password
    }

    /*Set the user's first name*/
    public void SetFirstName(String name /*in, user's first name*/) {
        this.firstName = name; //Set the user's first name
    }

    /*Return the user's first name*/
    public String GetFirstName() {
        return firstName; //Return the user's first name
    }

    /*Set the user's last name*/
    public void SetLastName(String name /*in, user's last name*/) {
        this.lastName = name; //Set the user's last name
    }

    /*Return the user's last name*/
    public String GetLastName() {
        return lastName; //Return the user's last name
    }

    /*Set the user's checking balance*/
    public void SetCheckingBalance(float balance /*in, user's checking balance*/) {
        this.checkingBal = balance; //Set the user's checking balance
    }

    /*Return the user's checking balance*/
    public float GetCheckingBalance() {
        return checkingBal; //Return the user's checking balance
    }

    /*Set the user's savings balance*/
    public void SetSavingsBalance(float balance /*in, user's savings balance*/) {
        this.savingsBal = balance; //Set the user's savings balance
    }

    /*Return the user's savings balance*/
    public float GetSavingsBalance() {
        return savingsBal; //Return the user's savings balance
    }
}
