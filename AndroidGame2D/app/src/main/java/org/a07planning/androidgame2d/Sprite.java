package org.a07planning.androidgame2d;

import android.graphics.Bitmap;

public class Sprite {
    public Bitmap image;

    private Bitmap[][] frames;

    public final int WIDTH;
    public final int HEIGHT;
    public final int width;
    public final int height;

    public Sprite(Bitmap image, int rowCount, int colCount)
    {
        this.image = image;
        frames = new Bitmap[colCount][rowCount];
        this.WIDTH = image.getWidth();
        this.HEIGHT = image.getHeight();
        this.width = this.WIDTH/ colCount;
        this.height= this.HEIGHT/ rowCount;
        for(int col = 0; col< colCount; col++ ) {
            for(int row = 0; row< rowCount; row++ ) {
                this.frames[col][row] = createSubImageAt(row, col);
            }
        }
    }

    private Bitmap createSubImageAt(int row, int col) {
        // createBitmap(bitmap, x, y, width, height).
        Bitmap subImage = Bitmap.createBitmap(image, col* width, row* height, width, height);
        return subImage;
    }

    public Bitmap getFrame(int row, int col) {
        return frames[col][row];
    }
}
