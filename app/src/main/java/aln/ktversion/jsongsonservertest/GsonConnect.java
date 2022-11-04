package aln.ktversion.jsongsonservertest;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class GsonConnect implements Callable<String> {
    private final String TAG = "TAG GsonConnect";
    private String gsonString,url;

    @Override
    public String call() throws Exception {

        return getData();
    }

    private String getData() {
        LogHistory.d(TAG,"getData :執行"+gsonString);
        HttpURLConnection httpConnection = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            httpConnection = (HttpURLConnection) new URL(url).openConnection();
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setChunkedStreamingMode(0);
            httpConnection.setUseCaches(false);
            httpConnection.setRequestMethod("POST");
//            httpConnection.setRequestProperty("content-type", "application/json");
            httpConnection.setRequestProperty("charset", "UTF-8");
            try(BufferedWriter bw =
                            new BufferedWriter(new OutputStreamWriter(httpConnection.getOutputStream()))
            ) {
                LogHistory.d(TAG,"Gson Write :"+ gsonString);
                bw.write(gsonString);
            }
            int responseCode = httpConnection.getResponseCode();
            if(responseCode == 200) {
                try(InputStreamReader isr =
                            new InputStreamReader(httpConnection.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                ){
                  String line;
                  while (( line = br.readLine()) != null ){
                      stringBuilder.append(line);
                  }
                }
            }else {
                LogHistory.d(TAG, "responseCode: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(httpConnection != null){
                httpConnection.disconnect();
            }
        }
        LogHistory.d(TAG, "End: "+stringBuilder.toString());
        return stringBuilder.toString();

    }

    public GsonConnect( String url,String gsonString) {
        this.gsonString = gsonString;
        this.url = url;
    }

    public String getGsonString() {
        return gsonString;
    }

    public void setGsonString(String gsonString) {
        this.gsonString = gsonString;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
