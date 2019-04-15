package com.example.user.flyingfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View {

    //The Bitmap class represents a bitmap image
    //By using Bitmap we are allowed to manipulate images by adding different kind of effects on image
    //
    Bitmap fish[]=new Bitmap[2];

    int fishX=10;
    int fishy;
    int fishSpeed;
    int canvasWidth,canvasHeight;

    int score,lifeCounterOfFish;

    int yellowX,yellowY,yellowSpeed=16;
    Paint yellowPaint=new Paint();

    int greenX,greenY,greenSpeed=20;
    Paint greenPaint=new Paint();

    int redX,redY,redSpeed=20;
    Paint redPaint=new Paint();

    boolean touch=false;

    Bitmap gameBackground;
    Paint scorPaint=new Paint();
    Bitmap life[]=new Bitmap[2];

    public FlyingFishView(Context context) {
        super(context);

        //Creating a bitmap Factory
        fish[0]=BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1]=BitmapFactory.decodeResource(getResources(),R.drawable.fish2);
        gameBackground=BitmapFactory.decodeResource(getResources(),R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(true);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(true);

        scorPaint.setColor(Color.WHITE);
        scorPaint.setTextSize(70);
        scorPaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorPaint.setAntiAlias(true);

        life[0]= BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]= BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishy= 550;
        score=0;
        lifeCounterOfFish=3;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        canvasWidth=canvas.getWidth();
        canvasHeight=canvas.getHeight();

        canvas.drawBitmap(gameBackground,0,0,null);

        //TODO:For flying the fish
        int minFishY=fish[0].getHeight();
        int maxFishY=canvasHeight-fish[0].getHeight()*3;
        fishy=fishy+fishSpeed;

        if (fishy<minFishY){
            fishy=minFishY;
        }
        if (fishy>maxFishY){
            fishy=maxFishY;
        }

        fishSpeed=fishSpeed+2;

        //TODO: Tap on screen for flying fish
        if (touch){
            canvas.drawBitmap(fish[1],fishX,fishy,null);
            touch=false;
        }else {
            canvas.drawBitmap(fish[0],fishX,fishy,null);
        }


        yellowX=yellowX-yellowSpeed;

        if (hitBallCheck(yellowX,yellowY)){

            //Whenever the fish hit the yellow ball it will increase by 10 score.
            score=score+10;
            yellowX=-100;
        }

        if (yellowX<0){
            yellowX=canvasWidth+2;

            //The yellow ball will appeared randomly
            yellowY=(int)Math.floor(Math.random() * (maxFishY-minFishY)+minFishY);
        }

        //Create the yellow ball
        canvas.drawCircle(yellowX,yellowY,25,yellowPaint);


        greenX=greenX-greenSpeed;

        if (hitBallCheck(greenX,greenY)){

            //Whenever the fish hit the yellow ball it will increase by 15 score.
            score=score+15;
            greenX=-100;
        }

        if (greenX<0){

            greenX=canvasWidth+2;

            //The yellow ball will appeared randomly
            greenY=(int)Math.floor(Math.random() * (maxFishY-minFishY)+minFishY);
        }

        //Create the yellow ball
        canvas.drawCircle(greenX,greenY,30,greenPaint);

        redX=redX-redSpeed;

        if (hitBallCheck(redX,redY)){

            //Whenever the fish hit the yellow ball it will increase by 10 score.

            redX=-100;

            //One heart will be destroyed
            lifeCounterOfFish--;

            if (lifeCounterOfFish==0){
                Toast.makeText(getContext(),"Game Over",Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(getContext(),GameOverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("score",score);
                getContext().startActivity(intent);

            }


        }

        if (redX<0){

            redX=canvasWidth+2;

            //The yellow ball will appeared randomly
            redY=(int)Math.floor(Math.random() * (maxFishY-minFishY)+minFishY);
        }

        //Create the yellow ball
        canvas.drawCircle(redX,redY,30,redPaint);

        canvas.drawText("Score: "+score,30,60,scorPaint);

        for (int i=0;i<3;i++){
            int x=(int) (580+life[0].getWidth()*1.5*i);
            int y=30;

            if (i<lifeCounterOfFish){

                //When the fish life is available the res heart will appear
                canvas.drawBitmap(life[0],x,y,null);
            }else {

                //When the fish hit the red ball the grey heart will appear
                canvas.drawBitmap(life[1],x,y,null);
            }
        }




    }

    public boolean hitBallCheck(int x,int y){
        if (fishX<x && x<(fishX+fish[0].getWidth()) && fishy<y && y<(fishy+fish[0].getHeight() )){
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){

            touch=true;

            fishSpeed=-22;
        }

        return  true;
    }
}
