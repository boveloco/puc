package com.example.developer.particles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by developer on 17/04/17.
 */

public class RenderView extends View {
    Paint paint = new Paint();
    float startTime;
    Explosao explosao;

    public RenderView(Context context){
        super(context);
        explosao = new Explosao();
        GameResources.getInstance().addObject(explosao);
        startTime = System.nanoTime();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float deltaTime = (System.nanoTime() - startTime) / 1000000.0f;
        startTime = System.nanoTime();

        canvas.drawRGB(200,200,200);
        GameResources.getInstance().updateAndDraw(deltaTime, canvas, paint);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            explosao.loadParticulas(200, (int) event.getX(), (int) event.getY());
        }
        return super.onTouchEvent(event);
    }
}
