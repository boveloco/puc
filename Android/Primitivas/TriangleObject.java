package com.example.developer.primitivas;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by developer on 06/03/17.
 */

public class TriangleObject extends GameObject {

    int x2,x3,y2,y3, color = 0;

    public TriangleObject setPoints(int x1, int y1, int x2, int y2, int x3, int y3){
        this.x = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y = y1;
        this.y2 = y2;
        this.y3 = y3;
        return this;
    }
    @Override
    public TriangleObject setColor(int c){
        this.color = c;
        return this;
    }


    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        super.onDraw(canvas, paint);
        paint.setColor(color);
        paint.setStrokeWidth(10);
        canvas.drawLine(x,y,x2,y2,paint);
        canvas.drawLine(x2,y2,x3,y3,paint);
        canvas.drawLine(x,y,x3,y3,paint);
    }
}
