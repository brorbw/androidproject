package com.larusaarhus.assignment;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public final static String NAME = "com.larusaarhus.iandroid.NAME";
    public final static String ID = "com.larusaarhus.iandroid.ID";
    public final static int REQUEST_IMAGE_CAPTURE = 1;
    public final static String PICTURE = "picture.jpg";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        initPicture();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle", "dest");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LifeCycle", "start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCycle", "stop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCycle", "pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle", "resume");
    }

    public void switchActivity(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } else {
            Log.d("Camera", "some error starting the camera");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            imageView.setImageBitmap(image);
            new SaveImage().execute(image);
        }
    }


    private void initPicture() {
        String filePath = getApplicationContext().getFilesDir() + "/" + PICTURE + ".jpg";
        File file = new File(filePath);
        if(file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageDrawable(getDrawable(R.drawable.face));
        }
    }

    private class SaveImage extends AsyncTask<Bitmap, Void, Void> {
        @Override
        protected Void doInBackground(Bitmap ... b) {
            if(getApplicationContext().getFilesDir().canWrite()) {
                String path = getApplicationContext().getFilesDir() + "/" + PICTURE + ".jpg";
                File myFile = new File(path);
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(myFile);
                    b[0].compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                } catch (Exception e) {
                    Log.d("Camera", "unable to write to buffer");
                } finally {
                    //fos.close();
                }
            } else {
                Log.d("Camera","unable to write to media. No permissions");
                throw new NullPointerException();
            }
        return null;
        }
    }

}
