package net.ezbim.scan;

/**
 * Created by hdk on 2016/12/3.
 */

public enum ZBarState {
    ZBAR_AUTO_FOCUS(1),
    ZBAR_RESTART_PREVIEW(2),
    ZBAR_DECODE_SUCCEEDED(3),
    ZBAR_DECODE_FAILED(4),
    ZBAR_DECODE(5),
    ZBAR_QUIT(6);


    private int value = 0;
    private ZBarState(int value){
        this.value =value;
    }

    public static ZBarState valueOf(int value){
        switch (value){
            case 1:
                return ZBAR_AUTO_FOCUS;
            case 2:
                return ZBAR_RESTART_PREVIEW;
            case 3:
                return ZBAR_DECODE_SUCCEEDED;
            case 4:
                return ZBAR_DECODE_FAILED;
            case 5:
                return ZBAR_DECODE;
            case 6:
                return ZBAR_QUIT;
            default:
                return null;
        }
    }

    public int value(){
        return this.value;
    }

}
