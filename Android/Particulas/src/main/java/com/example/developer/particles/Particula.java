package com.example.developer.particles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by developer on 17/04/17.
 */

public class Particula extends GameObject {
    Random r;
    public static final int ESTADO_VIDA = 0;
    public static final int ESTADO_MORTA = 1;

    public static final int TEMPO_DE_VIDA = 200;
    public static final int TAMANHO_MAXINO = 15;
    public static final int VELOCIDADE_MAXIMA = 20;

    int estado;

    double xv, yv;

    int idade;
    int tempoDeVida;
    int cor;
    int alpha,red,green,blue;

    public boolean isVida(){
        return estado == ESTADO_VIDA;
    }

    public boolean isMorta(){
        return estado == ESTADO_MORTA;
    }
    public Particula(float x, float y){
        r = new Random();
        this.x = x;
        this.y = y;

        this.idade = 0;

        this.tempoDeVida = TEMPO_DE_VIDA;
        this.alpha = 255;
        this.red = r.nextInt(255);
        this.green = r.nextInt(255);
        this.blue = r.nextInt(255);

        this.estado = ESTADO_VIDA;

        this.width = r.nextInt(TAMANHO_MAXINO);

        height = width;
        cor = Color.argb(255, r.nextInt(255), r.nextInt(255), r.nextInt(255));

        xv = r.nextDouble()*VELOCIDADE_MAXIMA*2-VELOCIDADE_MAXIMA;
        yv = r.nextDouble()*VELOCIDADE_MAXIMA*2-VELOCIDADE_MAXIMA;
    }

    @Override
    public void update(float deltaTime) {
        if(isVida()){
            x += xv * deltaTime * 0.1f;
            y += yv * deltaTime * 0.1f;

            alpha -= 2;
            idade++;
            if(idade >= tempoDeVida || alpha <= 0){
                estado = ESTADO_MORTA;
            } else {
                cor = Color.argb(alpha,red,green,blue);
            }
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        super.draw(canvas, paint);
        paint.setColor(cor);
        paint.setAlpha(alpha);
        canvas.drawRect(x,y,x+width,y+height,paint);
        paint.reset();

    }
}
