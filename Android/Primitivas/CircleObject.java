package com.example.developer.primitivas;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by developer on 06/03/17.
 */

public class CircleObject extends GameObject {
    public float radius = 500;
    public int color = 0;
    public float strokeWidth = 1;
    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        super.onDraw(canvas, paint);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);
        canvas.drawCircle(x,y,radius,paint);
    }

    public CircleObject setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
    @Override
    public CircleObject setColor(int c){
        this.color = c;
        return this;
    }

    public CircleObject setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    public CircleObject setStroke(float s){
        this.strokeWidth = s;
        return this;
    }
}
