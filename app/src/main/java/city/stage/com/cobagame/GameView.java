package city.stage.com.cobagame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by indomegabyte on 14/02/17.
 */
public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;
    private Thread gameThread = null;
    //adding the player to this class
    private Player2 player2;
    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Enemy [] enemies;
    private int enemyCount = 2;
    private Cloud cloud;
    private Meteor [] meteor;
    private  int meteorcount = 2;
    private boolean isGameOver;
    int countKenaMeteor;
    int score;
    int lives;
    int screenX;
    //the high Scores Holder
    Integer highScore[] = new Integer [6];
    //Shared Prefernces to store the High Scores
    SharedPreferences sharedPreferences;
    static MediaPlayer gameOn;
    MediaPlayer eatCherry;
    MediaPlayer gameOver;
    RelativeLayout mRelativeLayout;
    PopupWindow pw;
    Context context;
    private Heart [] heart;
    private int countHeart = 2;
    ArrayList hiScore , hiScore2;






    public GameView(Context context,int screenX, int screenY) {
        super(context);
//        mRelativeLayout = (RelativeLayout)findViewById(R.id.rl_pop_up);
        this.screenX= screenX;
        //initializing player object
        player2 = new Player2(context,screenX,screenY);
        cloud = new Cloud(context,screenX,screenY);
        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        enemies = new Enemy[enemyCount];
        for(int i=0; i<enemyCount; i++){
            enemies[i] = new Enemy(context, screenX, screenY);
        }

        meteor = new Meteor[meteorcount];
        for (int i=0 ; i<meteorcount; i++){
            meteor[i] = new Meteor(context,screenX,screenY);
        }
        heart = new Heart [countHeart];
        for (int i =0 ; i< countHeart; i++){
            heart[i] = new Heart(context,screenX,screenY);
        }
//        hearts =  new Hearts(context, screenX, screenY);


        isGameOver = false;
        countKenaMeteor= 0;
        score = 1000;


            hiScore = new ArrayList();
            hiScore2 = new ArrayList();

//
//        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME", Context.MODE_PRIVATE);
//        hiScore.set(0, sharedPreferences.getInt("score1", 0));
//        hiScore.set(1, sharedPreferences.getInt("score2", 0));
//        hiScore.set(2, sharedPreferences.getInt("score3", 0));
//        hiScore.set(3, sharedPreferences.getInt("score4", 0));
//        hiScore.set(4, sharedPreferences.getInt("score5", 0));

        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME", Context.MODE_PRIVATE);
        //initializing the array high scores with the previous values
        highScore[0] = sharedPreferences.getInt("score1", 0);
        highScore[1] = sharedPreferences.getInt("score2", 0);
        highScore[2] = sharedPreferences.getInt("score3", 0);
        highScore[3] = sharedPreferences.getInt("score4", 0);
        highScore[4] = sharedPreferences.getInt("score5",0);
        highScore[5] = 0;

        lives = 3;

        if (gameOn==null)
        gameOn = MediaPlayer.create(context, R.raw.gameon);
        gameOn.setLooping(true);
        gameOn.start();


        eatCherry = MediaPlayer.create(context,R.raw.jump);

        //initializing context
        this.context = context;


    }

    @Override
    public void run() {
        while (playing) {
            //to update the frame 
            update();

            //to draw the frame 
            draw();

            //to control 
            control();
    }
}



    private void update() {

        score--;
        player2.update();

        for(int i = 0; i < 2; i++){
            heart[i].update(lives);
            if (lives == 3 ){
                heart[0].setX(screenX-250);
                heart[1].setX(screenX -180);

            }
            if (lives == 2 ){
                heart[0].setX(screenX-250);
                heart[1].setX(-400);

            }
            if(lives == 1 ){
                heart[0].setX(-400);
                heart[1].setX(-400);

            }
            if (lives == 0){
                heart[0].setX(-400);
                heart[1].setX(-400);

            }

        }

        //updating the enemy coordinate with respect to player speed
        for(int i=0; i<enemyCount; i++){
            enemies[i].update(player2.getSpeed());
            if (Rect.intersects(player2.getDetectCollison(),enemies[i].getDetectCollision())){
                enemies[i].setX(-300);
                score = score+100;
                eatCherry.start();
            }
        }
        cloud.update(player2.getSpeed());

        for(int i=0; i<meteorcount; i++) {
            meteor[i].update(player2.getSpeed());
            if (Rect.intersects(player2.getDetectCollison(), meteor[i].getDetectCollision())) {
                meteor[i].setX(-400);
                countKenaMeteor++;
                lives = lives - 1;
            }
        }

        if (countKenaMeteor >= 3 || score == 0) {
            isGameOver = true;
            playing = false;


            if(gameOn!=null)
            {
                if(gameOn.isPlaying()){
                    gameOn.reset();//It requires again setDataSource for player object.
                    gameOn.stop();// Stop it
                    gameOn.release();// Release it
                    gameOn=null; // Initilize to null so it can be used later
                }
            }

            gameOver = MediaPlayer.create(context,R.raw.gameover);
            gameOver.start();


            highScore[5] = score;

            Arrays.sort( highScore, Collections.reverseOrder());




//            int highScore2[] = new int[10];
//            int position = 0;
//            //Assigning the scores to the highscore integer array
//            for (int j = 0; j < 5; j++) {
//                if (score > highScore[j]) {
//                    highScore2[position] = score;
//                    if(j<5)
//                    highScore2[position+1] = highScore[j];
//                    //final int finalJ = j;
//                    //highScore[j] = score;
//                    //break;
//                    position = position+2;
//                }else{
//                    highScore2[position] = highScore[j];
//                    position++;
//                }
//            }
//            highScore = highScore2;

//            for (int i = 0 ; i < 5 ; i ++) {
//
//                if (score > hiScore.indexOf(i)){
//                    hiScore.add(score);
//
//                } else{
//
//                }
//
//            }








//            //Assigning the scores to the highscore integer array
//            for(int i=0; i<=4; i++){
//
//                if(highScore[i] < score){
//
////                    final int finalI = i;
//                    highScore[i] = score;
//                    break;
//                }
//            }

            //storing the scores through shared Preferences
            SharedPreferences.Editor e = sharedPreferences.edit();
            for (int j = 0; j < 5; j++) {
                int k = j + 1;
                e.putInt("score" + k, highScore[j]);

            }
            e.apply();
        }

    }

    private void draw() {
        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing a background color for canvas
            canvas.drawColor(Color.parseColor("#8ab2f2"));



            canvas.drawBitmap(
                    cloud.getBitmap(),
                    cloud.getX(),
                    cloud.getY(),
                    paint
            );

            //Drawing the player
            canvas.drawBitmap(
                    player2.getBitmap(),
                    player2.getX(),
                    player2.getY(),
                    paint);


            for (int i =0; i<meteorcount; i++){
                canvas.drawBitmap(
                        meteor[i].getBitmap(),
                        meteor[i].getX(),
                        meteor[i].getY(),
                        paint);
            }

            for (int i = 0; i < enemyCount; i++) {
                canvas.drawBitmap(
                        enemies[i].getBitmap(),
                        enemies[i].getX(),
                        enemies[i].getY(),
                        paint
                );
            }

                paint.setTextSize(100);
                canvas.drawText(String.valueOf(score),100,100,paint);

//            paint.setTextSize(40);
//            canvas.drawText("Live: " + lives, screenX - 300, 50, paint);
            for (int i = 0 ;  i <countHeart; i++){
                    canvas.drawBitmap(
                            heart[i].getBitmap(),
                            heart[i].getX(),
                            heart[i].getY(),
                            paint );
                }
//            canvas.drawBitmap(
//                    hearts.getBitmap(),
//                    hearts.getX(),
//                    hearts.getY(),
//                    paint
//            );


            if(isGameOver){
                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Game Over",canvas.getWidth()/2,yPos,paint);
                canvas.drawText("Your Score :"+score,canvas.getWidth()/2,yPos-80,paint ) ;

//                canvas.drawText("Tap Any Where", getWidth()/2,yPos-160,paint);

                paint.setTextSize(50);
                paint.setTextAlign(Paint.Align.CENTER);
                int y = (int) ((canvas.getHeight()/2));
                canvas.drawText("Tap to Start Again", canvas.getWidth()/2, y+100, paint);

            }

            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control(){

        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //when the game is paused
        //setting the variable to false
        playing = false;
//        gameOn.pause();
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameOn.start();
        gameThread = new Thread(this);
        gameThread.start();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:

                //When the user presses on the screen
                //we will do something here
                player2.stopLompat();
                break;
            case MotionEvent.ACTION_DOWN:
                //When the user releases the screen
                //do something here

                player2.setLompat();
                break;
        }
        if(isGameOver){
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                context.startActivity(new Intent(context,MainActivity.class));
                ((Activity) context).finish();
//                context.startActivity(intent);
            }
        }

        return true;
    }

     //stop the music on exit
    public static void stopMusic(){

        if(gameOn!=null)
        {
            if(gameOn.isPlaying()){
                gameOn.reset();//It requires again setDataSource for player object.
                gameOn.stop();// Stop it
                gameOn.release();// Release it
                gameOn=null; // Initilize to null so it can be used later
            }
        }

    }

    public static void resumeMusic(){

        if(gameOn!=null) {
            gameOn.start();
            }
        if (gameOn == null) {
            gameOn= null;
        }
        }

    }







