package aln.ktversion.jsongsonservertest;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonString {
    private final String TAG = "TAG JsonString";

    public String fromJsonBook(Book book) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",book.getName());
            jsonObject.put("price",book.getPrice());
            jsonObject.put("author",book.getAuthor());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonString  = jsonObject.toString();
        LogHistory.d(TAG,"jsonString :"+jsonString);
        return jsonString;
    }
    public Order toJSonOrder(String string){
        Order order = new Order();
        try {
            JSONObject jsonObject = new JSONObject(string);
            order.setTotalPay(jsonObject.getDouble("totalPay"));
            order.setOrderId(jsonObject.getString("orderId"));
            String s = jsonObject.getString("book");

            JSONObject jBook = new JSONObject(s);
            order.setBook(new Book(jBook.getString("name")
                    ,jBook.getDouble("price"), jBook.getString("author")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return order;
    }
}
