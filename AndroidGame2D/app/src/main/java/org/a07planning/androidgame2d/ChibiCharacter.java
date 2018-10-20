package org.a07planning.androidgame2d;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;

public class ChibiCharacter{

    public ChibiCharacterSceneObject sceneObject;

    private int hitpoints = 10;

    public int bulletsTargeting = 0;

    // Velocity of game character (pixel/millisecond)
    private static final float VELOCITY = 0.1f;

    private int movingVectorX = 10;
    private int movingVectorY = 5;

    private List<Coordinate> path;
    int pathDistance = 0;
    private Pathfinder pathfinder = new Pathfinder();

    private GameSurface gameSurface;

    private Grid grid;


    public ChibiCharacter(GameSurface gameSurface, ChibiCharacterSceneObject sceneObject, Grid grid) {
        this.sceneObject = sceneObject;
        this.gameSurface= gameSurface;
        this.grid = grid;
        //initPath
        path = pathfinder.findPath(new Coordinate(0,0), new Coordinate(0,43), grid);
        int apa = 0;
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
        int gridX = grid.getXGridCell(getX());
        int gridY = grid.getYGridCell(getY());

        if(grid.getXGridCell(getX()) == path.get(pathDistance).x &&
                grid.getYGridCell(getY()) == path.get(pathDistance).y)
        {
            pathDistance++;
            if(pathDistance == path.size()){
                pathDistance = 0;
            }
        }
        if(grid.getXGridCell(getX()) < path.get(pathDistance).x) {
            movingVectorX = 1;
        }
        else if(grid.getXGridCell(getX()) > path.get(pathDistance).x) {
            movingVectorX = -1;
        }
        else {
            movingVectorX = 0;
        }

        if(grid.getYGridCell(getY()) < path.get(pathDistance).y) {
            movingVectorY = 1;
        }
        else if(grid.getYGridCell(getY()) > path.get(pathDistance).y) {
            movingVectorY = -1;
        }
        else {
            movingVectorY = 0;
        }

        double movingVectorLength = Math.sqrt(movingVectorX* movingVectorX + movingVectorY*movingVectorY);
        sceneObject.move(
                (int)(distance* movingVectorX / movingVectorLength),
                (int)(distance* movingVectorY / movingVectorLength),
                this.gameSurface.getWidth(),
                this.gameSurface.getHeight());
    }

    //public void setMovingVector(int movingVectorX, int movingVectorY)  {
        //this.movingVectorX= movingVectorX;
        //this.movingVectorY = movingVectorY;
    //}

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