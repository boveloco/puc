package com.example.developer.primitivas;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by developer on 06/03/17.
 */

public class StarObject extends GameObject {

    public int x1,y1,x2,x3,x4,x5,y2,y3,y4,y5,color;

    StarObject(){
        this.x = 0;
        this.y = 0;
    }

    public StarObject setPoints(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int x5, int y5){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
        this.x5 = x5;
        this.y2 = y2;
        this.y3 = y3;
        this.y4 = y4;
        this.y5 = y5;
        return this;
    }
    @Override
    public StarObject setPosition(int x, int y){
        this.x = x - 130;
        this.y = y - 130;
        return this;
    }
    @Override
    public StarObject setColor(int color) {
        this.color = color;
        return this;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        super.onDraw(canvas, paint);
        paint.setColor(color);
        paint.setStrokeWidth(5);

        canvas.drawLine(x+x1,y+y1,x+x3,y+y3,paint);
        canvas.drawLine(x+x1,y+y1,x+x4,y+y4,paint);
        canvas.drawLine(x+x2,y+y2,x+x4,y+y4,paint);
        canvas.drawLine(x+x2,y+y2,x+x5,y+y5,paint);
        canvas.drawLine(x+x3,y+y3,x+x5,y+y5,paint);

    }
}
