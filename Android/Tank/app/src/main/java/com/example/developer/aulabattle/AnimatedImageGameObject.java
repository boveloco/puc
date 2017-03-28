package com.example.developer.aulabattle;


import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.InputStream;

public class AnimatedImageGameObject extends GameObject {
    Bitmap anim[];
    int frames;
    float elapsedTime = 0;
    int currentFrame = 0;
    public int timeToNextFrame = 125;

    public void loadImages(String file, AssetManager manager, int framesW, int framesH){
        try {
            InputStream is = manager.open(file);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            frames = framesW * framesH;
            anim = new Bitmap[frames];
            w = bitmap.getWidth() / framesW;
            h = bitmap.getHeight() / framesH;

            int indice = 0;
            for (int i=0; i<framesW; i++){
                for (int j=0; j < framesH; j++){
                    anim[indice++] = Bitmap.createBitmap(bitmap, i*w, j*w, w, h);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        elapsedTime += deltaTime;
        if (elapsedTime > timeToNextFrame) {
            elapsedTime = 0;
            currentFrame++;
            if(currentFrame >= frames){
                currentFrame = 0;
            }
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        super.draw(canvas, paint);
        canvas.drawBitmap(anim[currentFrame],x ,y, paint);
    }
}
