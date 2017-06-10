package com.example.katevandonge.dejaphotoproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PhotoPicker extends AppCompatActivity {

    private static final int SELECTED_PIC =1;
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    int counter = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView)findViewById(R.id.imageView);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView3 = (ImageView)findViewById(R.id.imageView3);

        Button switchScreen= (Button) findViewById(R.id.button2);
        switchScreen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    public void btnClick(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case SELECTED_PIC:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Thumbnails.DATA};
                    Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filepath=cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                    Drawable drawable = new BitmapDrawable(bitmap);
                    imageView.setBackground(drawable);

                    MainActivity.dpcopied.addPhoto(uri);
                    try {
                        MainActivity.master.updateMasterQ(MainActivity.copiedMode, MainActivity.cameraMode, MainActivity.friendMode);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent intentChi = new Intent(this, MainActivity.class);
                    intentChi.putExtra("imageURI", uri.toString());
                    //setResult(1, intentChi);
                    //startActivity(intentChi);
                    //this.finish();

                    //DejaPhotoCopied.addPhoto(uri);
                    /*for(int i=0; i<Wall.photoArr.length; i++){
                        Log.v("Scrolling", "In the for");
                        if(Wall.photoArr[i].photouri.equals(uri)){
                            Log.v("Scrolling", "URI MATch");
                            Wall.photoArr[i].DJP=true;
                            me = Wall.photoArr[i].DJP + "";
                            k = i;
                            break;
                        }
                    }*/

                }
                break;
            default:
                break;
        }
    }
}
