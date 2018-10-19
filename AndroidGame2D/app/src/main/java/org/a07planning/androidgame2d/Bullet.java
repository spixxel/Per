package org.a07planning.androidgame2d;

public class Bullet {
    BulletSceneObject bulletSceneObject;
    int targetId;
    public Bullet(BulletSceneObject bulletSceneObject, int targetId)
    {
        this.targetId = targetId;
        this.bulletSceneObject = bulletSceneObject;
    }

    public boolean update(int newTargetX, int newTargetY)
    {
        return bulletSceneObject.update(newTargetX, newTargetY);
    }

}
