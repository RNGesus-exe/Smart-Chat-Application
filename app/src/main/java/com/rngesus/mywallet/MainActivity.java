package com.rngesus.mywallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rngesus.mywallet.Login.Number_verify;

public class MainActivity extends AppCompatActivity {
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Thread thread=new Thread() {
            public void run() {
                try {
                    sleep(5000);
                }catch (Exception ex)
                {
                    ex.printStackTrace();

                }finally {
                    Intent i = new Intent(MainActivity.this, Number_verify.class);
                    startActivity(i);
                    finish();
                }

            }
        };thread.start();
    }
}