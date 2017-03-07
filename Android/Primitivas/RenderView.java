package com.example.developer.primitivas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
/**
 * Created by developer on 06/03/17.
 */

public class RenderView extends View{

    Paint paint = new Paint();
    int r= 200, g= 0, b= 0;

    public RenderView(Context c){
        super(c);

        PointObject po = new PointObject().setPosition(100,100).setSize(100).setColor(Color.BLUE);
        GameResources.getInstance().gameObjects.add(po);
        CircleObject co = new CircleObject().setPosition(1000,1000).setRadius(500).setStroke(100);
        GameResources.getInstance().gameObjects.add(co);
        RectObject ro = new RectObject().setPoints(0,700,400,1070).setColor(Color.GREEN);
        GameResources.getInstance().gameObjects.add(ro);
        TriangleObject to = new TriangleObject().setPoints(200,300,400,700,900,800).setColor(Color.MAGENTA);
        GameResources.getInstance().gameObjects.add(to);
        StarObject so = new StarObject().setPoints(0,80,130,-20,260,80,230,230,80,230).setColor(Color.CYAN);
        GameResources.getInstance().gameObjects.add(so);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            CircleObject circleObject = new CircleObject().setColor(Color.BLACK).setPosition((int)event.getX(), (int)event.getY()).setRadius(30);
            GameResources.getInstance().gameObjects.add(circleObject);
            invalidate();
            return true;
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            GameResources.getInstance().gameObjects.get(4).setPosition((int)event.getX(), (int)event.getY());
            invalidate();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP){
            GameResources.getInstance().gameObjects.get(4).setPosition(130,130);
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRGB(r,g,b);
        for (GameObject go : GameResources.getInstance().gameObjects){
            go.onDraw(canvas, paint);
        }
    }
}
