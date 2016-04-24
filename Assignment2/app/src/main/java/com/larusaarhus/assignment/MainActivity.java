package com.larusaarhus.assignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends Activity {
    public final static String PREF = "com.larusaarhus.iandroid.PREF";
    public final static String NAME = "com.larusaarhus.iandroid.NAME";
    public final static String ID = "com.larusaarhus.iandroid.ID";
    public final static String DEV = "com.larusaarhus.iandroid.DEV";
    public final static int REQUEST_IMAGE_CAPTURE = 1;
    public final static String PICTURE = "picture.jpg";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LifeCycle", "create");
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        initPicture();
        updateText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle", "dest");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(imageView == null){
            imageView = (ImageView) findViewById(R.id.imageView);
        }
        updateText();
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
        intent.putExtra(NAME,((TextView) findViewById(R.id.name)).getText().toString());
        intent.putExtra(ID,((TextView) findViewById(R.id.id)).getText().toString());
        intent.putExtra(DEV,((CheckBox) findViewById(R.id.checkBox)).isChecked());
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

    private void updateText(){
        SharedPreferences preferences = getSharedPreferences(PREF,Context.MODE_PRIVATE);
        String name = preferences.getString(NAME,null);
        String id = preferences.getString(ID,null);
        boolean dev = preferences.getBoolean(DEV, false);
        if(dev){
            CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
            checkBox.setChecked(dev);
        } else {
            CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
            checkBox.setChecked(false);
            Log.d("Prefs", "No dev definede yet");
        }
        if(name != null){
            TextView textView = (TextView) findViewById(R.id.name);
            textView.setText(name);
        } else {
            Log.d("Prefs", "No name definede yet");
        }
        if(id != null){
            TextView textView = (TextView) findViewById(R.id.id);
            textView.setText(id);
        } else {
            Log.d("Prefs", "No id definede yet");
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
