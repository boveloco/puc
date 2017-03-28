package com.example.developer.aulabattle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decor = getWindow().getDecorView();
        int ui0pt = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decor.setSystemUiVisibility(ui0pt);

        getSupportActionBar().hide();
        setContentView(new RenderView(this));


    }
}
