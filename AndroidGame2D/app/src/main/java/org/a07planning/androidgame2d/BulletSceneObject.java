package org.a07planning.androidgame2d;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BulletSceneObject extends GameObject {
    int xStart;
    int yStart;
    int xTarget;
    int yTarget;

    int traveled = 0;


    public BulletSceneObject(Sprite image, int x, int y, int xStart, int yStart, int xTarget, int yTarget) {
        super(image, 1, 1, x, y);
        this.xStart = xStart;
        this.yStart = yStart;
        this.xTarget = xTarget;
        this.yTarget = yTarget;
    }

    int animateCounter = 0;

    public boolean update(int newTargetX, int newTargetY)
    {
        traveled++;
        x = (newTargetX-xStart)*traveled/10+xStart;
        y = (newTargetY-yStart)*traveled/10+yStart;
        return traveled > 10;
    }

    public void animate() {
    }

    public void draw(Canvas canvas)  {
        canvas.drawBitmap(sprite.getFrame(0,0),x, y, null);
    }
}
