package city.stage.com.cobagame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by indomegabyte on 28/02/17.
 */
public class Hearts {
    private Bitmap bitmap;


    private int x;
    private int y;
    private int maxX;
    private int minX;
    private int speed = 1;
    private int maxY;
    private int minY;

    public Hearts(Context context,int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.lives);

        maxX = screenX;
        x = screenX - 180;

        y = 20;

    }

    public void update(int playerSpeed){
        x = maxX-180;
        y = 20;
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
