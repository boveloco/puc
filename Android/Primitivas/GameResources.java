package com.example.developer.primitivas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 06/03/17.
 */
public class GameResources {
    private static GameResources ourInstance = new GameResources();

    public static GameResources getInstance() {
        return ourInstance;
    }

    public List<GameObject> gameObjects = new ArrayList<>();

    private GameResources() {

    }
}
