package aln.ktversion.jsongsonservertest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.Callable;

public class GsonConnect implements Callable<String> {
    private final String TAG = "TAG GsonConnect";
    private String gsonString,url;

    @Override
    public String call() throws Exception {

        return getData();
    }

    private String getData() {
        HttpURLConnection httpURLConnection = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setChunkedStreamingMode(0);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("charset","UTF-8");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write(gsonString);
            LogHistory.d(TAG,"OutGsonString :" + gsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int responseCode = 0;
        try {
            responseCode = httpURLConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(responseCode == 200) {
            try (
                    InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                    BufferedReader bfReader = new BufferedReader(inputStreamReader);
            ) {
                String line;
                while ((line = bfReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
                LogHistory.d(TAG,"response :"+stringBuilder.toString());
            }
        }
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
