package com.example.developer.gogogo;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.io.InputStream;

/**
 * Created by developer on 03/04/17.
 */

public class ImageGameObject extends GameObject {

    Bitmap bitmap;
    Matrix matrix = new Matrix();

    public void loadImage(String filename, AssetManager manager){
        try {
            InputStream inputStream = manager.open(filename);
            bitmap = BitmapFactory.decodeStream(inputStream);
            width = bitmap.getWidth();
            height = bitmap.getHeight();
            inputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        matrix.reset();
        matrix.preTranslate(x-width/2, y-height/2);
        matrix.preRotate((float) (angle*180/Math.PI), width/2, height/2);
        canvas.drawBitmap(bitmap, matrix, null);
    }
}
