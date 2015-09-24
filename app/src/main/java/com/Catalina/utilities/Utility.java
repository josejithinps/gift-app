package com.Catalina.utilities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.Catalina.catalinaapp.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;



public class Utility {

    public static String txtFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cata.txt";
    public static String mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/catamedia.3gp";
    public static final String FILENAME = "/sdcard/Gift.catalina";

    private String TAG = "Utility Class";
    private Integer[] thumbIDs = {

            R.drawable.pens, R.drawable.perfume, R.drawable.bag, R.drawable.flower,
            R.drawable.chocolate, R.drawable.teddy_bear, R.drawable.backpack,
            R.drawable.watch

    };

    //Enumerators to specify various status
    public enum GiftSelectionStatus {
        SELECTED,
        NOT_SELECTED,
        CHANGE;
    }

    public enum WrapperSelectionStatus {
        SELECTED,
        NOT_SELECTED,
        CHANGE;
    }

    public enum VoiceMessageStatus {
        RECORDED,
        NOT_RECORDED,
        CHANGE;
    }


	
	public static void writeToFile(String data) {

        FileInputStream fis = null;
        byte[] bytes = null;

        if (Settings.getInstance().isMessageAttached()) {
            try {
                fis = new FileInputStream(mFilePath);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                while (fis.available() > 0) {
                    bos.write(fis.read());
                }
            } catch (IOException e1) {

                e1.printStackTrace();
            }
            bytes = bos.toByteArray();

        }


        File file = new File (FILENAME);
        FileOutputStream fos = null;
        byte[] giftWrapperIdentifier = data.getBytes();

        try {
            fos = new FileOutputStream(file,true);

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        try {

            if (fos != null) {
                fos.write(data.getBytes());
                if(Settings.getInstance().isMessageAttached())
                fos.write(bytes);
            }


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
        File mFile = new File(FILENAME);

    }
	
	public String readFromFile() {
		
        String returnValue = "";
    	try{
    	File myFile = new File(FILENAME);
    	FileInputStream fIn = new FileInputStream(myFile);
    	BufferedReader myReader = new BufferedReader(
    			new InputStreamReader(fIn));
    	StringBuilder stringBuilder = new StringBuilder();
    	String aDataRow = "";
    	
    	while ((aDataRow = myReader.readLine()) != null) {
    		stringBuilder.append(aDataRow);
    	}
    	returnValue = stringBuilder.toString();
    	myReader.close();
    	Log.v(TAG, "Reading File - "+returnValue);
            }
            catch (FileNotFoundException e) {
            	Log.e("Main activity", "File not found: " + e.toString());
    		} catch (IOException e) {
    			Log.e("Main activity", "Can not read file: " + e.toString());
    		}

            return returnValue;
    	}


	public static void sendMail(Activity activity, String subject, String text)
	 {   
		
		Log.v("Test", "Gmail Invoke");
	     Intent gmailIntent = new Intent(); 
	     gmailIntent.setType("application/catalina");
	     gmailIntent.setClassName("com.google.android.gm","com.google.android.gm.ComposeActivityGmail");  
	     gmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);  
	     gmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);  
	     gmailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+ FILENAME));
	     
	    try {  
	      activity.startActivity(gmailIntent);  
	    } catch(ActivityNotFoundException ex)  {  
	      // handle error
	    }  
	}

	public static void giftFinder(int id) {
		switch (id) {
			case 0:
				Settings.getInstance().setImageGiftSelected(R.drawable.pens);
				break;
			case 1:
				Settings.getInstance().setImageGiftSelected(R.drawable.perfume);
				break;
			case 2:
				Settings.getInstance().setImageGiftSelected(R.drawable.bag);
				break;
			case 3:
				Settings.getInstance().setImageGiftSelected(R.drawable.flower);
				break;
			case 4:
				Settings.getInstance().setImageGiftSelected(R.drawable.chocolate);
				break;
			case 5:
				Settings.getInstance().setImageGiftSelected(R.drawable.teddy_bear);
				break;
			case 6:
				Settings.getInstance().setImageGiftSelected(R.drawable.backpack);
				break;
			case 7:
				Settings.getInstance().setImageGiftSelected(R.drawable.watch);
				break;
			default:
				Settings.getInstance().setImageGiftSelected(R.drawable.flower);
				break;

		}
	}
}
