package aln.ktversion.jsongsonservertest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG MainActivity";
    private EditText etName,etAuthor,etPrice;
    private EditText etName2,etAuthor2,etPrice2;
    private EditText etName3,etAuthor3,etPrice3;
    private Button btBookList,btStringList;
    private TextView tvSend,tvReceive,tvMessage;
    private Spinner spinner;
    private List<String> bookStringList,typeList;
    private List<Book> sourceBookList;
    private ArrayAdapter<String> adapter;
    private String tempString,bookString,selectType;
    private Boolean gsonFlag = false;
    private JsonObject jsonObject;
    private static final String url = RemoteAccess.URL+"MyJsonGsonServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        initial();
        handleListener();

    }

    private void initial() {
        bookStringList = new ArrayList<>();
        typeList = new ArrayList<>();
        sourceBookList = new ArrayList<>();
        sourceBookList.add(new Book("cook",750,"James"));
        sourceBookList.add(new Book("Math",1300,"Tony"));
        sourceBookList.add(new Book("Kotlin",360,"Jerry"));

        // 設定顯示Book
        etName.setText(sourceBookList.get(0).getName());
        etAuthor.setText(sourceBookList.get(0).getAuthor());
        etPrice.setText(String.valueOf(sourceBookList.get(0).getPrice()));

        etName2.setText(sourceBookList.get(1).getName());
        etAuthor2.setText(sourceBookList.get(1).getAuthor());
        etPrice2.setText(String.valueOf(sourceBookList.get(1).getPrice()));

        etName3.setText(sourceBookList.get(2).getName());
        etAuthor3.setText(sourceBookList.get(2).getAuthor());
        etPrice3.setText(String.valueOf(sourceBookList.get(2).getPrice()));

        typeList.add("json");
        typeList.add("gson");

        adapter = new ArrayAdapter<>(this,R.layout.item_spinner,typeList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectType =String.valueOf ( ((TextView) view ).getText() );
                if(Objects.equals(selectType,"json")){
                    gsonFlag = false;
                }else {
                    gsonFlag = true;
                }
                LogHistory.d(TAG,"selectType :"+selectType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void handleListener() {
        findViewById(R.id.btBook1).setOnClickListener(v -> {
            JsonObject jsonObject = new JsonObject();
            JSONObject jObject = new JSONObject();
            if (gsonFlag) {
                jsonObject.addProperty("action", selectType);
                jsonObject.addProperty("type", "book");
                jsonObject.addProperty("data", new GsonString().fromGsonObject(sourceBookList.get(0)));
                tvSend.setText(jsonObject.toString());
                LogHistory.d(TAG, "book1 :" + jsonObject.toString());
                String backString = RemoteAccess.getData(url, jsonObject.toString());
                LogHistory.d(TAG, "backString :" + backString);
                Order order = new Gson().fromJson(backString, Order.class);
                toTextMessage(tvReceive, order);
            }else {
                try {
                    jObject.put("action",selectType);
                    jObject.put("type","book");
                    jObject.put("data",new JsonString().fromJsonBook(sourceBookList.get(0)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvSend.setText(jObject.toString());
                String backString = RemoteAccess.getData(url,jObject.toString());
                Order order = new JsonString().toJSonOrder(backString);
                tvReceive.setText("orderId :"+order.getOrderId()+"\ntotalPay :"+order.getTotalPay()+"\nbookName :"+order.getBook().getName());
                //                toTextMessage(tvReceive, order);
            }


        });
        findViewById(R.id.btBook2).setOnClickListener(v -> {
            jsonObject = new JsonObject();
            jsonObject.addProperty("action","gson");
            jsonObject.addProperty("type","book");
            jsonObject.addProperty("data",new GsonString().fromGsonObject(sourceBookList.get(1)) );
            tvSend.setText(jsonObject.toString());
            String backString = RemoteAccess.getData(url,jsonObject.toString());
            LogHistory.d(TAG,"backString :"+backString);
            Order order = new Gson().fromJson(backString,Order.class);
            toTextMessage(tvReceive,order);
        });
        findViewById(R.id.btBook3).setOnClickListener(v -> {
            jsonObject = new JsonObject();
            jsonObject.addProperty("action","gson");
            jsonObject.addProperty("type","book");
            jsonObject.addProperty("data",new GsonString().fromGsonObject(sourceBookList.get(2)) );
            tvSend.setText(jsonObject.toString());
            String backString = RemoteAccess.getData(url,jsonObject.toString());
            LogHistory.d(TAG,"backString :"+backString);
            Order order = new Gson().fromJson(backString,Order.class);
            toTextMessage(tvReceive,order);
        });

        // 建立BookList
        btBookList.setOnClickListener(v -> {
            jsonObject = new JsonObject();
            jsonObject.addProperty("action","gson");
            jsonObject.addProperty("type","bookList");
            jsonObject.addProperty("data",new GsonString().fromGsonBookArray(sourceBookList));
            tvSend.setText(jsonObject.toString());
            String backString = RemoteAccess.getData(url,jsonObject.toString());
            LogHistory.d(TAG,"backString :"+backString);
            Order order = new Gson().fromJson(backString,Order.class);
            toTextMessage(tvReceive,order);
        });

        // Send StringList
        btStringList.setOnClickListener(v -> {
            bookStringList = new ArrayList<>();
            for(Book book :sourceBookList){
                bookStringList.add(new GsonString().fromGsonObject(book));
            }
            jsonObject = new JsonObject();
            jsonObject.addProperty("action","gson");
            jsonObject.addProperty("type","StringList");
            jsonObject.addProperty("data",new GsonString().fromGsonArray(bookStringList));
            tvSend.setText(jsonObject.toString());
            String backString = RemoteAccess.getData(url,jsonObject.toString());
            tvReceive.setText(backString);

            Order order = new Gson().fromJson(backString,Order.class);
            toTextMessage(tvReceive,order);
        });

    }

    private void toTextMessage(TextView tvReceive, Order order) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("單號 :"+order.getOrderId())
                .append("\n").append("金額 :"+order.getTotalPay()).append("\n");
        for(int i=0;i<order.getOrderList().size();i++){
            Book book = new Gson().fromJson(order.getOrderList().get(i),Book.class);
            stringBuilder.append("book"+(i+1)+" :"+book.getName() +"\n");
        }
        LogHistory.d(TAG,stringBuilder.toString());
        tvReceive.setText(stringBuilder.toString());
    }

    private void toInitialEditText() {
        etPrice.setText("");
        etAuthor.setText("");
        etName.setText("");
    }

    private boolean checkData(TextView textView) {
        if(!Objects.equals(textView.getText().toString().trim(),null)
        && !Objects.equals(textView.getText().toString().trim(),"")){
            return true;
        }else{
            tvMessage.setText("Empty");
            return false;
        }
    }

    private void findViews() {
        etName = findViewById(R.id.etName);
        etAuthor =findViewById(R.id.etAuthor);
        etPrice = findViewById(R.id.etPrice);

        etName2 = findViewById(R.id.etName2);
        etAuthor2 =findViewById(R.id.etAuthor2);
        etPrice2 = findViewById(R.id.etPrice2);
        etName3 = findViewById(R.id.etName3);
        etAuthor3 =findViewById(R.id.etAuthor3);
        etPrice3 = findViewById(R.id.etPrice3);

        btBookList = findViewById(R.id.btBookList);
        btStringList = findViewById(R.id.btStringList);
        tvMessage = findViewById(R.id.tvMessage);
        tvReceive = findViewById(R.id.tvReceive);
        tvSend = findViewById(R.id.tvSend);
        spinner = findViewById(R.id.spinner);

    }
}