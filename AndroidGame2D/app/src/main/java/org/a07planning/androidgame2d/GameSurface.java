package org.a07planning.androidgame2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    public Game game;
    private Scenegraph scenegraph = new Scenegraph();
    private GameFactory gameFactory = new GameFactory(getResources());
    private static final int MAX_STREAMS=100;
    private int soundIdExplosion;
    private int soundIdBackground;

    private boolean soundPoolLoaded;
    private SoundPool soundPool;

    private long lastUpdateNanoTime =-1;



    public GameSurface(Context context)  {
        super(context);

        // Make Game Surface focusable so it can handle events.
        this.setFocusable(true);

        // Sét callback.
        this.getHolder().addCallback(this);

        this.initSoundPool();
    }

    private void initSoundPool()  {
        // With Android API >= 21.
        if (Build.VERSION.SDK_INT >= 21 ) {

            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder= new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        // With Android API < 21
        else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        // Load the sound background.mp3 into SoundPool
        this.soundIdBackground= this.soundPool.load(this.getContext(), R.raw.background,1);

        // Load the sound explosion.wav into SoundPool
        this.soundIdExplosion = this.soundPool.load(this.getContext(), R.raw.explosion,1);

        // When SoundPool load complete.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPoolLoaded = true;

                // Playing background sound.
                //playSoundBackground();
            }
        });
    }

    public void playSoundExplosion()  {
        if(this.soundPoolLoaded) {
            float leftVolumn = 0.8f;
            float rightVolumn =  0.8f;
            // Play sound explosion.wav
            int streamId = this.soundPool.play(this.soundIdExplosion,leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    public void playSoundBackground()  {
        if(this.soundPoolLoaded) {
            float leftVolumn = 0.8f;
            float rightVolumn =  0.8f;
            // Play sound background.mp3
            int streamId = this.soundPool.play(this.soundIdBackground,leftVolumn, rightVolumn, 1, -1, 1f);
        }
    }

    boolean building = false;

    public void requestTowerBuild() {
        building=true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if(building) {
                game.spawnTower((int)event.getRawX(), (int)event.getRawY(), this, scenegraph);
                game.buildTowerAccept.sceneObject.hidden = true;
                game.buildTowerDecline.sceneObject.hidden = true;
                building = false;
            }
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if(building) {
                if(game.grid.empty(
                        game.grid.getXGridCell((int)event.getRawX()),
                        game.grid.getYGridCell((int)event.getRawY()),
                        Tower.gridWidth,
                        Tower.gridHeight))
                {
                    game.buildTowerAccept.sceneObject.hidden = false;
                    game.buildTowerDecline.sceneObject.hidden = true;
                }
                else {
                    game.buildTowerAccept.sceneObject.hidden = true;
                    game.buildTowerDecline.sceneObject.hidden = false;
                }
                game.moveBuildtower((int)event.getRawX(), (int)event.getRawY());
            }
            return true;
        }
        return false;
    }

    public void update()  {
        // Current time in nanoseconds
        long now = System.nanoTime();

        // Never once did draw.
        if(lastUpdateNanoTime==-1) {
            lastUpdateNanoTime= now;
        }
        // Change nanoseconds to milliseconds (1 nanosecond = 1000000 milliseconds).
        int deltaTime = (int) ((now - lastUpdateNanoTime)/ 1000000 );
        lastUpdateNanoTime= now;

        game.gameLoop(deltaTime, scenegraph, this);
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        canvas.drawBitmap(game.background,0, 0, null);
        game.grid.renderGrid(canvas);
        scenegraph.draw(canvas);
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.game = gameFactory.createGame(this, scenegraph);
        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }

}