package com.example.myflappybird;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    private int canvasWidth;
    private int canvasHeight;

    private int liveScore = 0;
    private boolean gameover = false;

    private Bitmap bird;
    private int birdX;
    private int birdY;
    private int birdSpeed;

    private int pointX;
    private int pointY;
    private int[] pointSpeed = new int[]{20,25,30,35,40};
    private int speed;
    private int shape;

    private Paint point = new Paint();

    private Bitmap backgroundImage;
    private Bitmap astroid[] = new Bitmap[3];
    private Paint score = new Paint();
    private Paint finalscore = new Paint();
    private Paint over = new Paint();

    private Rect rect;
    int dwidth,dheight;

    public GameView(Context context) {
        super(context);
        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dwidth=size.x;
        dheight=size.y;
        rect = new Rect(0,0,dwidth,dheight);

        bird = BitmapFactory.decodeResource(getResources(), R.drawable.birdpic1);
        birdY = 500;
        birdX = 0;
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        astroid[0] = BitmapFactory.decodeResource(getResources(), R.drawable.meteor1);
        astroid[1] = BitmapFactory.decodeResource(getResources(), R.drawable.meteor2);
        astroid[2] = BitmapFactory.decodeResource(getResources(), R.drawable.meteor3);

        point.setColor(Color.WHITE);
        point.setAntiAlias(false);

        score.setColor(Color.WHITE);
        score.setTextSize(42);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        score.setAntiAlias(true);

        over.setColor(Color.WHITE);
        over.setTextSize(360);
        over.setTypeface(Typeface.DEFAULT_BOLD);
        over.setAntiAlias(true);

        finalscore.setColor(Color.WHITE);
        finalscore.setTextSize(180);
        finalscore.setTypeface(Typeface.DEFAULT_BOLD);
        finalscore.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
            canvasHeight = canvas.getHeight();
            canvasWidth = canvas.getWidth();
            canvas.drawBitmap(backgroundImage, null, rect, null);
            int minbirdY = 50;
            int maxbirdY = canvasHeight - minbirdY;
            birdY += birdSpeed;

            if(!gameover){
                canvas.drawBitmap(bird, birdX, birdY, null);
                if (birdY < minbirdY) birdY = minbirdY;
                if (birdY > maxbirdY) birdY = maxbirdY;
                birdSpeed += 2;
                canvas.drawBitmap(astroid[shape],pointX, pointY, null);
                canvas.drawText("Score: " + liveScore, 20, 60, score);
                pointX -= pointSpeed[speed];

                if (pointX+astroid[shape].getWidth() < 0) {
                    shape = (int)(Math.random() * 3);
                    speed = (int)(Math.random() * 5);
                    pointX = canvasWidth + 20;
                    pointY = birdY;
                    liveScore++;
                    //pointY = (int) Math.floor(Math.random() * (maxbirdY - minbirdY)) + minbirdY;
                    if(pointY+astroid[shape].getHeight()>maxbirdY)
                        pointY -= astroid[shape].getHeight();
                }
                if (collisioncheck(pointX, pointY)) {
                    gameover = true;
                }
            }

        if(gameover) {
            canvas.drawText("GAME", 0, canvasHeight / 3, over);
            canvas.drawText(" OVER ", 0, 2 * canvasHeight / 3, over);
            canvas.drawText("     Score:" + liveScore, 0, 7 * canvasHeight / 9, finalscore);
            //restart();
        }
    }

    public boolean collisioncheck(int x, int y){
        if(x < (birdX+2*bird.getWidth()/3) && x + astroid[shape].getWidth()> birdX+bird.getWidth()/3)
            if((birdY+bird.getHeight() < y+astroid[shape].getHeight() && birdY+bird.getHeight()/2 > y)
                || birdY+bird.getHeight()/3 < y+astroid[shape].getHeight() && birdY+bird.getHeight()/3 > y)
                return  true;
        if(birdY>=canvasHeight-50)
            return true;
        else
            return false;
    }
    public void restart(){
        liveScore = 0;
        gameover = false;
        birdY = 500;
        birdSpeed = 0;
        pointX = canvasWidth + 20;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if (gameover)
                restart();
            else
                birdSpeed = -20;
        }
        return true;
    };
}
