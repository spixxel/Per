package org.a07planning.androidgame2d;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class ChibiCharacter{

    public ChibiCharacterSceneObject sceneObject;

    private int hitpoints = 10;

    public int bulletsTargeting = 0;

    // Velocity of game character (pixel/millisecond)
    private static final float VELOCITY = 0.1f;

    private int movingVectorX = 10;
    private int movingVectorY = 5;

    private GameSurface gameSurface;

    public ChibiCharacter(GameSurface gameSurface, ChibiCharacterSceneObject sceneObject) {
        this.sceneObject = sceneObject;
        this.gameSurface= gameSurface;
    }

    public void target()
    {
        bulletsTargeting++;
    }

    public boolean targetable()
    {
        if(bulletsTargeting >= hitpoints) return false;
        return true;
    }

    public boolean hit()
    {
        hitpoints--;
        bulletsTargeting--;
        if(hitpoints <= 0)
        {
            return false;
        }
        return true;
    }

    public void move(int deltaTime)  {
        sceneObject.animate();
        // Distance moves
        float distance = VELOCITY * deltaTime;
        double movingVectorLength = Math.sqrt(movingVectorX* movingVectorX + movingVectorY*movingVectorY);
        int status = sceneObject.move(
                (int)(distance* movingVectorX / movingVectorLength),
                (int)(distance* movingVectorY / movingVectorLength),
                this.gameSurface.getWidth(),
                this.gameSurface.getHeight());

        if(status == 1) //hit x wall
        {
            movingVectorX = - movingVectorX;
        }
        if(status == 2) //hit y wall
        {
            movingVectorY = - movingVectorY ;
        }
    }

    public void setMovingVector(int movingVectorX, int movingVectorY)  {
        this.movingVectorX= movingVectorX;
        this.movingVectorY = movingVectorY;
    }

    public boolean isInside(int x, int y)
    {
        return sceneObject.isInside(x,y);
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