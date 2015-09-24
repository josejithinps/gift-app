package com.Catalina.catalinaapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.Catalina.utilities.MediaAdapter;
import com.Catalina.utilities.ServiceConnector;
import com.Catalina.utilities.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@SuppressLint("NewApi")
public class GiftActivity extends Activity implements OnClickListener {

	RelativeLayout parentLayout;
	LinearLayout bottomLayout;
	ImageView image_gift;
	ImageView image_wrapper;
	Bitmap bgr;
	Canvas canvas;
    byte[] dataFromMail;
	private int counter;
	private Button buttonVoice;
    FileInputStream fis = null;
    String path;
	private String TAG = "Catalina";
	int cx, cy, sx, sy;
	private static final int SWIPE_MIN_DISTANCE = 5;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	String part1; 
	String part2;
	View.OnTouchListener gestureListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_gift);
		
		bottomLayout = (LinearLayout) findViewById(R.id.bottomLayout);
		
		bottomLayout.setVisibility(View.GONE);

		
		
		buttonVoice = (Button) findViewById(R.id.buttonVoice);
		buttonVoice.setEnabled(false);
		
		counter = 0;
		
		Intent intent = getIntent();
		Uri uri = getIntent().getData();

		if (intent == null)
			return;
		Uri uriData = intent.getData();
		if (uriData == null)
			return;
		String scheme = uriData.getScheme();
		path = uriData.getPath();
		dataFromMail = readFromFile(uri);


        String textFromByteArray = new String(dataFromMail,0,3);

		Log.v("Main Activity", "Read from File--" + textFromByteArray);
		String[] parts = textFromByteArray.split("-");
		part1 = parts[0]; // 004
		part2 = parts[1];

		image_wrapper = (ImageView) findViewById(R.id.wrapper_img);
		image_gift = (ImageView) findViewById(R.id.gift_img);

		gestureDetector = new GestureDetector(this, new MyGestureDetector());
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		};

		// ******** Button Click Listeners ***********
//		buttonPrint.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
////				ServiceConnector connector = new ServiceConnector();
////				try {
////					Log.v("data",""+part2.toString());
////					connector.downloadUrl(part2.toString());
////				} catch (URISyntaxException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
//				Thread thread = new Thread()
//				{
//				    @Override
//				    public void run() {
//				        try {
//				        	ServiceConnector SC = new ServiceConnector();
//				        	//int val = Integer.parseInt(part2)+1;
//				        	//part2 = String.valueOf(Integer.parseInt(part2)+1);
//							Log.v("Inside Thread", String.valueOf(Integer.parseInt(part2)+1));
//
//								SC.downloadUrl(String.valueOf(Integer.parseInt(part2)+1));
//
//				        } catch (Exception e) {
//				            e.printStackTrace();
//				        }
//				    }
//				};
//
//				thread.start();
//			}
//		});
		
		buttonVoice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

                Toast.makeText(getApplicationContext(), "Playing Message...", Toast.LENGTH_SHORT).show();
				MediaAdapter.getInstance().playAudioMessage(true);

            }
		});
		// ********************************************
		if (part1.length() > 0) {
			switch (Integer.parseInt(part1)) {
			case 0:
				image_gift.setImageResource(R.drawable.pens_mdpi);
				break;

			case 1:
				image_gift.setImageResource(R.drawable.perfume_mdpi);
				break;
			case 2:
				image_gift.setImageResource(R.drawable.bag_mdpi);
				break;
			case 3:
				image_gift.setImageResource(R.drawable.flower_mdpi);
				break;
			case 4:
				image_gift.setImageResource(R.drawable.chocolate_mdpi);
				break;
			case 5:
				image_gift.setImageResource(R.drawable.teddy_bear_mdpi);
				break;
			case 6:
				image_gift.setImageResource(R.drawable.backpack_mdpi);
				break;
			case 7:
				image_gift.setImageResource(R.drawable.watch_mdpi);
				break;
			default:
				image_gift.setImageResource(R.drawable.flower_mdpi);
				break;
			}
		}
		if (part2.length() > 0) {
			switch (Integer.parseInt(part2)) {
			case 0:
				bgr = BitmapFactory.decodeResource(getResources(),R.drawable.wrapper1).copy(Bitmap.Config.ARGB_8888, true);// Bitmap.createBitmap(300,
																				// 300,
																				// Bitmap.Config.ARGB_8888);
				break;

			case 1:
				bgr = BitmapFactory.decodeResource(getResources(),R.drawable.wrapper2).copy(Bitmap.Config.ARGB_8888, true);// Bitmap.createBitmap(300,
																				// 300,
																				// Bitmap.Config.ARGB_8888);
				break;
			case 2:
				bgr = BitmapFactory.decodeResource(getResources(),R.drawable.wrapper3).copy(Bitmap.Config.ARGB_8888, true);// Bitmap.createBitmap(300,
																				// 300,
																				// Bitmap.Config.ARGB_8888);
				break;
			case 3:
				bgr = BitmapFactory.decodeResource(getResources(),R.drawable.wrapper4).copy(Bitmap.Config.ARGB_8888, true);// Bitmap.createBitmap(300,
				// 300,
				// Bitmap.Config.ARGB_8888);
				break;
			default:
				break;
			}
		}

		canvas = new Canvas(bgr);
		//

		image_wrapper.setImageBitmap(bgr);
		image_wrapper.setOnClickListener(GiftActivity.this);
		image_wrapper.setOnTouchListener(gestureListener);
		// image_gift.setImageResource(R.drawable.preview);
		cx = canvas.getWidth();
		cy = canvas.getHeight();
		Display display = getWindowManager().getDefaultDisplay();
		sx = display.getWidth();
		sy = display.getHeight();

	}

	private byte[] readFromFile(Uri uri) {

		Log.v(TAG, "Read file ");
		String ret;
		InputStream in = null;
		try {
			in = getContentResolver().openInputStream(uri);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        try {


        while ((len = in.read(buffer)) != -1){

            byteBuffer.write(buffer, 0, len);
        }
        }
        catch (Exception e){


        }

        File file = new File (Utility.txtFilePath);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file);

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        try {


            fos.write(byteBuffer.toByteArray());

        } catch (IOException e) {

            e.printStackTrace();
        }
        try {
            fos.flush();

        } catch (IOException e) {

            e.printStackTrace();
        }
        try {
            fos.close();

        } catch (IOException e) {

            e.printStackTrace();
        }


        return byteBuffer.toByteArray();

	}

	// }
	class MyGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {

			return false;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

	}

	@Override
	public void onClick(View v) {


	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		counter++;
		if(counter == 250){
//			buttonPrint.setEnabled(true);
			if (MediaAdapter.getInstance().hasVoiceMessage()) {
				buttonVoice.setEnabled(true);
				bottomLayout.setVisibility(View.VISIBLE);
				MediaAdapter.getInstance().playAudioMessage(true);
			}

		}

		Paint paint = new Paint();
		paint.setAlpha(0xFF);// transparent color
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

		canvas.drawCircle(event.getX() * ((float) cx / (float) sx),
				event.getY() * ((float) cy / (float) sy), 60, paint);

		image_wrapper.invalidate();
		return super.dispatchTouchEvent(event);

	}

}
