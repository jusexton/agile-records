package main.java.users;

/**
 * Class that will represent all users that reside on sql server.
 */
public abstract class User {

    private int userId;
    private string firstName;
    private string lastName;

    public void setUserId(int id){
        userID = id;
    }
    public void setUserFirstName(string s){
        firstName = s;
    }
    public void setUserLastName(string s){
        lastName = s;
    }
    public int getUserID(){
        return userId;
    }
    public string getUserFirstName(){
        return firstName;
    }
    public string getUserLastName(){
        return lastName;
    }


}
