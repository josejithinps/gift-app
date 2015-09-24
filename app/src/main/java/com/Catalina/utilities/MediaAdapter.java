package com.Catalina.utilities;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by catalina on 8/17/15.
 */
public class MediaAdapter {

    private static MediaAdapter instance = null;


    private MediaAdapter () {

    }

    public static MediaAdapter getInstance(){
        if(instance == null) {
            instance = new MediaAdapter();
            return  instance;
        } else {
            return instance;
        }
    }

    // checks if recieved message has a voice message attached
    public boolean hasVoiceMessage() {
        File mediaFile = new File(Utility.txtFilePath);
        if (mediaFile.length() > 3)
            return true;
        else
            return false;
    }

    public void playAudioMessage(boolean isReceivedMedia){
        try{
            String filePath;
            MediaPlayer mediaPlayer = new MediaPlayer();
            if (isReceivedMedia) {
                filePath = Utility.txtFilePath;
                Log.v("******","path: "+filePath);

                File mFile = new File(filePath);
                Log.v("Main Activity", "mediafileLength --" + mFile.length());
                FileInputStream fis = new FileInputStream(filePath);
                mediaPlayer.setDataSource(fis.getFD(),3, mFile.length()-3);
            }
            else{

                filePath = Utility.mFilePath;
//                File mFile = new File(filePath);
//                FileInputStream fis = new FileInputStream(filePath);
                mediaPlayer.setDataSource(filePath);
            }


            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException ex) {
            String s = ex.toString();
            ex.printStackTrace();
        }
    }
}
