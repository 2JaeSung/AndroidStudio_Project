package edu.skku.cs.pa3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Obstacle {
    private int scrW, scrH;

    public int sx, sy;

    public int x, y;
    public int ow, oh;

    public Bitmap obstacle;

    Random random = new Random();

    public Obstacle(Context context, int width, int height, String map){
        scrW = width;
        scrH = height;

        if(map.equals("0")){
            obstacle = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird);
            ow = obstacle.getWidth() / 2;
            oh = obstacle.getHeight() / 2;

            int corner = random.nextInt(4) + 1;
            if(corner == 1){
                x = width;
                y = 0;

                sx = -(random.nextInt(3) + 2);
                sy = (random.nextInt(3) + 2);
            }
            else if(corner == 2){
                x = 0;
                y = 0;

                sx = (random.nextInt(3) + 2);
                sy = (random.nextInt(3) + 2);
            }
            else if(corner == 3){
                x = 0;
                y = height;

                sx = (random.nextInt(3) + 2);
                sy = -(random.nextInt(3) + 2);
            }
            else{
                x = width;
                y = height;

                sx = -(random.nextInt(3) + 2);
                sy = -(random.nextInt(3) + 2);
            }




        }
        else{
            obstacle = BitmapFactory.decodeResource(context.getResources(), R.drawable.alien);
            ow = obstacle.getWidth() / 2;
            oh = obstacle.getHeight() / 2;

            int corner = random.nextInt(4) + 1;
            if(corner == 1){
                x = width;
                y = 0;

                sx = -(random.nextInt(3) + 3);
                sy = (random.nextInt(3) + 3);
            }
            else if(corner == 2){
                x = 0;
                y = 0;

                sx = (random.nextInt(3) + 3);
                sy = (random.nextInt(3) + 3);
            }
            else if(corner == 3){
                x = 0;
                y = height;

                sx = (random.nextInt(3) + 3);
                sy = -(random.nextInt(3) + 3);
            }
            else{
                x = width;
                y = height;

                sx = -(random.nextInt(3) + 3);
                sy = -(random.nextInt(3) + 3);
            }



        }




    }


}
