package org.a07planning.androidgame2d;

import android.content.res.Resources;

class ExplosionFactory {
    Resources res;
    public ExplosionFactory(Resources res) {
        this.res = res;
    }

    Explosion createExplosion(GameSurface gameSurface, ExplosionSceneObject sprite) {
        // Create Explosion object.
        return new Explosion(gameSurface, sprite);
    }
}
