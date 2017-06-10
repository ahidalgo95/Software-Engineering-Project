package com.example.katevandonge.dejaphotoproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.rule.ActivityTestRule;
import android.util.Pair;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Peter on 6/9/2017.
 */

public class UserTest {

    /*
     * Test user constructor
     */
    @Test
    public void constructorTest()
    {
        User user = new User();
        assertEquals(user.loggedIn, false);
        assertEquals(user.myShareablePhotos.size(), 0);
        assertEquals(user.myFriends.size() , 0);
    }

    /*
     * Test logging in user
     */
    @Test
    public void logged_inTest()
    {
        User user = new User();
        assertEquals(user.isLoggedIn(), false);
        user.loggedIn = true;
        assertEquals(user.isLoggedIn(),true);
    }

    /*
     * Test setter for logging in
     */
    @Test
    public void setLoggedInTest()
    {
        User user = new User();

        assertEquals(user.loggedIn, false);
        user.setLoggedIn(true);

        assertEquals(user.loggedIn, true);
        user.setLoggedIn(false);


    }

    /*
     * Test setting password
     */
    @Test
    public void setPasswordTest()
    {
        User user = new User();
        user.setPassword("1234");
        assertEquals(user.myPassword, "1234");
    }

    /*
     * Test setting email
     */
    @Test
    public void setEmailTest()
    {
        User user = new User();
        user.setEmail("User1@gmail.com");
        assertEquals(user.myEmail, "User1@gmail.com");

    }

    /*
     * Test getting email
     */
    @Test
    public void getEmailTest()
    {
        User user = new User();
        user.myEmail = "test";
        assertEquals(user.getEmail(), "test");
    }

    /*
     * Test getting password
     */
    @Test
    public void getPasswordTest()
    {
        User user = new User();
        user.myPassword = "test";
        //getMyPassword == getName if you have an error just change it to getName.
        assertEquals(user.getPassword(), "test");

    }

    /*
     * Test adding friends
     */
    @Test
    public void addFriendsTest()
    {
        User user = new User();
        user.addFriend("test@gmail.com");

        assertEquals(user.myFriends.size(), 1);
        assertEquals(user.myFriends.get(0), "test@gmail.com");
    }
}
