package com.bovendorp.andre.paralax;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.io.InputStream;

/**
 * Created by developer on 10/04/17.
 */

public class ParalaxGameObject extends GameObject {
    float speed = 100;
    Bitmap bitmap, reversedBitmaps;

    public void loadImage(String filename, AssetManager manager, int w, int h, boolean matchW, boolean matchH){
        try {
            InputStream inputStream = manager.open(filename);
            bitmap = BitmapFactory.decodeStream(inputStream);
            width = bitmap.getWidth();
            height = bitmap.getHeight();
            inputStream.close();
            if(matchH){
                int newWidth = (width * h / height);
                bitmap = Bitmap.createScaledBitmap(bitmap, newWidth , h , true);
                width = bitmap.getWidth();
                height = bitmap.getHeight();
                Matrix mirrorMatrix = new Matrix();
                mirrorMatrix.setScale(-1,1);
                reversedBitmaps = Bitmap.createBitmap(bitmap, (int)x, (int)y, width, height, mirrorMatrix, true);
            } else {
                int newHeight =(w * height / width);
                bitmap = Bitmap.createScaledBitmap(bitmap, w , newHeight , true);
                width = bitmap.getWidth();
                height = bitmap.getHeight();
                Matrix mirrorMatrix = new Matrix();
                mirrorMatrix.setScale(-1,1);
                reversedBitmaps = Bitmap.createBitmap(bitmap, (int)x, (int)y, width, height, mirrorMatrix, true);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        super.draw(canvas, paint);
        if(x > -width)
            canvas.drawBitmap(bitmap, x, y, paint);

        canvas.drawBitmap(reversedBitmaps, x+width, y, paint);
        canvas.drawBitmap(bitmap, x+2*width, y, paint);
        if(x < -width)
            canvas.drawBitmap(reversedBitmaps, x+3*width, y, paint);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        x -= speed * deltaTime / 1000;
        if(x <= -2* width){
            x=0;
        }
    }
}