package city.stage.com.cobagame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by indomegabyte on 20/02/17.
 */
public class Cloud {


    private Bitmap bitmap;

    //x and y coordinates
    private int x;
    private int y;

    private int speed = 1;

    //min and max coordinates to keep the enemy inside the screen
    private int maxX;
    private int minX;

    private int maxY;
    private int minY;

    public Cloud(Context context,int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud);
        maxX = screenX;
        maxY = screenY * 3 / 4;
        minX = 0;
        minY = bitmap.getHeight();

        Random generator = new Random();
        speed = 4;
        x = screenX;
        y = generator.nextInt(maxY - bitmap.getHeight());
    }

    public void update(int playerSpeed) {
        //decreasing x coordinate so that enemy will move right to left
        x -= playerSpeed;
        x -= speed;
        //if the enemy reaches the left edge
        if (x < minX - bitmap.getWidth()) {
            //adding the enemy again to the right edge
            Random generator = new Random();
            speed =4;
            x = maxX;
            y = generator.nextInt(maxY-bitmap.getHeight());;
        }

    }

    public void setX(int x) {
        this.x = x;
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

