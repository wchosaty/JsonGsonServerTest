package aln.ktversion.jsongsonservertest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG MainActivity";
    private EditText etName,etAuthor,etPrice;
    private Button btCreateNew,btSend,btReceive;
    private TextView tvSend,tvReceive,tvMessage;
    private List<String> sendList,receiveList;
    private String tempString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        initial();
        handleListener();

    }

    private void initial() {
        sendList = new ArrayList<>();
        receiveList = new ArrayList<>();
    }

    private void handleListener() {
        // 建立Book
        btCreateNew.setOnClickListener(v -> {
            if( checkData(etAuthor) && checkData(etName)
                    && checkData(etPrice) ){
                String name = etName.getText().toString().trim();
                double price = Double.valueOf(etPrice.getText().toString().trim());
                String author = etAuthor.getText().toString().trim();
                String tempString = new GsonString().fromGsonObject(new Book(name,price,author));
                sendList.add( tempString );
                LogHistory.d(TAG,"new book create Book:"+name);
                LogHistory.d(TAG,"GsonBookString :"+tempString);
//                toInitialEditText();
            }
        });

        // Send List
        btSend.setOnClickListener(v -> {
            tempString = new GsonString().fromGsonArray(sendList);
            LogHistory.d(TAG,"btSend"+tempString);
            tvSend.setText(tempString);
            String url = RemoteAccess.URL+"MyJsonGsonServlet";

            String gsonString = RemoteAccess.getData(url,tempString);
            Gson gson = new Gson();
            Order order = gson.fromJson(gsonString,Order.class);
            toTextMessage(tvReceive,order);
        });

        // Receive List
        btReceive.setOnClickListener(v -> {
            receiveList = new GsonString().toGsonArray(tempString);
            for(String s : receiveList){
                LogHistory.d(TAG,"btReceive :"+s);
            }
        });
    }

    private void toTextMessage(TextView tvReceive, Order order) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("購買者 :"+order.getCustomerName())
                .append("\n").append("金額 :"+order.getTotalPay());
        for(int i=0;i<order.getOrderList().size();i++){
            Book book = new Gson().fromJson(order.getOrderList().get(i),Book.class);
            stringBuilder.append("book"+(i+1)+" :"+book.getName() +"\n");
        }
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
        btCreateNew = findViewById(R.id.btCreateNew);
        btSend = findViewById(R.id.btSend);
        btReceive = findViewById(R.id.btReceive);
        tvMessage = findViewById(R.id.tvMessage);
        tvReceive = findViewById(R.id.tvReceive);
        tvSend = findViewById(R.id.tvSend);

    }
}