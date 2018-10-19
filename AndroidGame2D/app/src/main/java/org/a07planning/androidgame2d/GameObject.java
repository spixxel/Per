    package org.a07planning.androidgame2d;


    import android.graphics.Bitmap;
    import android.graphics.Canvas;

    public abstract class GameObject {

        public static int nextId = 1;

        protected Sprite sprite;

        protected final int rowCount;
        protected final int colCount;
        public int id;

        protected int x;

        protected int y;

        public GameObject(Sprite sprite, int rowCount, int colCount, int x, int y) {
            id = nextId;
            nextId++;
            this.sprite = sprite;
            this.rowCount= rowCount;
            this.colCount= colCount;

            this.x= x;
            this.y= y;
        }

        public int getX() {
            return this.x;
        }
        public int getY() {
            return this.y;
        }

        public abstract void draw(Canvas canvas);
    }