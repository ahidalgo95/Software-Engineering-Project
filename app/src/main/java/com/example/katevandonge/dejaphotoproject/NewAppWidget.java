package com.example.katevandonge.dejaphotoproject;

import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.RemoteViews;
import static android.R.attr.button;
import static android.R.attr.content;
import static android.support.v4.content.ContextCompat.startActivity;
import static java.lang.Thread.sleep;

import android.Manifest;

import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    //Gallery pList;
    //WallpaperManager myWall;
    public static String WIDGET_BUTTON = "MY_PACKAGE_NAME.WIDGET_BUTTON";
    public static String WIDGET_NEXT = "NEXT_BUTTON";
    public static String WIDGET_PREV = "PREV_BUTTON";
    public static String WIDGET_RELEASE = "RELEASE_BUTTON";


    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        CharSequence widgetText = context.getString(R.string.appwidget_text);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);


        Intent intentA = new Intent(WIDGET_BUTTON);
        // This button launches the app
        intentA.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        //KARMA BUTTON
        Intent intentKarma = new Intent(context, NewAppWidget.class);
        intentKarma.setAction(WIDGET_BUTTON);
        PendingIntent pendingIntentKarma = PendingIntent.getBroadcast(context, 0, intentKarma, 0);
        views.setOnClickPendingIntent(R.id.Karma, pendingIntentKarma);

        //NEXT BUTTON
        intentKarma = new Intent(context, NewAppWidget.class);
        intentKarma.setAction(WIDGET_NEXT);
        pendingIntentKarma = PendingIntent.getBroadcast(context, 0, intentKarma, 0);
        views.setOnClickPendingIntent(R.id.Next, pendingIntentKarma);

        //PREV BUTTON
        intentKarma = new Intent(context, NewAppWidget.class);
        intentKarma.setAction(WIDGET_PREV);
        pendingIntentKarma = PendingIntent.getBroadcast(context, 0, intentKarma, 0);
        views.setOnClickPendingIntent(R.id.Back, pendingIntentKarma);

        //RELEASE
        intentKarma = new Intent(context, NewAppWidget.class);
        intentKarma.setAction(WIDGET_RELEASE);
        pendingIntentKarma = PendingIntent.getBroadcast(context, 0, intentKarma, 0);
        views.setOnClickPendingIntent(R.id.button6, pendingIntentKarma);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intentKarma) {
        super.onReceive(context, intentKarma);


        //KARMA BUTTON
        if (intentKarma.getAction().equals(NewAppWidget.WIDGET_BUTTON)) {
            Photo[] wallArr = Wall.photoArr;
            int counter = Wall.counter;
            Photo thisPhoto = wallArr[counter];
            thisPhoto.karma = true;
            wallArr[counter].karma = true;

            //Log test
            String logger = ""+wallArr[counter].karma;
            Log.v("Karma: should be true:", logger);

        }

        //RELEASE BUTTON
        if (intentKarma.getAction().equals(NewAppWidget.WIDGET_RELEASE)) {
            Photo[] wallArr = Wall.photoArr;
            int counter = Wall.counter;
            Photo thisPhoto = wallArr[counter];
            if(thisPhoto!=null) {
                thisPhoto.release = true;
                wallArr[counter].release = true;
            }
            wallArr[counter] = null;
            //Calls mover method to iterate through photo array
            mover(context);

            //Log test
            String logger = ""+wallArr[counter].release;
            Log.v("Release should be true:", logger);

       }

        //NEXT BUTTON
        if (intentKarma.getAction().equals(NewAppWidget.WIDGET_NEXT)) {
            //Calls mover method to iterate through photo array
            mover(context);

        }

        //BACK BUTTON
        if (intentKarma.getAction().equals(NewAppWidget.WIDGET_PREV)) {
            WallpaperManager myWall = Wall.myWall;
            Photo[] wallArr = Wall.photoArr;
            int wallArrSize = wallArr.length;
            int counter = Wall.counter;
            int looper = 0;
            wallArr[counter].shown=false;
            counter--;
            if (counter < 0) {
                counter = wallArrSize + counter;
            }
            if (wallArr[counter] == null) {
                for (looper = 0; looper <= wallArrSize; looper++) {
                    counter--;
                    if (counter < 0) {
                        counter = wallArrSize + counter;
                    }
                    if (wallArr[counter] != null) {
                        break;
                    }
                }
            }
            //Clears wallpaper if entire array is null
            if (wallArr[counter] == null) {
                try {
                    myWall.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            //set wallpaper otherwise
            Photo picToSet = wallArr[counter];
            try {
                String locDisplay = picToSet.locName;
                Log.v(locDisplay, locDisplay);
                //Creates bitmap of proper screen ratio
                Bitmap bm = picToSet.toBitmap(context.getContentResolver());
                bm = Bitmap.createScaledBitmap(bm, 411, 670, true);
                Bitmap newBm = addLocation(locDisplay, bm);
                myWall.setBitmap(newBm);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Wall.counter = counter;
        }
    }

    /**
     * uses counter to keep track of current photo and iterate through the array list
     */
    public void mover(Context context){
        WallpaperManager myWall = Wall.myWall;
        Photo[] wallArr = Wall.photoArr;
        String wall = "" + wallArr.length;
        Log.v(wall, wall);
        int wallArrSize = wallArr.length;
        int counter = Wall.counter;
        int looper = 0;
        counter++;

        /**
         * update counter when counter is greater than or equal to arraySize
         */
        if (counter >= wallArrSize) {
            counter = wallArrSize - counter;
            //or just make counter = 0 if this breaks!
        }
        /**
         * if the array at the counter is null, then break when the picture was removed
         */
        if (wallArr[counter] == null) {
            for (looper = 0; looper <= wallArrSize; looper++) {
                counter++;
                if (counter >= wallArrSize) {
                    counter = wallArrSize - counter;
                }
                if (wallArr[counter] != null) {
                    if(wallArr[counter].shown == false) {
                        Log.v("Mover-Breaking", "Mover-Breaking");
                        break;
                    }
                }
            }
        }
        if (wallArr[counter] == null) {
            try {
                myWall.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Wall.counter = counter;
            return;
        }
        /**
         * Reset all to false once end reached
         */
        //Log.v("before mover if", "before mover if");
        if(wallArr[counter].shown == true){
            for(looper=0; looper<wallArrSize; looper++){
                if(wallArr[looper]!=null){
                    wallArr[looper].shown=false;
                    //Log.v("mover, reseting T/f", "mover, reseting T/f");
                }
            }
            counter = 0;
        }
        /**
         * set the wallpaper to the current photo
         */
        Photo picToSet = wallArr[counter];
        picToSet.shown = true;
        wallArr[counter].shown = true;

        /**
         * pulling the current bitmap, adding location, and setting it to a new bitmap
         */
        try {
            String locDisplay = picToSet.locName;
            Log.v(locDisplay, locDisplay);
            Bitmap bm = picToSet.toBitmap(context.getContentResolver());
            bm = Bitmap.createScaledBitmap(bm, 411, 670, true);
            Bitmap newBm = addLocation(locDisplay, bm);
            myWall.setBitmap(newBm);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Wall.counter = counter;

    }

    /**
     * add location string to bitmap and display on the screen on top of the photo
     */

    public Bitmap addLocation(String locDisplay, Bitmap bm){
        Bitmap newBm = bm.copy(Bitmap.Config.ARGB_8888, true);
        //Canvas stuff to new bm
        Canvas canvas = new Canvas(newBm);

        Paint paint = new Paint();

        paint.setColor(Color.RED);
        paint.setTextSize(25);
        canvas.drawText(locDisplay, 15, 550, paint);
        canvas.drawBitmap(newBm, 0f, 0f, null);

        return newBm;
    }

}

