package com.example.developer.primitivas;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by developer on 06/03/17.
 */

public class GameObject {

    public int x= 0, y= 0;
    public void onDraw(Canvas canvas, Paint paint) {}
    public GameObject setColor(int color){return this;}
    public GameObject setPosition(int x, int y) {this.x = x; this.y = y; return this;}
}
