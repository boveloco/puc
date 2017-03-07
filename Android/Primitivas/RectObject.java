package com.example.developer.primitivas;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by developer on 06/03/17.
 */

public class RectObject extends GameObject {
    float l,t,r,b = 0;
    int color = 0;
    public RectObject setPoints(float left, float top, float right, float bottom){
        this.l = left;
        this.t = top;
        this.r = right;
        this.b = bottom;
        return this;
    }
    @Override
    public RectObject setColor(int color){
        this.color = color;
        return this;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        super.onDraw(canvas, paint);
        paint.setColor(this.color);
        canvas.drawRect(l,t,r,b,paint);

    }
}
