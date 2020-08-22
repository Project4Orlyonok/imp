import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonGen {
    JSONObject Json= new JSONObject();
    JSONArray JsonArray=new JSONArray();

    public String stroka (Map<String,String> data){
//        Json.put("name","dis");
//        Map<String, Double> minprice = new HashMap<>();
        JsonArray.add(data);
        Json.put("data",JsonArray);
        return Json.toJSONString();

    }
    public Map<String,String> dataGen(double power,String name,int time){
        Map<String, String> data = new HashMap<>();
        data.put("Power Before", String.valueOf(power));
        data.put("Power ", String.valueOf(power));
        data.put("Name of Generation",name);
        data.put("Time", String.valueOf(time));
        return data;
    }
}

