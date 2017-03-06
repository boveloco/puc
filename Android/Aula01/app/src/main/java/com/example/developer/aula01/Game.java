package com.example.developer.aula01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final EditText nomeJogador = (EditText) findViewById(R.id.textPersonName);

        Button b = (Button) findViewById(R.id.playGameButton);

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent telaGame = new Intent(Game.this, Play.class);
                startActivity(telaGame);
            }
        });

    }
}
