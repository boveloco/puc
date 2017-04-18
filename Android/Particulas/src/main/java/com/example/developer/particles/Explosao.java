package com.example.developer.particles;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by developer on 17/04/17.
 */

public class Explosao extends GameObject {
    Random r;
    public static final int ESTADO_VIDA = 0;
    public static final int ESTADO_MORTA = 1;

    public int estado = ESTADO_MORTA;

    Particula[] particulas;


    public boolean isVida(){
        return estado == ESTADO_VIDA;
    }

    public boolean isMorta(){
        return estado == ESTADO_MORTA;
    }

    public void loadParticulas(int numeroDeParticulas, int x, int y){
        estado = ESTADO_VIDA;
        particulas = new Particula[numeroDeParticulas];
        for (int i=0; i<particulas.length; i++){
            Particula p = new Particula(x,y);
            particulas[i] = p;
        }
    }

    @Override
    public void update(float deltaTime) {
        if(isVida()){
            boolean morta = true;
            for (int i = 0; i<particulas.length;i++){
                if(particulas[i].isVida()){
                    particulas[i].update(deltaTime);
                    morta = false;
                }
            }
            if(morta){
                estado = ESTADO_MORTA;
            }
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if(isVida()){
            for (int i = 0; i<particulas.length;i++) {
                particulas[i].draw(canvas,paint);
            }
        }
    }

    public Explosao(){

    }
}
