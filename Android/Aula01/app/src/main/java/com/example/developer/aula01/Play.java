package com.example.developer.aula01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Play extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //random num user
        final TextView randNum = (TextView) findViewById(R.id.randNum);

        //botoes
        final Button bPair = (Button) findViewById(R.id.pairButton);
        final Button bImpar = (Button) findViewById(R.id.imparButton);

        //random
        final Random rand = new Random();

        //imput user
        final EditText numUser = (EditText) findViewById(R.id.numUser);


        bPair.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int rNum = rand.nextInt() % 10;
                randNum.setText("" + rNum);
                if (verifyPair(rNum, Integer.parseInt(numUser.getText().toString()))) {
                    Toast.makeText(Play.this, "Voce Ganhou", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Play.this, "Voce Perdeu", Toast.LENGTH_LONG).show();
                }
            }
        });

        bImpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rNum = rand.nextInt() % 10;
                randNum.setText("" + rNum);
                if (!verifyPair(rNum, Integer.parseInt(numUser.getText().toString()))) {
                    Toast.makeText(Play.this, "Voce Ganhou", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Play.this, "Voce Perdeu", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    final boolean verifyPair(int n1, int n2){
        if( ((n1 + n2) & 1) == 0) {
            return true;
        }
        return false;
    }
}
