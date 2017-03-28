package com.example.developer.aulabattle;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class GameResources {
    private static GameResources ourInstance = new GameResources();

    List<GameObject> gameObjectList = new ArrayList<>();


    public static GameResources getInstance() {
        return ourInstance;
    }

    private GameResources() {
    }

    public void addObject(GameObject obj) {
        this.gameObjectList.add(obj);
    }

    public void removeObject(GameObject obj) {
        gameObjectList.remove(obj);
    }

    public void updateAndDraw(float deltaTime, Canvas canvas, Paint paint) {
        for (GameObject g : gameObjectList) {
            g.update(deltaTime);
            g.draw(canvas, paint);
        }
    }
}
