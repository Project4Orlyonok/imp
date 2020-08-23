import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonGen {
    JSONObject Json= new JSONObject();
    JSONArray JsonArray=new JSONArray();

    public String stroka (Map<String,String> data,String name){
        Json.put("name",name);
//        Map<String, Double> minprice = new HashMap<>();
//        for (Object JA : JsonArray){
//            System.out.println(JA);
//            System.out.println(data);
//            if (!JA.equals(data)){
//                JsonArray.add(data);
//            }
//        }

//        for (int i=0;i<JsonArray.size();i++){
//            System.out.println(JsonArray.indexOf(data));
//            System.out.println(data);
            if (!JsonArray.contains(data)){ JsonArray.add(data);}
//            JsonArray.forEach(e-> System.out.println(e));
//        }
//        JsonArray.add(data);

        Json.put("data",JsonArray);
        return Json.toJSONString();

    }
    public Map<String,String> dataGen(double powerBefore,double powerAfter,int time){
        Map<String, String> data = new HashMap<>();
        data.put("Power Before", String.valueOf(powerBefore));
        data.put("Power After", String.valueOf(powerAfter));
        data.put("Time", String.valueOf(time));
        return data;
    }
}

