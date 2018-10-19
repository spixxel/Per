package org.a07planning.androidgame2d;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;

public class Tower{

    public TowerSceneObject sceneObject;
    int cooldown = 0;

    public static final int gridWidth = 2;
    public static final int gridHeight = 2;

    private GameSurface gameSurface;

    public Tower(GameSurface gameSurface, TowerSceneObject sceneObject) {
        this.sceneObject = sceneObject;
        this.gameSurface= gameSurface;
    }

    public boolean isInside(int x, int y)
    {
        return sceneObject.isInside(x,y);
    }

    boolean animationOngoing = false;


    public boolean shoot()
    {
        if(cooldown == 20)
        {
            animationOngoing = true;
            cooldown = 0;
            return true;
        }
        return false;
    }

    public ChibiCharacter findTarget(List<ChibiCharacter> enemies)
    {
        for(ChibiCharacter enemy: enemies)
        {
            if((Math.abs(sceneObject.getX() - enemy.sceneObject.getX())+
                    Math.abs(sceneObject.getY() - enemy.sceneObject.getY())) < 500)
            {
                return enemy;
            }
        }
        return null;
    }

    public void update()  {
        if(animationOngoing) {
            animationOngoing = sceneObject.animate();
        }
        if(cooldown < 20)
        {
            cooldown++;
        }
    }

    public int getX()
    {
        return sceneObject.getX();
    }

    public int getY()
    {
        return sceneObject.getY();
    }
}