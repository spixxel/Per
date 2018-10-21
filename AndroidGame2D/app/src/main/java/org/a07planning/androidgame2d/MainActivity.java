package org.a07planning.androidgame2d;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    GameSurface gameSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inScaled = false;


        FrameLayout game = new FrameLayout(this);
        gameSurface = new GameSurface(this);
        LinearLayout gameWidgets = new LinearLayout (this);

        Button endGameButton = new Button(this);
        endGameButton.setHeight(320);
        endGameButton.setWidth(200);
        endGameButton.setY(1425);
        endGameButton.setX(-85);
        endGameButton.setBackground(new BitmapDrawable(this.getResources(),BitmapFactory.decodeResource(getResources(),R.drawable.bullet,o)));
        Button endGameButton2 = new Button(this);
        endGameButton2.setHeight(350);
        endGameButton2.setWidth(200);
        endGameButton2.setText("L");
        endGameButton2.setY(1425);
        endGameButton2.setX(-90);
        Button endGameButton3 = new Button(this);
        endGameButton3.setHeight(350);
        endGameButton3.setWidth(200);
        endGameButton3.setText("L");
        endGameButton3.setY(1425);
        endGameButton3.setX(-80);
        Button endGameButton4 = new Button(this);
        endGameButton4.setHeight(350);
        endGameButton4.setWidth(200);
        endGameButton4.setText("L");
        endGameButton4.setY(1425);
        endGameButton4.setX(-80);
        Button endGameButton5 = new Button(this);
        endGameButton5.setHeight(350);
        endGameButton5.setWidth(200);
        endGameButton5.setText("L");
        endGameButton5.setY(1425);
        endGameButton5.setX(-80);
        TextView myText = new TextView(this);
        myText.setText("rIZ..i");

        gameWidgets.addView(myText);
        gameWidgets.addView(endGameButton);
        gameWidgets.addView(endGameButton2);
        gameWidgets.addView(endGameButton3);
        gameWidgets.addView(endGameButton4);
        gameWidgets.addView(endGameButton5);

        game.addView(gameSurface);
        game.addView(gameWidgets);

        setContentView(game);

        endGameButton.setOnTouchListener(new Button.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gameSurface.requestTowerBuild();
                return gameSurface.dispatchTouchEvent(motionEvent);
            }
        });
        endGameButton2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        endGameButton3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        endGameButton4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        endGameButton5.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gameSurface.dispatchTouchEvent(event);
    }
}
