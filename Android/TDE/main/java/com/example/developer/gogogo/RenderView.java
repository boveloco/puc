package com.example.developer.gogogo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.Image;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by developer on 03/04/17.
 */

public class RenderView extends View {

    Paint paint = new Paint();
    float startTime = 0;
    Explosion exp;
    Boolean explosion =  true;
    TankGameObject player;
    GestureDetector gestureDetector;
    ImageGameObject tank, ofset1, ofset2, ofset3, ofset4;
    Random r;

    public RenderView(Context context) {
        super(context);
        r = new Random();
        exp = new Explosion("spritesExplosao.png", context.getAssets());
        gestureDetector = new GestureDetector(context, new GestureListener());

        startTime = System.nanoTime();
        BulletGamoObject bullet1 = new BulletGamoObject("spriteBala.png", context.getAssets());
        bullet1.x = 100;
        bullet1.y = 100;
        bullet1.velocity = 500;
        bullet1.name = "Bullet1";
        GameResources.getInstance().addObject(bullet1);

        startTime = System.nanoTime();
        BulletGamoObject bullet2 = new BulletGamoObject("spriteBala.png", context.getAssets());
        bullet2.x = 600;
        bullet2.y = 100;
        bullet2.angle = Math.PI;
        bullet2.velocity = 500;
        bullet2.name = "Bullet2";
        GameResources.getInstance().addObject(bullet2);

        player = new TankGameObject("spritesTankAzul.png", context.getAssets());
        player.x = 534;
        player.y = 953;
        player.name = "tank";
        GameResources.getInstance().addObject(player);

        BulletGamoObject bullet3 = new BulletGamoObject("spriteBala.png", context.getAssets());
        bullet3.x = r.nextInt(720);
        bullet3.y = r.nextInt(1280);
        bullet3.velocity = 500;
        bullet3.name = "bullet3";
        GameResources.getInstance().addObject(bullet3);

        tank = new ImageGameObject();
        tank.loadImage("Tank.png", context.getAssets());
        tank.x = r.nextInt(720);
        tank.y = r.nextInt(1280);
        tank.name = "tankcomborda";

        ofset1 = new ImageGameObject();
        ofset2 = new ImageGameObject();
        ofset3 = new ImageGameObject();
        ofset4 = new ImageGameObject();

        ofset1.bitmap = tank.bitmap.copy(Bitmap.Config.ALPHA_8, true);
        ofset1.x = tank.x + 10;
        ofset1.y = tank.y;
        ofset1.width = tank.width;
        ofset1.height = tank.height;

        ofset2.bitmap = tank.bitmap.copy(Bitmap.Config.ALPHA_8, true);
        ofset2.x = tank.x;
        ofset2.y = tank.y + 10;
        ofset2.width = tank.width;
        ofset2.height = tank.height;

        ofset3.bitmap = tank.bitmap.copy(Bitmap.Config.ALPHA_8, true);
        ofset3.x = tank.x - 10;
        ofset3.y = tank.y;
        ofset3.width = tank.width;
        ofset3.height = tank.height;

        ofset4.bitmap = tank.bitmap.copy(Bitmap.Config.ALPHA_8, true);
        ofset4.x = tank.x - 10;
        ofset4.y = tank.y - 10;
        ofset4.width = tank.width;
        ofset4.height = tank.height;


        GameResources.getInstance().addObject(ofset1);
        GameResources.getInstance().addObject(ofset2);
        GameResources.getInstance().addObject(ofset3);
        GameResources.getInstance().addObject(ofset4);
        GameResources.getInstance().addObject(tank);


    }

    public void resolveCollision(GameObject obj1, GameObject obj2){
        if(obj1.name.equalsIgnoreCase("tank") && explosion){
            generateExplosion(obj1, obj2);
        }
        if(obj2.name.equalsIgnoreCase("tank") && explosion){
            generateExplosion(obj2, obj1);
        }
    }

    public void generateExplosion(GameObject obj, GameObject obj2) {
        exp.x = obj.x;
        exp.y = obj.y;
        GameResources.getInstance().addObject(exp);
        explosion = false;
        GameResources.getInstance().removeObject(obj2);
    }

    public boolean onTouchEvent(MotionEvent event) {
        BulletGamoObject obj = new BulletGamoObject("spriteBala.png", getContext().getAssets());
        obj.x = r.nextInt(720);
        obj.y = r.nextInt(1280);
        obj.velocity = 500;
        obj.angle = Math.PI/2;
        GameResources.getInstance().addObject(obj);
        return gestureDetector.onTouchEvent(event);
    }

    public void randomizeBullets(GameObject obj){
        int v = r.nextInt(4);
        System.out.println(v);
        switch (v){
            case 0:
                obj.angle = Math.PI/2 * 3;
                obj.x = r.nextInt(getWidth());
                obj.y = 0;
                break;
            case 1:
                obj.angle = Math.PI/2;
                obj.x = r.nextInt(getWidth());
                obj.y = getHeight();
                break;
            case 2:
                obj.angle = 0;
                obj.x = 0;
                obj.y = r.nextInt(getHeight());
                break;
            case 3:
                obj.angle = Math.PI;
                obj.x = getWidth();
                obj.y = r.nextInt(getHeight());
                break;
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float deltaTime = (System.nanoTime() - startTime)/1000000;
        startTime = System.nanoTime();
        if(exp.currentFrame == 3){
            GameResources.getInstance().removeObject(exp);
            explosion = true;
        }

        for (int i = 0; i< GameResources.getInstance().gameObjectList.size()-1; i++){
            GameObject obj1 = GameResources.getInstance().gameObjectList.get(i);
            for (int j = i+1; j< GameResources.getInstance().gameObjectList.size(); j++){
                GameObject obj2 = GameResources.getInstance().gameObjectList.get(j);
                if ((obj1.x > getWidth() || obj1.y > getHeight() || obj1.x < 0 || obj1.y < 0) && explosion){
                    randomizeBullets(obj1);
                }
                if ((obj2.x > getWidth() || obj2.y > getHeight() || obj2.x < 0 || obj2.y < 0) && explosion){
                    randomizeBullets(obj2);
                }
                if(obj1.getBoundingBox().intersect(obj2.getBoundingBox())){
                    // System.out.println("Colidiu");
                    resolveCollision(obj1, obj2);
                }
            }
        }

        canvas .drawRGB(200,200,200);
        GameResources.getInstance().updateAndDraw(deltaTime,canvas,paint);
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
            } else {
                if(Math.abs(diffY) > 100 && Math.abs(velocityY) > 100){
                    if(diffY > 0){
                        //swipeUp
                        player.angle = (float) Math.PI/2;
                    } else {
                        //swipeDown
                        player.angle = (float) Math.PI/2 *3;
                    }
                }
            }
            return result;
        }

    }
}
