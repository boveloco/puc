package com.example.developer.particles;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 17/04/17.
 */
public class GameResources {
    private static GameResources ourInstance = new GameResources();

    public static GameResources getInstance() {
        return ourInstance;
    }

    private GameResources() {
    }
    public List<GameObject> gameObjectsList = new ArrayList<>();

    public void addObject(GameObject obj){
        for (int i =0; i < gameObjectsList.size(); i++){
            GameObject obj2 = gameObjectsList.get(i);
            if(obj.layer <= obj.layer){
                gameObjectsList.add(i, obj);
                return;
            }
        }
        gameObjectsList.add(obj);
    }

    public void removeObjject(GameObject obj){
        gameObjectsList.remove(obj);
    }

    public void updateAndDraw(float deltaTime, Canvas canvas, Paint paint){
        for (GameObject obj: gameObjectsList){
            obj.update(deltaTime);
            obj.draw(canvas, paint);
        }
    }
}
