package com.example.developer.aulabattle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by developer on 27/03/17.
 */

public class RenderView extends View {

    Paint paint = new Paint();
    public int r = 200, g = 200, b=200;
    float startTime;
    boolean explosion = true;

    TankGameObject player;
    Bullet bull;
    Explosion exp;
    GestureDetector gestureDetector;
    public RenderView(Context context) {
        super(context);
        bull = new Bullet("spriteBala.png", context.getAssets());
        exp = new Explosion("spritesExplosao.png", context.getAssets());
        gestureDetector = new GestureDetector(context, new GestureListener());
        startTime = System.nanoTime();
        player = new TankGameObject("spritesTankAzul.png", context.getAssets());
        player.x = 100;
        player.y = 100;
        GameResources.getInstance().addObject(player);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(bull.isRendered){
            GameResources.getInstance().removeObject(bull);
            bull.isRendered = false;
        }
        bull.x = player.x;
        bull.y = player.y;
        bull.velocity = 150;
        bull.angle = player.angle;
        GameResources.getInstance().addObject(bull);
        bull.isRendered = true;
        explosion = true;
        exp.currentFrame = 0;
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawRGB(r,g,b);
        float deltaTime = (System.nanoTime() - startTime)/1000000.0f;
        startTime = System.nanoTime();
        GameResources.getInstance().updateAndDraw(deltaTime, canvas, paint);
        if ((bull.x > getWidth() || bull.y > getHeight() || bull.x < 0 || bull.y < 0) && explosion) {
            exp.x = bull.x;
            exp.y = bull.y;
            GameResources.getInstance().addObject(exp);
            explosion = false;
        }
        if(exp.currentFrame == 3){
            GameResources.getInstance().removeObject(exp);
            explosion = true;
        }
        invalidate();
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();
            if (Math.abs(diffX) > Math.abs(diffY)){
                if(Math.abs(diffX) > 100 && Math.abs(velocityX) > 100){
                    if(diffX > 0){
                        //swipeRight
                        player.angle = 0;
                    } else {
                        //swipeLeft
                        player.angle = (float) Math.PI;
                    }
                }
            }
            return result;
        }

    }
}
