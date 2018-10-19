package org.a07planning.androidgame2d;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Grid {

    public static final int cellWidth = 40;
    public static final int cellHeight = 40;

    int gridWidth;
    int gridHeight;

    int[][] occupied;

    public Grid(int width, int height)
    {
        gridWidth = width;
        gridHeight = height;
        occupied = new int[width][height];
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                occupied[i][j] = -1;
            }
        }
    }

    public int getXGridCell(int screenX) {
        return screenX/cellWidth;
    }

    public int getYGridCell(int screenY) {
        return screenY/cellHeight;
    }

    public int getXSnapGrid(int screenX) {
        return screenX - screenX%cellWidth;
    }

    public int getYSnapGrid(int screenY) {
        return screenY - screenY%cellHeight;
    }

    public boolean empty(int x, int y, int width, int height) {
        if(x+width >= gridWidth ) return false;
        if(y+height >= gridHeight ) return false;
        for(int i = x; i < x+width; i++) {
            for(int j = y; j < y+height; j++) {
                if(occupied[i][j] != -1) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean build(int x, int y, int width, int height, int id) {
        if(x+width >= gridWidth ) return false;
        if(y+height >= gridHeight ) return false;
        if(empty(x,y,width,height))
        {
            for(int i = x; i < x+width; i++) {
                for(int j = y; j < y+height; j++) {
                    occupied[i][j] = id;
                }
            }
            return true;
        }
        return false;
    }

    public void destroy(int id) {
        for(int i = 0; i < gridWidth; i++) {
            for(int j = 0; j < gridHeight; j++) {
                if(occupied[i][j] == id) {
                    occupied[i][j] = -1;
                }
            }
        }
    }

    public void renderGrid(Canvas canvas)
    {

        Paint p = new Paint();
        p.setColor(Color.WHITE);
        for(int i = 0; i < gridWidth; i++) {
            for(int j = 0; j < gridHeight; j++) {
                canvas.drawLine(i*cellWidth,j*cellHeight, i*cellWidth+cellWidth, j*cellHeight, p);
                canvas.drawLine(i*cellWidth,j*cellHeight, i*cellWidth, j*cellHeight+cellHeight, p);
            }
        }
    }

    public void printGrid()
    {
        for(int i = 0; i < gridHeight; i++) {
            for(int j = 0; j < gridWidth; j++) {
                System.out.print(occupied[j][i] + " ");
            }
            System.out.println();
        }
    }
}
