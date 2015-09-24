package com.Catalina.catalinaapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class MailActivity extends Activity {
//Button btn, scratch;
final String FILENAME = "/sdcard/Gift.catalina";
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       setContentView(R.layout.mail_layout);
       String textToSaveString = "12 16";
		 writeToFile(textToSaveString);

	}
	public void sendGmail(Activity activity, String subject, String text)   
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
	private void writeToFile(String data) {
        try {

        	File myFile = new File(FILENAME);
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = 
									new OutputStreamWriter(fOut);
			myOutWriter.write(data);
			myOutWriter.close();
			fOut.close();
			Toast.makeText(getBaseContext(),
					"Done writing SD 'mysdfile.txt'",
					Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            Log.e("Main Activity", "File write failed: " + e.toString());
        } 
		
	}
		private String readFromFile() {
		
        String ret = "";

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
    	ret = stringBuilder.toString();
    	myReader.close();
    	Toast.makeText(getBaseContext(),
    			"Done reading SD 'mysdfile.txt'",
    			Toast.LENGTH_SHORT).show();
            }
            catch (FileNotFoundException e) {
            	Log.e("Main activity", "File not found: " + e.toString());
    		} catch (IOException e) {
    			Log.e("Main activity", "Can not read file: " + e.toString());
    		}

            return ret;
    	}
}
