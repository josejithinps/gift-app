package com.Catalina.catalinaapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.Catalina.utilities.CircleButton;
import com.Catalina.utilities.MediaAdapter;
import com.Catalina.utilities.Settings;
import com.Catalina.utilities.Utility;

public class GiftDispatch extends Activity {

    private ImageView imageGiftSelected;
    private ImageView imageWrapperSelected;
    private View overlayGift;
    private View overlayWrapper;
    private CircleButton imageButtonGiftEdit;
    private CircleButton imageButtonGiftClose;
    private CircleButton imageButtonWrapperEdit;
    private CircleButton imageButtonWrapperClose;
    private ImageView buttonSend;
    private ImageView buttonPlay;
    private LinearLayout playLayout;

    public static String PREFS_NAME = "GiftData";
    private String TAG = GiftDispatch.class.getSimpleName().toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_dispatch);

        buttonSend = (ImageView) findViewById(R.id.buttonSend);
        buttonPlay = (ImageView) findViewById(R.id.buttonPlay);

        playLayout = (LinearLayout) findViewById(R.id.playLayout);

        imageButtonGiftEdit = (CircleButton) findViewById(R.id.buttonEditGift);
        imageButtonGiftClose = (CircleButton) findViewById(R.id.buttonCloseGift);
        imageButtonWrapperEdit = (CircleButton) findViewById(R.id.buttonEditWrapper);
        imageButtonWrapperClose = (CircleButton) findViewById(R.id.buttonCloseWrapper);

        imageGiftSelected = (ImageView) findViewById(R.id.imageGiftSelected);
        imageWrapperSelected = (ImageView) findViewById(R.id.imageWrapperSelected);

        imageGiftSelected.setImageResource(Settings.getInstance().getImageGiftSelected());
        imageWrapperSelected.setImageResource(Settings.getInstance().getImageWrapperSelected());

        overlayGift = (View) findViewById(R.id.overlay_gift);
        overlayWrapper = (View) findViewById(R.id.overlay_wrapper);


        imageGiftSelected.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                overlayGift.setVisibility(View.VISIBLE);
                imageButtonGiftEdit.setVisibility(View.VISIBLE);
                imageButtonGiftClose.setVisibility(View.VISIBLE);
                return false;
            }
        });

        imageWrapperSelected.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                overlayWrapper.setVisibility(View.VISIBLE);
                imageButtonWrapperEdit.setVisibility(View.VISIBLE);
                imageButtonWrapperClose.setVisibility(View.VISIBLE);
                return false;
            }
        });

        // Image Change button listener
        imageButtonGiftEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.getInstance().setGiftSelectionStatus(Utility.GiftSelectionStatus.CHANGE);
                Intent intent = new Intent(GiftDispatch.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        //Close button listener
        imageButtonGiftClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overlayGift.setVisibility(View.INVISIBLE);
                imageButtonGiftClose.setVisibility(View.INVISIBLE);
                imageButtonGiftEdit.setVisibility(View.INVISIBLE);
            }
        });

        // Wrapper change button listener
        imageButtonWrapperEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.getInstance().setWrapperSelectionStatus(Utility.WrapperSelectionStatus.CHANGE);
                Intent intent = new Intent(GiftDispatch.this, MainActivity.class);
                finish();
                startActivity(intent);

            }
        });

        imageButtonWrapperClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overlayWrapper.setVisibility(View.INVISIBLE);
                imageButtonWrapperClose.setVisibility(View.INVISIBLE);
                imageButtonWrapperEdit.setVisibility(View.INVISIBLE);
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMail();
            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaAdapter.getInstance().playAudioMessage(false);
            }
        });

        if (!Settings.getInstance().isMessageAttached()){
            playLayout.setVisibility(View.INVISIBLE);
        }

    }


    private void createMail() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        // String restoredText = prefs.getString(PREFS_NAME, null);
        Log.v(TAG, "Create mail");
        // if (restoredText != null) {
        String giftID = prefs.getString("giftID", "No name defined");
        String wrapperID = prefs.getString("wrapperID", "No name defined"); // 0
        // is
        // the
        // default
        // value.

        Log.v(TAG, "GiftID - " + giftID + " WrapperID - " + wrapperID);

        Utility.writeToFile(giftID + "-" + wrapperID);

        Utility.sendMail(GiftDispatch.this, "Catalina Gift mail",
                "Tap the attached catalina gift file. If you don't have Gift Exchange application installed, please download it from Google Play Store(http://play.google.com).");
        // }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Overriding back button
            new AlertDialog.Builder(this)
                    .setMessage("Do you want to leave app?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gift_dispatch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
