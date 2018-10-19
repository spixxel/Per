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
    public final List<Bullet> bulletList = new ArrayList<>();

    public Tower buildTowerAccept;
    public Tower buildTowerDecline;

    public final ExplosionFactory explosionFactory;

    public final Grid grid;

    public Game(ExplosionFactory explosionFactory, Resources res, int explosionResourceId, GameSurface gameSurface, Scenegraph graph)
    {
        this.explosionResourceId = explosionResourceId;
        this.res = res;
        this.explosionFactory = explosionFactory;
        grid = new Grid(gameSurface.getWidth()/Grid.cellWidth, gameSurface.getHeight()/Grid.cellHeight);
        Sprite towerAcceptBitmap = new Sprite(BitmapFactory.decodeResource(res,R.drawable.buildtoweraccept), 4, 3);
        Sprite towerDeclineBitmap = new Sprite(BitmapFactory.decodeResource(res,R.drawable.buildtowerdecline), 4, 3);

        TowerSceneObject towerSceneObjectAccept = new TowerSceneObject(towerAcceptBitmap,grid.getXSnapGrid(0),grid.getYSnapGrid(0));
        graph.root.addGameSceneObject(towerSceneObjectAccept);
        buildTowerAccept = new Tower(gameSurface,towerSceneObjectAccept);
        buildTowerAccept.sceneObject.hidden = true;

        TowerSceneObject towerSceneObjectDecline = new TowerSceneObject(towerDeclineBitmap,grid.getXSnapGrid(0),grid.getYSnapGrid(0));
        graph.root.addGameSceneObject(towerSceneObjectDecline);
        buildTowerDecline = new Tower(gameSurface,towerSceneObjectDecline);
        buildTowerDecline.sceneObject.hidden = true;
    }


    public void gameLoop(int deltaTime, Scenegraph graph, GameSurface gameSurface)
    {
        moveMobs(deltaTime);
        animateExplosions(graph);
        towersShoot(graph);
        moveBullets(graph, gameSurface);
        animateTowers();
    }

    public void moveBuildtower(int x, int y) {
        buildTowerAccept.sceneObject.x = grid.getXSnapGrid(x);
        buildTowerAccept.sceneObject.y = grid.getYSnapGrid(y);
        buildTowerDecline.sceneObject.x = grid.getXSnapGrid(x);
        buildTowerDecline.sceneObject.y = grid.getYSnapGrid(y);
    }

    void spawnEnemy(int x, int y, GameSurface gameSurface, Scenegraph graph)
    {
        Sprite chibiBitmap1 = new Sprite(BitmapFactory.decodeResource(res,R.drawable.chibi1), 4, 3);
        ChibiCharacterSceneObject chibiSprite1 = new ChibiCharacterSceneObject(chibiBitmap1,x,y);
        graph.root.addGameSceneObject(chibiSprite1);
        ChibiCharacter chibi1 = new ChibiCharacter(gameSurface,chibiSprite1);
        chibiList.add(chibi1);
    }

    void moveMobs(int deltaTime)
    {
        for(ChibiCharacter chibi: chibiList) {
            chibi.move(deltaTime);
        }
    }

    void animateExplosions(Scenegraph graph)
    {
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
    }

    void animateTowers()
    {
        for(Tower tower: towerList) {
            tower.update();
        }
    }

    void moveBullets(Scenegraph graph, GameSurface gameSurface)
    {
        Iterator<Bullet> bulletIterator= bulletList.iterator();
        while(bulletIterator.hasNext()) {
            {
                Bullet bullet = bulletIterator.next();

                Iterator<ChibiCharacter> enemyIterator = chibiList.iterator();
                while (enemyIterator.hasNext()) {
                    ChibiCharacter chibi = enemyIterator.next();
                    if (chibi.sceneObject.id == bullet.targetId) {
                        if (bullet.update(chibi.getX(), chibi.getY())) {
                            bulletIterator.remove();
                            graph.removeObject(bullet.bulletSceneObject.id);
                            if (!chibi.hit()) {
                                // Remove the current element from the iterator and the list.
                                graph.removeObject(chibi.sceneObject.id);
                                enemyIterator.remove();
                                // Create Explosion object.
                                Bitmap bitmap = BitmapFactory.decodeResource(res, explosionResourceId);
                                ExplosionSceneObject explosionSceneObject = new ExplosionSceneObject(new Sprite(bitmap, 5, 5), chibi.getX(), chibi.getY());
                                Explosion explosion = explosionFactory.createExplosion(gameSurface, explosionSceneObject);
                                graph.root.addGameSceneObject(explosionSceneObject);
                                explosion.start();
                                explosionList.add(explosion);
                            }
                        }
                    }
                }
            }
        }
    }

    void towersShoot(Scenegraph graph)
    {
        for(Tower tower: towerList) {
            ChibiCharacter target = tower.findTarget(chibiList);
            if(target != null)
            {
                if(tower.shoot(target)) {
                    Bitmap bitmap = BitmapFactory.decodeResource(res,R.drawable.bullet);
                    BulletSceneObject bulletSceneObject = new BulletSceneObject(
                            new Sprite(bitmap, 1, 1),
                            tower.getX(),
                            tower.getY(),
                            tower.getX(),
                            tower.getY(),
                            target.getX(),
                            target.getY());
                    Bullet bullet = new Bullet(bulletSceneObject, target.sceneObject.id);
                    graph.root.addGameSceneObject(bulletSceneObject);
                    bulletList.add(bullet);
                }
            }
        }
    }

    void spawnTower(int x, int y, GameSurface gameSurface, Scenegraph graph)
    {
        if(grid.empty(grid.getXGridCell(x), grid.getYGridCell(y),Tower.gridWidth, Tower.gridHeight)) {
            Sprite towerBitmap = new Sprite(BitmapFactory.decodeResource(res,R.drawable.tower), 4, 3);
            TowerSceneObject towerSceneObject = new TowerSceneObject(towerBitmap,grid.getXSnapGrid(x),grid.getYSnapGrid(y));
            graph.root.addGameSceneObject(towerSceneObject);
            Tower tower = new Tower(gameSurface,towerSceneObject);
            towerList.add(tower);
            grid.build(grid.getXGridCell(x), grid.getYGridCell(y),Tower.gridWidth, Tower.gridHeight, towerSceneObject.id);
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
