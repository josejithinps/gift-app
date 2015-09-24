package com.Catalina.utilities;

/**
 * Created by catalina on 8/13/15.
 */
public class Settings {

    private static Settings instance = null;
    private int imageGiftSelected;
    private int imageWrapperSelected;
    private boolean messageAttached = false;
    private Utility.GiftSelectionStatus giftSelectionStatus;
    private Utility.WrapperSelectionStatus wrapperSelectionStatus;
    private Utility.VoiceMessageStatus voiceMessageStatus;


    private Settings() {

        giftSelectionStatus = Utility.GiftSelectionStatus.NOT_SELECTED;
        wrapperSelectionStatus = Utility.WrapperSelectionStatus.NOT_SELECTED;
        voiceMessageStatus = Utility.VoiceMessageStatus.NOT_RECORDED;

    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();

        }

        return instance;
    }

    // Accessors
    public int getImageGiftSelected() {
        return imageGiftSelected;
    }

    public void setImageGiftSelected(int imageGiftSelected) {
        this.imageGiftSelected = imageGiftSelected;
    }

    public int getImageWrapperSelected() {
        return imageWrapperSelected;
    }

    public void setImageWrapperSelected(int imageWrapperSelected) {
        this.imageWrapperSelected = imageWrapperSelected;
    }

    public Utility.GiftSelectionStatus getGiftSelectionStatus() {
        return giftSelectionStatus;
    }

    public void setGiftSelectionStatus(Utility.GiftSelectionStatus giftSelectionStatus) {
        this.giftSelectionStatus = giftSelectionStatus;
    }

    public Utility.WrapperSelectionStatus getWrapperSelectionStatus() {
        return wrapperSelectionStatus;
    }

    public void setWrapperSelectionStatus(Utility.WrapperSelectionStatus wrapperSelectionStatus) {
        this.wrapperSelectionStatus = wrapperSelectionStatus;
    }

    public Utility.VoiceMessageStatus getVoiceMessageStatus() {
        return voiceMessageStatus;
    }

    public void setVoiceMessageStatus(Utility.VoiceMessageStatus voiceMessageStatus) {
        this.voiceMessageStatus = voiceMessageStatus;
    }

    public boolean isMessageAttached() {
        return messageAttached;
    }

    public void setMessageAttached(boolean messageAttached) {
        this.messageAttached = messageAttached;
    }
}
