package org.a07planning.androidgame2d;

import android.graphics.Canvas;

public class Scenegraph {
    SceneNode root = new SceneNode();

    public void draw(Canvas canvas)
    {
        root.draw(canvas);
    }
    public void removeObject(int id)
    {
        root.removeObject(id);
    }
}
