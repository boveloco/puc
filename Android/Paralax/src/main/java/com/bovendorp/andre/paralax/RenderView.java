package com.bovendorp.andre.paralax;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by developer on 10/04/17.
 */

public class RenderView extends View {
    Paint paint = new Paint();
    float startTime = 0.0f;

    ParalaxGameObject paralax, chaoGameObject;
    public RenderView(Context context) {
        super(context);
        startTime = System.nanoTime();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(paralax != null) {
            return;
        }
        paralax = new ParalaxGameObject();
        chaoGameObject = new ParalaxGameObject();
        paralax.loadImage("introBackground.png", getContext().getAssets(), getWidth(), getHeight(), false, true);
        paralax.layer = 1;
        paralax.speed = 100;
        GameResources.getInstance().addObject(paralax);
        chaoGameObject.loadImage("chao.png", getContext().getAssets(), paralax.width, getHeight(), true, false);
        chaoGameObject.layer = 2;
        chaoGameObject.speed = 500;
        chaoGameObject.y = getHeight()-chaoGameObject.height;
        GameResources.getInstance().addObject(chaoGameObject);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float deltaTIme = (System.nanoTime() - startTime)/1000000;
        startTime = System.nanoTime();
        canvas.drawRGB(0,168,213);
        GameResources.getInstance().updateAndDraw(deltaTIme,canvas,paint);
        invalidate();
    }
}
