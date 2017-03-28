package com.example.developer.aulabattle;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by developer on 27/03/17.
 */

public class Explosion extends AnimatedImageGameObject {
    Matrix matrix = new Matrix();
    public Explosion(String file, AssetManager manager) {

        loadImages(file, manager, 4,1);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        matrix.reset();
        matrix.preTranslate(x-w/2,y-h/2);
        canvas.drawBitmap(anim[currentFrame], matrix, paint);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
