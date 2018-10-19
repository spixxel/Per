package org.a07planning.androidgame2d;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class ExplosionSceneObject extends GameObject {

    private int rowIndex = 0 ;
    private int colIndex = -1 ;

    private boolean finish= false;

    public ExplosionSceneObject(Sprite sprite, int x, int y) {
        super(sprite, 5, 5, x, y);
    }

    public void update()  {
        this.colIndex++;

        if(this.colIndex >= this.colCount)  {
            this.colIndex =0;
            this.rowIndex++;

            if(this.rowIndex>= this.rowCount)  {
                this.finish= true;
            }
        }
    }

    public boolean isFinish() {
        return finish;
    }

    @Override
    public void draw(Canvas canvas)  {
        canvas.drawBitmap(sprite.getFrame(rowIndex, colIndex), x, y,null);
    }
}