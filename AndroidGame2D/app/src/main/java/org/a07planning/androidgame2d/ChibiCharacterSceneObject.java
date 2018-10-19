package org.a07planning.androidgame2d;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class ChibiCharacterSceneObject extends GameObject {
    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 2;
    private static final int ROW_BOTTOM_TO_TOP = 3;

    // Row index of Image are being used.
    private int rowUsing = ROW_LEFT_TO_RIGHT;

    private int colUsing;

    public ChibiCharacterSceneObject(Sprite image, int x, int y) {
        super(image, 4, 3, x, y);
    }

    int animateCounter = 0;

    public void animate() {
        animateCounter++;
        if(animateCounter > 10)
        {
            animateCounter = 0;
            colUsing++;
            if(colUsing >= colCount)  {
                colUsing =0;
            }
        }
    }

    public int move(int moveX, int moveY, int screenWidth, int screenHeight) {
        // Calculate the new position of the game character.
        x += moveX;
        y += moveY;

        // rowUsing
        if( moveX > 0 )  {
            if(moveY > 0 && Math.abs(moveX) < Math.abs(moveY)) {
                rowUsing = ROW_TOP_TO_BOTTOM;
            }else if(moveY < 0 && Math.abs(moveX) < Math.abs(moveY)) {
                rowUsing = ROW_BOTTOM_TO_TOP;
            }else  {
                rowUsing = ROW_LEFT_TO_RIGHT;
            }
        } else {
            if(moveY > 0 && Math.abs(moveX) < Math.abs(moveY)) {
                rowUsing = ROW_TOP_TO_BOTTOM;
            }else if(moveY < 0 && Math.abs(moveX) < Math.abs(moveY)) {
                rowUsing = ROW_BOTTOM_TO_TOP;
            }else  {
                rowUsing = ROW_RIGHT_TO_LEFT;
            }
        }

        // When the game's character touches the edge of the screen, then change direction
        if(x < 0 )  {
            x = 0;
            return 1;
        } else if(x > screenWidth -sprite.width)  {
            x= screenWidth-sprite.width;
            return 1;
        }
        if(y < 0 )  {
            y = 0;
            return 2;
        } else if(y > screenHeight- sprite.height)  {
            y= screenHeight- sprite.height;
            return 2;
        }
        return 0;


    }


    public boolean isInside(int x, int y)
    {
        return (getX() < x && x < getX() + sprite.width
                && getY() < y && y < getY()+ sprite.height);
    }

    public void draw(Canvas canvas)  {
        canvas.drawBitmap(sprite.getFrame(this.rowUsing,this.colUsing),x, y, null);
    }
}
