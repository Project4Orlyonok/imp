import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class json {
    JSONObject Json= new JSONObject();
    JSONArray JsonArray=new JSONArray();

    public String stroka (Map<String,String> data){
        Json.put("name","dis");
//        Map<String, Double> minprice = new HashMap<>();
        JsonArray.add(data);
        Json.put("data",JsonArray);
        return Json.toJSONString();

    }
    public Map<String,String> data(double price,double power,String from,String to,double time){
        Map<String, String> data = new HashMap<>();
        data.put("Price", String.valueOf(price));
        data.put("Power", String.valueOf(power));
        data.put("From",from);
        data.put("To",to);
        data.put("Time", String.valueOf(time));
        return data;
    }
}

