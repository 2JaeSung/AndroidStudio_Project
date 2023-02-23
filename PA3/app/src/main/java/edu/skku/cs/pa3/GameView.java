package edu.skku.cs.pa3;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.LogRecord;

public class GameView extends View {

    Context context;
    int w, h;

    Bitmap player;
    Bitmap imgBack;

    int x, y;
    int rw, rh;
    float x1, y1;

    int sx = 0;
    int sy = 0;


    Random rnd = new Random();
    public boolean isDead = false;
    public String map = "";

    private Paint paint = new Paint();
    private Paint paint1 = new Paint();
    String end = "GAME OVER";
    String end1 = "To save and exit : click EXIT button";
    private ArrayList<Obstacle> mObstacle = new ArrayList<Obstacle>();

    PointF p1 = new PointF();
    PointF p2 = new PointF();
    float player_ang = 0;



    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        mHandler.sendEmptyMessageDelayed(0, 10);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);

        this.w = w;
        this.h = h;

        x = w / 2;
        y = h / 2;

        if(map.equals("0")){
            imgBack = BitmapFactory.decodeResource(context.getResources(), R.drawable.skyback);
        }
        else{
            imgBack = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceback);
        }
        imgBack = Bitmap.createScaledBitmap(imgBack, w, h, true);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(160);
        paint.setColor(Color.RED);

        paint1.setTextAlign(Paint.Align.CENTER);
        paint1.setTextSize(60);
        paint1.setColor(Color.RED);

        if(map.equals("0")){
            player = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane);
            rw = player.getWidth() / 2;
            rh = player.getHeight() / 2;
        }
        else{
            player = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
            rw = player.getWidth() / 2;
            rh = player.getHeight() / 2;
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(imgBack, 0, 0, null);

        if(isDead){
            canvas.drawText(end, w / 2, h / 2 - 250, paint);
            canvas.drawText(end1, w / 2, h / 2, paint1);
        }

        x += sx;
        if(x < rw || x > w - rw){
            sx = -sx;
            x += sx;
        }
        y += sy;
        if(y < rh || y > h - rh){
            sy = -sy;
            y += sy;
        }
        canvas.rotate(player_ang, x, y);
        canvas.drawBitmap(player, x - rw, y - rh, null);
        canvas.rotate(-player_ang, x, y);

        for(Obstacle tmp : mObstacle){
            tmp.x += tmp.sx;
            tmp.y += tmp.sy;
            PointF o1 = new PointF(tmp.x, tmp.y);
            PointF o2 = new PointF(tmp.x + tmp.sx, tmp.y + tmp.sy);
            float ang = cwDegree(o1, o2);
            canvas.rotate(ang, tmp.x, tmp.y);
            canvas.drawBitmap(tmp.obstacle, tmp.x - tmp.ow, tmp.y - tmp.oh, null);
            canvas.rotate(-ang, tmp.x, tmp.y);
        }


    }

    private void makeObstacle(){
        if(map.equals("0")){
            if(mObstacle.size() < 15 && rnd.nextInt(1000) < 20){
                mObstacle.add(new Obstacle(context, w, h, map));
            }
        }
        else{
            if(mObstacle.size() < 20 && rnd.nextInt(1000) < 25){
                mObstacle.add(new Obstacle(context, w, h, map));
            }
        }

    }

    private void removeObstacle(){
        for(int i = mObstacle.size() - 1; i >= 0; i--){
            if(mObstacle.get(i).x < -mObstacle.get(i).ow || mObstacle.get(i).x > w + mObstacle.get(i).ow || mObstacle.get(i).y < -mObstacle.get(i).oh || mObstacle.get(i).y > h + mObstacle.get(i).oh){
                mObstacle.remove(i);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            x1 = event.getX();
            y1 = event.getY();
        }

        if(event.getAction() == MotionEvent.ACTION_UP){
            float x2 = event.getX();
            float y2 = event.getY();

            sx = (int)(x2 - x1) / 100;
            sy = (int)(y2 - y1) / 100;

            p1.x = (int) x1;
            p1.y = (int) y1;
            p2.x = (int) x2;
            p2.y = (int) y2;
        }
        player_ang = cwDegree(p1, p2);

        return true;
    }

    public void isCollision(){
        for(Obstacle tmp : mObstacle){
            if(checkCollision(x, y, 0.7f * rh, tmp.x, tmp.y, 0.7f * tmp.oh)){
                isDead = true;
                break;
            }
        }
    }

    static public boolean checkCollision(float x, float y, float r, float tx, float ty, float tr) {
        return (x - tx) * (x - tx) + (y - ty) * (y - ty) <= (r + tr) * (r + tr);
    }

    static public float cwDegree(PointF p1, PointF p2){
        double rad = -Math.atan2(p2.y - p1.y, p2.x - p1.x);
        return 90 - (float) Math.toDegrees(rad);
    }

    Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            if(!isDead){
                makeObstacle();
                removeObstacle();
                isCollision();
                invalidate();
                mHandler.sendEmptyMessageDelayed(0, 10);
            }
        }
    };


}