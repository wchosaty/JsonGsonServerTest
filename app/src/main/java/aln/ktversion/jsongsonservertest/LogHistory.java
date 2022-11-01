package aln.ktversion.jsongsonservertest;

import android.util.Log;

public class LogHistory {
    private static final Boolean logFlag = true;

    public static void d(String tag,String message){
        if(logFlag){
            Log.d(tag,message);
        }
    }


}
