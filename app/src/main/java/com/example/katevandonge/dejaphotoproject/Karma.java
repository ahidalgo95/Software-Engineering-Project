package com.example.katevandonge.dejaphotoproject;

/**
 * Created by katevandonge on 5/10/17.
 */


public class Karma {

    boolean karma;

    public Karma(){
        karma = false;

    }

    public void switching(){
        if(karma == true){
            karma = false;
        }
        else if(karma == false){
            karma = true;
        }
        return;
    }
}
