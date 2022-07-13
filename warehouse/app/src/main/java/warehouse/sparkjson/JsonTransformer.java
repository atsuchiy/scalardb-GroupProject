package warehouse.sparkjson;

import com.google.gson.Gson;
import spark.ResponseTransformer;

// フロントエンドがPOSTした内容をJSONに変換
public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object model) throws Exception {
        return gson.toJson(model);
    }

    public <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
}