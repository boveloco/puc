package com.example.developer.primitivas;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by developer on 06/03/17.
 */

public class PointObject extends GameObject {
    public float size= 10;
    public int color;

    public PointObject setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public  PointObject setSize(int r){
        this.size = r;
        return this;
    }

    @Override
    public PointObject setColor(int c){
        this.color = c;
        return this;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        super.onDraw(canvas, paint);
        paint.setStrokeWidth(size);
        paint.setColor(color);
        canvas.drawPoint(x,y,paint);
    }
}
