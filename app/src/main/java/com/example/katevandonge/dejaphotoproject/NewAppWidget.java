package com.example.katevandonge.dejaphotoproject;

import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.RemoteViews;
import static android.R.attr.button;

import android.Manifest;

import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    Gallery pList;
    WallpaperManager myWall;
    public static String WIDGET_BUTTON = "MY_PACKAGE_NAME.WIDGET_BUTTON";
    public static String WIDGET_NEXT = "NEXT_BUTTON";
    public static String WIDGET_PREV = "PREV_BUTTON";

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        String but = "hello world!";

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, but);


        Intent intentA = new Intent(WIDGET_BUTTON);
        // This button launches the app
        intentA.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        //OPEN APP BUTTON
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.button6, pendingIntent);

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



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            //final int N = appWidgetIds.length;

            // Perform this loop procedure for each App Widget that belongs to this provider
            //for (int i=0; i<N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
                Log.v("for", "gfor");
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
        if (intentKarma.getAction().equals(NewAppWidget.WIDGET_BUTTON)) {
            Log.v("karmwidgetIFF", "karmawidgetIFF");
            Toast.makeText(context, "HELLO", Toast.LENGTH_SHORT).show();
            //String kate = Wall.kate;
            //Log.v(kate, kate);
            Karma fuck = new Karma();
            fuck.switching();
        }
        if (intentKarma.getAction().equals(NewAppWidget.WIDGET_NEXT)) {
            Log.v("NEXTwidgetIFF", "NEXTwidgetIFF");
            Toast.makeText(context, "NEXT", Toast.LENGTH_SHORT).show();
            //Wall thisWoll = new Wall(); //Wall.Woll;
            //thisWoll.next();
            //Wall wal = new Wall(context, Wall.pList, Wall.myWall);
            //wal.next();
            /*int index = Wall.index;
            index++;
            Wall.index = index;
            String widind = "" + index;
            String wallind = "" + Wall.index;
            Log.v(widind, widind);
            Log.v(wallind, wallind);*/
            WallpaperManager myWall = Wall.myWall;
            try {
                myWall.setResource(+R.drawable.pic);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        if (intentKarma.getAction().equals(NewAppWidget.WIDGET_PREV)) {
            Log.v("BACKwidgetIFF", "BACKwidgetIFF");
            Toast.makeText(context, "BACK", Toast.LENGTH_SHORT).show();
            WallpaperManager myWall = Wall.myWall;
            try {
                myWall.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }


}

