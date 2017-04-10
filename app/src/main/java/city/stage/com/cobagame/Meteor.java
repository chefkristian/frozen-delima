package city.stage.com.cobagame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by indomegabyte on 20/02/17.
 */
public class Meteor {
    Bitmap bitmap;
    //x and y coordinates
    private int x;
    private int y;

    //enemy speed
    private int speed = 1;

    //min and max coordinates to keep the enemy inside the screen
    private int maxX;
    private int minX;

    private int maxY;
    private int minY;

    private Rect detectCollision;

    public Meteor(Context context,int screenX, int screenY) {

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.meteor);
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = bitmap.getHeight();

        Random generator = new Random();
        speed = generator.nextInt(5)+10;
        x = screenX;
        y = generator.nextInt(maxY-bitmap.getHeight());

        detectCollision = new Rect(x,y,bitmap.getWidth(),bitmap.getHeight());
    }

    public void update(int playerSpeed) {
        //decreasing x coordinate so that enemy will move right to left
        x -= playerSpeed;
        x -= speed;
        //if the enemy reaches the left edge
        if (x < minX - bitmap.getWidth()) {
            //adding the enemy again to the right edge
            Random generator = new Random();
            speed = generator.nextInt(5)+10;
            x = maxX;
            y = generator.nextInt(maxY-bitmap.getHeight());;
        }
        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x+50;
        detectCollision.top = y+50;
        detectCollision.right = x + bitmap.getWidth()-50;
        detectCollision.bottom = y + bitmap.getHeight()-50;
    }
    public void setX(int x) {
        this.x = x;
    }

    public Rect getDetectCollision() {
        return detectCollision;
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
