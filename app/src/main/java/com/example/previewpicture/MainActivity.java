package com.example.previewpicture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.previewpicture.custom.GridViewCustomActivity;
import com.example.previewpicture.list.GridView2Activity;
import com.example.previewpicture.list.ListView2Activity;
import com.example.previewpicture.nine.activity.GridStyleActivity;
import com.example.previewpicture.rec.RecycleViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        findViewById(R.id.button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ListView2Activity.class);
                        startActivity(intent);

                    }
                });
        findViewById(R.id.button2)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, RecycleViewActivity.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.button3)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, GridStyleActivity.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.button4)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, GridView2Activity.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.button6)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, GridViewCustomActivity.class);
                        intent.putExtra("type", 0);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.button7)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, GridViewCustomActivity.class);
                        intent.putExtra("type", 1);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.button8)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, GridViewCustomActivity.class);
                        intent.putExtra("type", 2);
                        startActivity(intent);
                    }
                });
    }
}
