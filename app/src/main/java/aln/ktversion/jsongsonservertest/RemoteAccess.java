package aln.ktversion.jsongsonservertest;

import java.time.temporal.Temporal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class RemoteAccess {
    private final String TAG = "TAG RemoteAccess";
    public static final String URL = "http://10.0.2.2:8080/MyJsonGsonServlet/";

    public static String getData(String url, String outString){
        GsonConnect gsonConnect = new GsonConnect(url,outString);
        FutureTask<String> task = new FutureTask<>(gsonConnect);
        Thread thread = new Thread(task);
        thread.start();
        try {
            return task.get();
        } catch (Exception e) {
            task.cancel(true);
            return "";
        }
    }
}
