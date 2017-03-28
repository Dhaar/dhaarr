package com.example.boopathyraj.lims;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


public class Firstpage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

       TimerTask task=new TimerTask() {
           @Override
           public void run() {
               Intent fb=new Intent(Firstpage.this,Signin.class);
               startActivity(fb);
               finshscreen();
           }
       };
        Timer t=new Timer();
        t.schedule(task,1500);

       }
    private void finshscreen(){
        this.finish();
    }

}




