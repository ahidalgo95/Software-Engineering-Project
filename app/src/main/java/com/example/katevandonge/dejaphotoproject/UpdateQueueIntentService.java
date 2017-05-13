package com.example.katevandonge.dejaphotoproject;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UpdateQueueIntentService extends IntentService {

    int rate;

    public UpdateQueueIntentService() {
        super("worker");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(UpdateQueueIntentService.this, "SERVICE STARED", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        Toast.makeText(UpdateQueueIntentService.this, "SERVICE STOPPED", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        while (intent != null) {
            //String Srate = (String) intent.getExtras().get("rate");
            //Log.v(Srate, Srate);
            //int Qrate = Integer.parseInt((String)Srate);

            Intent intent2 = new Intent(UpdateQueueIntentService.this, NewAppWidget.class);
            String WIDGET_NEXT = "NEXT_BUTTON";
            intent2.setAction(WIDGET_NEXT);
            NewAppWidget widget = new NewAppWidget();
            widget.onReceive(getApplicationContext(), intent2);

            synchronized (this){
                try {
                    wait(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
