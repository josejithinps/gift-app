package com.Catalina.catalinaapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Catalina.utilities.Settings;
import com.Catalina.utilities.Utility;
import com.Catalina.wrapper.ImageAdapter;
import com.Catalina.wrapper.SlidingUpPanelLayout;
import com.Catalina.wrapper.SlidingUpPanelLayout.PanelSlideListener;

import java.io.File;
import java.io.IOException;



public class MainActivity extends Activity {

	private GridView gridView;
    private LinearLayout mainLayout;
	private TextView handleInstruction;
	private ImageView image1, image2, image3, image4,imageMicro;
    private TextView micInstructionLabel;
    private MediaRecorder mRecorder = null;
	public static String PREFS_NAME = "GiftData";
	public String TAG = "MainActivity";
	private LinearLayout linearLayoutDragView;
	private LinearLayout viewRecordPanel;
	private LinearLayout layoutTimerText;
	private Animation animBounce;
	private Handler handler;
	private TextView textRecordTimeLeft;
	private String seconds = "";
    private Button buttonNext;
    private MicStatus MIC_STATUS = MicStatus.OFF;
    private CountDown countDown;
//    private static String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/catamedia.3gp";
    final String FILENAME = "/sdcard/Gift.catalina";
	private Integer[] thumbIDs = {

			R.drawable.pens, R.drawable.perfume, R.drawable.bag, R.drawable.flower,
			R.drawable.chocolate, R.drawable.teddy_bear, R.drawable.backpack,
			R.drawable.watch

	};

	private SlidingUpPanelLayout panel;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        panel = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

        // if condition to be added to select view for edit
        if (Settings.getInstance().getWrapperSelectionStatus() == Utility.WrapperSelectionStatus.CHANGE)
        {
            panel.setEnabled(true);
            panel.expandPanel();
        } else{
            panel.setEnabled(false);
        }

        micInstructionLabel = (TextView) findViewById(R.id.micInstructionLabel);
		layoutTimerText = (LinearLayout) findViewById(R.id.layoutTimerText);
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
		linearLayoutDragView = (LinearLayout) findViewById(R.id.dragView);
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(new ImageAdapter(this, thumbIDs));
        viewRecordPanel = (LinearLayout) findViewById(R.id.layoutRecordPanel);
		animBounce = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
		textRecordTimeLeft = (TextView) findViewById(R.id.textViewRecordTimeLeft);


		image1 = (ImageView) findViewById(R.id.image1);
		image2 = (ImageView) findViewById(R.id.image2);
		image3 = (ImageView) findViewById(R.id.image3);
		image4 = (ImageView) findViewById(R.id.image4);
		imageMicro = (ImageView) findViewById(R.id.image_microphone);
		handleInstruction = (TextView) findViewById(R.id.handleInstruction);
        buttonNext = (Button) findViewById(R.id.buttonNext);


        File file = new File(FILENAME);
        if(file.exists())
        {
            boolean delete = file.delete();
            Log.v("File deleation", "delete flag +"+delete);

        }

        // *************** Wrapper Listeners *******************

		image1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setWrapperSelected(Integer.toString(0));
				Log.v("Offer 1", "Selected");

				promptVoiceMessage();
				Settings.getInstance().setImageWrapperSelected(R.drawable.wrapper1);

			}
		});

		image2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setWrapperSelected(Integer.toString(1));
				Log.v("Offer 2", "wrapper 2 Selected");
				promptVoiceMessage();
				Settings.getInstance().setImageWrapperSelected(R.drawable.wrapper2);
			}
		});

		image3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setWrapperSelected(Integer.toString(2));
				Log.v("Offer 3", "wrapper 3 Selected");
				promptVoiceMessage();
				Settings.getInstance().setImageWrapperSelected(R.drawable.wrapper3);
			}
		});

		image4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setWrapperSelected(Integer.toString(3));
				Log.v("Offer 4", "wrapper 4 Selected");
				promptVoiceMessage();
				Settings.getInstance().setImageWrapperSelected(R.drawable.wrapper4);
			}
		});


		imageMicro.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

                if(MIC_STATUS == MicStatus.OFF){
                    MIC_STATUS = MicStatus.ON;

                    micInstructionLabel.setText(R.string.mic_recording);
                    showTimer();
                    showMessage("Recording...");
                } else {
                    layoutTimerText.setVisibility(View.INVISIBLE);
                    buttonNext.setVisibility(View.VISIBLE);
                    stopRecording();
                    showMessage("Recording stopped!");
                    countDown.cancel();
                    imageMicro.setClickable(false);
                    micInstructionLabel.setVisibility(View.INVISIBLE);
                }


			}
		});

		handler = new Handler();




		panel.setPanelSlideListener(new PanelSlideListener() {

			@Override
			public void onPanelSlide(View view, float slideOffset) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPanelHidden(View panel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPanelExpanded(View view) {
				// TODO Auto-generated method stub
				handleInstruction.setText("Get Your Wrapper");
                mainLayout.removeView(gridView);
			}

			@Override
			public void onPanelCollapsed(View panel) {
				// TODO Auto-generated method stub
				handleInstruction.setText("Select Your Gift");
			}

			@Override
			public void onPanelAnchored(View panel) {
				// TODO Auto-generated method stub

			}
		});



		gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int id,
                                    long arg3) {
                // TODO Auto-generated method stub
                Log.v("item clicked", "" + Integer.toString(id));
                SharedPreferences.Editor editor = getSharedPreferences(
                        PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("giftID", Integer.toString(id));
                editor.commit();

                //Saving id of gift's image selected for using later
                Utility.giftFinder(id);

                // Deciding navigation
                if (Settings.getInstance().getGiftSelectionStatus() == Utility.GiftSelectionStatus.CHANGE) {
                    Intent intent = new Intent(MainActivity.this, GiftDispatch.class);
                    Settings.getInstance().setGiftSelectionStatus(Utility.GiftSelectionStatus.SELECTED);
                    startActivity(intent);
                    finish();
                }else {
                    Settings.getInstance().setGiftSelectionStatus(Utility.GiftSelectionStatus.SELECTED);
                    panel.expandPanel();

                }



            }
        });

        buttonNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, GiftDispatch.class);
                finish();
                startActivity(intent);
            }
        });



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


    private enum MicStatus {
        ON,
        OFF,
        DONE;
    }

	private void setWrapperSelected(String wrapperID) {
		SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE).edit();
		editor.putString("wrapperID", wrapperID);
		editor.commit();

	}

	public void showMessage(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	private void createMail() {
		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		Log.v(TAG, "Creating mail");
		String giftID = prefs.getString("giftID", "No name defined");
		String wrapperID = prefs.getString("wrapperID", "No name defined"); // 0
		Utility.writeToFile(giftID + "-" + wrapperID);
		Utility.sendMail(MainActivity.this, "Catalina Gift mail",
				"Tap the attached catalina gift file");
		// }
	}

	private void promptVoiceMessage() {
        if (Settings.getInstance().getWrapperSelectionStatus() == Utility.WrapperSelectionStatus.CHANGE) {

            Intent intent = new Intent(MainActivity.this, GiftDispatch.class);
            Settings.getInstance().setWrapperSelectionStatus(Utility.WrapperSelectionStatus.SELECTED);
            startActivity(intent);
            finish();
        } else {
            Settings.getInstance().setWrapperSelectionStatus(Utility.WrapperSelectionStatus.SELECTED);
            panel.collapsePanel();
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("Got a message?")
                    .setMessage("Add a voice message?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Settings.getInstance().setMessageAttached(true);
                            showRecordPanel();


                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Settings.getInstance().setMessageAttached(false);
                    Intent intent = new Intent(MainActivity.this, GiftDispatch.class);
                    finish();
                    startActivity(intent);
                }
            }).show();
        }

	}

	private void showRecordPanel(){
		linearLayoutDragView.removeAllViews();
		linearLayoutDragView.addView(viewRecordPanel);
		linearLayoutDragView.setGravity(Gravity.CENTER_HORIZONTAL);
		viewRecordPanel.setVisibility(View.VISIBLE);
		panel.expandPanel(0.6f);
		panel.setEnabled(false);
		imageMicro.startAnimation(animBounce);
	}

    private void startRecording(){

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFile(Utility.mFilePath);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("TEST", "prepare() failed");
        }
        mRecorder.start();
        Log.e("Recording", "Recording started");
    }

    private void showTimer() {

        startRecording();
		countDown = new CountDown(10800,1000);
		countDown.start();
		layoutTimerText.setVisibility(View.VISIBLE);
	}

	private class CountDown extends CountDownTimer {

		public CountDown(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long l) {
			long temp = 0;
			temp = l/1000;
			final String seconds = Long.toString(temp);
			handler.post(new Runnable() {
                @Override
                public void run() {

                    textRecordTimeLeft.setText(" " + seconds + " second");

                }
            });
		}

		@Override
		public void onFinish() {

            layoutTimerText.setVisibility(View.INVISIBLE);
			buttonNext.setVisibility(View.VISIBLE);
            stopRecording();
		}

	}

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
}


