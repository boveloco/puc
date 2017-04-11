package com.bovendorp.andre.paralax;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 10/04/17.
 */
public class GameResources {
    private List<GameObject> gameObjectList;

    private static GameResources ourInstance = new GameResources();

    public static GameResources getInstance() {
        return ourInstance;
    }

    private GameResources() {
        gameObjectList = new ArrayList<>();
    }

    public void addObject(GameObject obj){
        for(int i=0; i <gameObjectList.size(); i++){
            GameObject obj2 = gameObjectList.get(i);
            if(obj.layer <= obj2.layer){
                gameObjectList.add(i, obj);
                return;
            }
        }
        gameObjectList.add(obj);
    }
    public void removeObject(GameObject obj){
        gameObjectList.remove(obj);
    }

    public void updateAndDraw(float deltaTime, Canvas canvas, Paint paint){
        for(GameObject g: gameObjectList){
            g.update(deltaTime);
            g.draw(canvas, paint);
        }
    }

}
