package com.larusaarhus.assignment;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;

public class MainActivity extends AppCompatActivity {
    public final static String NAME = "com.larusaarhus.iandroid.NAME";
    public final static String ID = "com.larusaarhus.iandroid.ID";
    public final static int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("LifeCycle","dest");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d("LifeCycle","start");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d("LifeCycle","stop");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d("LifeCycle","pause");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("LifeCycle","resume");
    }
    public void switchActivity(View view){
        Intent intent = new Intent(this,EditActivity.class);
        startActivity(intent);
    }
    public void takePicture(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

    }
}
