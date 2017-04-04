package com.example.developer.gogogo;

import android.content.res.AssetManager;

/**
 * Created by developer on 03/04/17.
 */

public class BulletGamoObject extends ImageGameObject {
    public float velocity = 0;
    public boolean isRendered = false;
    public BulletGamoObject(String filenamem, AssetManager manager){
        loadImage(filenamem, manager);
    }

    @Override
    public void update(float deltaTime) {
        x += Math.cos(angle) * deltaTime * velocity / 1000;
        y += Math.sin(angle) * deltaTime * velocity / 1000;
    }
}
