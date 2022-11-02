package aln.ktversion.jsongsonservertest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GsonString {

    public String fromGsonObject(Object o) {
        return new Gson().toJson(o);
    }
    public Book toGsonBook(String gsonString) {
        return new Gson().fromJson(gsonString,Book.class);
    }
    public Order toGsonOrder(String gsonString) {
        return new Gson().fromJson(gsonString,Order.class);
    }

    public String fromGsonArray(List<String> list) {
        return new Gson().toJson(list);
    }
    public List<String> toGsonArray(String gsonString) {
        Type type = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(gsonString,type);
    }
}
