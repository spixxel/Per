package org.a07planning.androidgame2d;

import android.graphics.Canvas;

public class TowerSceneObject extends GameObject {

    private int rowIndex = 0 ;
    private int colIndex = 0 ;

    public TowerSceneObject(Sprite sprite, int x, int y) {
        super(sprite, 4, 3, x, y);
    }

    public boolean animate()  {
        this.colIndex++;

        if(this.colIndex >= this.colCount)  {
            this.colIndex =0;
            this.rowIndex++;

            if(this.rowIndex>= this.rowCount)  {
                this.rowIndex = 0;
                return false;
            }
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas)  {
        canvas.drawBitmap(sprite.getFrame(rowIndex, colIndex), x, y,null);
    }

    public boolean isInside(int x, int y)
    {
        return (getX() < x && x < getX() + sprite.width
                && getY() < y && y < getY()+ sprite.height);
    }
}