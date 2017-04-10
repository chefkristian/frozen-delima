package city.stage.com.cobagame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.android.gms.ads.AdView;

/**
 * Created by indomegabyte on 14/02/17.
 */
public class GameActivity extends AppCompatActivity {


    //declaring gameview
    private GameView gameView;
//    MediaPlayer gameOnSound;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Getting display object
        Display display = getWindowManager().getDefaultDisplay();

        //Getting the screen resolution into point object
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //Initializing game view object
        //this time we are also passing the screen size to the GameView constructor
        gameView = new GameView(this, size.x, size.y);
//
//        gameOnSound = MediaPlayer.create(this,R.raw.gameon);
//        gameOnSound.setLooping(true);
//        gameOnSound.start();

        //adding it to contentview

        setContentView(gameView);

    }

    //pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    public void onBackPressed() {
        gameView.pause();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        GameView.stopMusic();
//                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
//                        gameView.resumeMusic();
                        gameView.resume();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    protected void onDestroy() {
//        gameOnSound.stop();
        super.onDestroy();
    }

//    private void showBanner() {
//        adView.setVisibility(View.VISIBLE);
//        adView.loadAd(new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
//
//    }
}
