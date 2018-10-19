package org.a07planning.androidgame2d;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game {

    Resources res;
    int explosionResourceId;

    public final List<ChibiCharacter> chibiList = new ArrayList<ChibiCharacter>();
    public final List<Explosion> explosionList = new ArrayList<Explosion>();
    public final List<Tower> towerList = new ArrayList<>();

    public final ExplosionFactory explosionFactory;

    public final Grid grid;

    public Game(ExplosionFactory explosionFactory, Resources res, int explosionResourceId, GameSurface gameSurface)
    {
        this.explosionResourceId = explosionResourceId;
        this.res = res;
        this.explosionFactory = explosionFactory;
        grid = new Grid(gameSurface.getWidth()/Grid.cellWidth, gameSurface.getHeight()/Grid.cellHeight);
    }


    public void gameLoop(int deltaTime, Scenegraph graph, GameSurface gameSurface)
    {
        for(ChibiCharacter chibi: chibiList) {
            chibi.move(deltaTime);
        }

        Iterator<Explosion> iterator= explosionList.iterator();
        while(iterator.hasNext())  {
            Explosion explosion = iterator.next();
            explosion.update();
            if(explosion.sprite.isFinish()) {
                // If explosion finish, Remove the current element from the iterator & list.
                graph.removeObject(explosion.sprite.id);
                iterator.remove();
                continue;
            }
        }

        for(Tower tower: towerList) {
            ChibiCharacter target = tower.findTarget(chibiList);
            if(target != null)
            {

                if(tower.shoot() && !target.hit())
                {
                    Iterator<ChibiCharacter> enemyIterator= chibiList.iterator();
                    while(enemyIterator.hasNext()) {
                        ChibiCharacter chibi = enemyIterator.next();
                        if(chibi.sceneObject.id == target.sceneObject.id)
                        {
                            // Remove the current element from the iterator and the list.
                            graph.removeObject(chibi.sceneObject.id);
                            enemyIterator.remove();
                            // Create Explosion object.
                            Bitmap bitmap = BitmapFactory.decodeResource(res,explosionResourceId);
                            ExplosionSceneObject explosionSceneObject = new ExplosionSceneObject(new Sprite(bitmap, 5, 5),chibi.getX(),chibi.getY());
                            Explosion explosion = explosionFactory.createExplosion(gameSurface, explosionSceneObject);
                            graph.root.addGameSceneObject(explosionSceneObject);
                            explosion.start();
                            explosionList.add(explosion);
                        }
                    }
                }
            }
        }

        for(Tower tower: towerList) {
            tower.update();
        }
    }


    void spawnEnemy(int x, int y, GameSurface gameSurface, Scenegraph graph)
    {
        Sprite chibiBitmap1 = new Sprite(BitmapFactory.decodeResource(res,R.drawable.chibi1), 4, 3);
        ChibiCharacterSceneObject chibiSprite1 = new ChibiCharacterSceneObject(chibiBitmap1,x,y);
        graph.root.addGameSceneObject(chibiSprite1);
        ChibiCharacter chibi1 = new ChibiCharacter(gameSurface,chibiSprite1);
        chibiList.add(chibi1);
    }

    void spawnTower(int x, int y, GameSurface gameSurface, Scenegraph graph)
    {
        grid.printGrid();
        if(grid.empty(grid.getXGridCell(x), grid.getYGridCell(y),Tower.gridWidth, Tower.gridHeight)) {
            Sprite chibiBitmap1 = new Sprite(BitmapFactory.decodeResource(res,R.drawable.chibi1), 4, 3);
            TowerSceneObject chibiSprite1 = new TowerSceneObject(chibiBitmap1,grid.getXSnapGrid(x),grid.getYSnapGrid(y));
            graph.root.addGameSceneObject(chibiSprite1);
            Tower chibi1 = new Tower(gameSurface,chibiSprite1);
            towerList.add(chibi1);
            grid.build(grid.getXGridCell(x), grid.getYGridCell(y),Tower.gridWidth, Tower.gridHeight, chibiSprite1.id);
        }

    }

    void changeEnemyDirection(int x, int y)
    {
        for(ChibiCharacter chibi: chibiList) {
            int movingVectorX = x - chibi.getX() ;
            int movingVectorY = y - chibi.getY() ;
            chibi.setMovingVector(movingVectorX, movingVectorY);
        }
    }
}
