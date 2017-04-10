package city.stage.com.cobagame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by indomegabyte on 14/02/17.
 */
public class Player2 {

    //Bitmap to get character from image
    private Bitmap bitmap, bitmap2;

    private boolean lompat, isFlying;

    //Gravity Value to add gravity effect on the ship
    private final int GRAVITY = -10;


    int maxX = 1500;
    int minX = 50;
    int minY ;
    int maxY;

    //Limit the bounds of the ship's speed
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    //coordinates
    private int x;
    private int y;

    private int[] images = {
      R.drawable.dinos,
      R.drawable.dinoss,
    };

    //motion speed of the character
    private int speed = 0;

    private Rect detectCollison;
    int gambar;
    int counterFly;
    Context context;



    //constructor
    public Player2 (Context context,int screenX, int screenY) {
        this.context = context;
        x = 75;
        y = 200;
        speed = 1;
        //Getting bitmap from drawable resource

        Bitmap tempBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.dinos);
//        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dinos);
        bitmap = Bitmap.createScaledBitmap(tempBitmap,tempBitmap.getWidth(),tempBitmap.getHeight(),false);


        //calculating maxY
        maxY = screenY-bitmap.getHeight();
        //top edge's y point is 0 so min y will always be zero
        minY = 0;
        lompat = false;

//        isFlying = true;
//        counterFly =0;


        detectCollison = new Rect(x,y,bitmap.getWidth(),bitmap.getHeight());


    }

    public void setLompat(){
        lompat = true;
    }

    public void stopLompat(){
        lompat = false;
    }

    //Method to update coordinate of character
    public void update(){
        //updating x coordinate
//        if(isFlying){
//            counterFly++;
//            if (counterFly % images.length == 0){
//                Bitmap tempBitmap = BitmapFactory.decodeResource(context.getResources(), images[counterFly % images.length]);
//                bitmap = Bitmap.createScaledBitmap(tempBitmap, tempBitmap.getWidth(), tempBitmap.getHeight(), false);
//            }
//        }

        if (lompat){
           speed = speed+5;
        }
        else {
            speed = speed-5;
        }
        //controlling the top speed
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        //if the speed is less than min speed
        //controlling it so that it won't stop completely
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }

        //moving the ship down
        y -= speed + GRAVITY;

        //but controlling it also so that it won't go off the screen
        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }

        detectCollison.left=x;
        detectCollison.top=y;
        detectCollison.right=x+bitmap.getWidth();
        detectCollison.bottom=y+bitmap.getHeight();
    }

    public Rect getDetectCollison() {
        return detectCollison;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }




}
