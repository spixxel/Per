package org.a07planning.androidgame2d;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GameFactory {
    Resources res;
    public GameFactory(Resources res)
    {
        this.res = res;
    }
    Game createGame(GameSurface gameSurface, Scenegraph graph)
    {
        Game game = new Game(new ExplosionFactory(res), res, R.drawable.explosion, gameSurface, graph);
        Sprite chibiBitmap1 = new Sprite(BitmapFactory.decodeResource(res,R.drawable.chibi1), 4,3);
        ChibiCharacterSceneObject chibiSprite1 = new ChibiCharacterSceneObject(chibiBitmap1,100,50);
        ChibiCharacterSceneObject chibiSpriteb = new ChibiCharacterSceneObject(chibiBitmap1,200,50);
        ChibiCharacterSceneObject chibiSpritec = new ChibiCharacterSceneObject(chibiBitmap1,300,50);
        ChibiCharacterSceneObject chibiSprited = new ChibiCharacterSceneObject(chibiBitmap1,400,50);
        graph.root.addGameSceneObject(chibiSprite1);
        graph.root.addGameSceneObject(chibiSpriteb);
        graph.root.addGameSceneObject(chibiSpritec);
        graph.root.addGameSceneObject(chibiSprited);
        ChibiCharacter chibi1 = new ChibiCharacter(gameSurface,chibiSprite1);
        ChibiCharacter chibib = new ChibiCharacter(gameSurface,chibiSpriteb);
        ChibiCharacter chibic = new ChibiCharacter(gameSurface,chibiSpritec);
        ChibiCharacter chibid = new ChibiCharacter(gameSurface,chibiSprited);

        Sprite chibiBitmap2 = new Sprite(BitmapFactory.decodeResource(res,R.drawable.chibi2),4,3);
        ChibiCharacterSceneObject chibiSprite2 = new ChibiCharacterSceneObject(chibiBitmap2,300,150);
        graph.root.addGameSceneObject(chibiSprite2);
        ChibiCharacter chibi2 = new ChibiCharacter(gameSurface,chibiSprite2);

        game.chibiList.add(chibi1);
        game.chibiList.add(chibib);
        game.chibiList.add(chibic);
        game.chibiList.add(chibid);
        game.chibiList.add(chibi2);

        return game;
    }
}
