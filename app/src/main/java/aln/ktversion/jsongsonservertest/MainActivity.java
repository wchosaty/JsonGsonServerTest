package aln.ktversion.jsongsonservertest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG MainActivity";
    private EditText etName,etAuthor,etPrice;
    private Button btCreateNew,btSend;
    private TextView tvSend,tvReceive,tvMessage;
    private Spinner spinner;
    private List<String> infoList;
    private List<Book> list;
    private ArrayAdapter adapter;
    private String itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        initial();
        handleListener();

    }

    private void initial() {
        list = new ArrayList<>();
        infoList = new ArrayList<>();
        infoList.add("json");infoList.add("gson");
        adapter =new ArrayAdapter<>(this,R.layout.item_spinner,infoList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView itemView = (TextView) view;
                itemName = String.valueOf(itemView.getText());
                LogHistory.d(TAG,"spinner :"+ itemName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void handleListener() {
        // 建立Book
        btCreateNew.setOnClickListener(v -> {
            if( checkData(etAuthor) && checkData(etName)
                    && checkData(etPrice) ){
                String name = etName.getText().toString().trim();
                double price = Double.valueOf(etPrice.getText().toString().trim());
                String author = etAuthor.getText().toString().trim();
                Book book = new Book(name,price,author);
                list.add( new Book(name,price,author) );
                toGsonObject(book);

                LogHistory.d(TAG,"new book create Book:"+name);
//                toInitialEditText();
            }
        });

        btSend.setOnClickListener(v -> {
            LogHistory.d(TAG,"btSend");
                    toGsonArray();
        });
    }

    private void toGsonObject(Book book) {
        String gsonObjectString = new Gson().toJson(book);
        LogHistory.d(TAG,"gsonObject :" + gsonObjectString);
        tvSend.setText(gsonObjectString);
    }

    private void toGsonArray() {
        String gsonString = new Gson().toJson(list);
        LogHistory.d(TAG,"toGsonString :"+gsonString);
        tvSend.setText(gsonString);
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
        btCreateNew = findViewById(R.id.btCreateNew);
        btSend = findViewById(R.id.btSend);
        tvMessage = findViewById(R.id.tvMessage);
        tvReceive = findViewById(R.id.tvReceive);
        tvSend = findViewById(R.id.tvSend);

        spinner = findViewById(R.id.spinner);
    }
}