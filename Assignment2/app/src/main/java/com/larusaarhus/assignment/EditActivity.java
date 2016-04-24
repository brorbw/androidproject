package com.larusaarhus.assignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by brorbw on 23/04/16.
 */
public class EditActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        Intent intent = getIntent();
        setContentView(R.layout.edit_activity);
        ((EditText) findViewById(R.id.editText)).setText(intent.getStringExtra(MainActivity.NAME));
        ((EditText) findViewById(R.id.editid)).setText(intent.getStringExtra(MainActivity.ID));
        ((CheckBox) findViewById(R.id.dev)).setChecked(intent.getBooleanExtra(MainActivity.DEV,false)); // default false for convience
    }

    public void save(View view){
        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREF,Context.MODE_PRIVATE).edit();
        String name = ((EditText) findViewById(R.id.editText)).getText().toString();
        String id = ((EditText) findViewById(R.id.editid)).getText().toString();
        boolean dev = ((CheckBox) findViewById(R.id.dev)).isChecked();
        Log.d(name,id);
        editor.putBoolean(MainActivity.DEV,dev);
        editor.putString(MainActivity.NAME, name);
        editor.putString(MainActivity.ID, id);
        editor.commit();
        this.finish();
    }
    public void cancel(View view){
        this.finish();
    }

}
