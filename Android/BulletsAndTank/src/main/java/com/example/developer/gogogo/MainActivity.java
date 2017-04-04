package com.example.developer.gogogo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decor = getWindow().getDecorView();
        int full = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decor.setSystemUiVisibility(full);
        getSupportActionBar().hide();
        setContentView(new RenderView(this));
    }
}
