package cn.wsgwz.basemodule.utilities;

import cn.wsgwz.basemodule.BaseConst;
import com.google.gson.*;

public class GsonUtil {
    private static final Gson GSON = BaseConst.INSTANCE.getGSON();

    public static String toPrettyFormat(String jsonStr) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(jsonStr).getAsJsonObject();

        //Logger.json(jsonStr);
        //return jsonStr;
        return GSON.toJson(jsonObject);

       /* JsonReader reader = new JsonReader(new StringReader(jsonStr));
        reader.setLenient(true);
        JsonParser jsonPar = new JsonParser();
        JsonElement jsonEl = jsonPar.parse(reader);
        String prettyJson = GSON.toJson(jsonEl);
        Logger.d(prettyJson);
        return prettyJson;*/

    }

    public static boolean isJson(String jsonStr) {
        //JsonElement jsonElement;
        try {
            //jsonElement =
            new JsonParser().parse(jsonStr);
        } catch (Exception e) {
            return false;
        }
        /*if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonObject()) {
            return false;
        }*/
        return true;
    }

}
