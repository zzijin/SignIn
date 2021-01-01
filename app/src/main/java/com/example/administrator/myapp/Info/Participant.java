package com.example.administrator.myapp.Info;

public class Participant {
    public int userID;
    public boolean clockIn;
    public boolean administrator;

    public Participant(int userID,boolean clockIn, boolean administrator)
    {
        this.userID = userID;this.clockIn = clockIn;this.administrator = administrator;
    }
}
