package org.a07planning.androidgame2d;

public class Explosion {

    public ExplosionSceneObject sprite;
    private GameSurface gameSurface;

    public Explosion(GameSurface gameSurface, ExplosionSceneObject sprite) {
        this.sprite = sprite;
        this.gameSurface= gameSurface;
    }

    public void start()
    {
        //this.gameSurface.playSoundExplosion();
    }

    public boolean update()  {
        sprite.update();
        return sprite.isFinish();
    }
}