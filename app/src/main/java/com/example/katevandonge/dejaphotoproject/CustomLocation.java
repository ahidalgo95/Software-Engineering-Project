package com.example.katevandonge.dejaphotoproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v4.content.FileProvider;


import java.io.File;

public class CustomLocation extends AppCompatActivity {

    private static final int SELECTED_PIC = 1;

    int counter = 1;
    ImageView imageView;
    EditText editText;
    int ii;
    int curr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        curr = Wall.counter;


        imageView = (ImageView)findViewById(R.id.imageView4);
        editText = (EditText)findViewById(R.id.editText);

        Button switchScreen = (Button) findViewById(R.id.button4);
        switchScreen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        /*Button camera = (Button) findViewById(R.id.button7);
        camera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });*/

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.v("in on click", "hi");
                if(Wall.photoArr[curr].ogAlbum != 3) {
                    Wall.photoArr[curr].locName = editText.getText().toString();
                }
                else{
                    Log.i("CustomLocation: ", "can't change location on friend's photo");
                }
                //Wall.photoArr[curr].locName = editText.getText().toString();
                Log.v("custom", editText.getText().toString());
            }
        });
    }

    public void btnClick(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PIC);
    }

   public void btnClick2(View view){
        //File fileDir = new File(Environment.getExternalStorageDirectory()+File.separator+".privPhotos");
        //Uri fileUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", fileDir);
        //Intent intent = new Intent(Intent.ACTION_PICK, fileUri);
      /* Uri camUri = DejaPhoto.onePhotoUri;
       for(int i=0; i<Wall.photoArr.length; i++) {
           if (Wall.photoArr[i].photouri.equals(camUri)) {
               Log.v("CAM", "URI matchh");
               ii=i;
               //Wall.photoArr[i].locName = editText.getText().toString();
               Log.v("CAM", editText.getText().toString());
               break;
           }
       }*/

        //startActivityForResult(intent, SELECTED_PIC);

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
                    for(int i=0; i<Wall.photoArr.length; i++) {
                        if (Wall.photoArr[i].photouri.equals(uri)) {
                            Log.v("custom location", "URI matchh");
                            curr=i;
                            //Wall.photoArr[i].locName = editText.getText().toString();
                            Log.v("custom", editText.getText().toString());
                            break;
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}
