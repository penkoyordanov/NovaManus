package helpers.SQL;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
/**
 * Created by penko.yordanov on 27-Oct-16.
 */
public class JSONtoSQL {
    static JSONParser parser = new JSONParser();


    private static JSONObject parseJsonFromFile(){
        JSONObject jsonObject;
        String sqlFolder=System.getenv("NM_SQLs");
        Object obj = null;
        try {

            obj = parser.parse(new FileReader(sqlFolder+File.separator+"SQL.json"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject =  (JSONObject) obj;
    }

    public static String getConncectionString(){
        return (String) parseJsonFromFile().get("connectionString");
    }

    public static String getAdsInArea(String areaName){
     return String.format((String)parseJsonFromFile().get("getAdsInArea"),areaName);
    }

    public static String getFollowersInAreaSQL(String areaName){
        return String.format((String) parseJsonFromFile().get("getFollowersInAreaSQ"),areaName);
    }

    public static String getFollowersByKeywordSQL(String keyword){
        return String.format((String) parseJsonFromFile().get("getFollowersbyKeywordSQL"),keyword);
    }

    public static String getAdsWithKeywordSQL(String areaName){
        return String.format((String) parseJsonFromFile().get("getAdsWithKeywordSQL"),areaName);
    }

    public static String selectUsers(){
        return (String) parseJsonFromFile().get("selectUsers");
    }

    public static String getVerificationCode(String email){
        return (String) parseJsonFromFile().get("selectUsers");
    }


    public static void main(String[] args) {
        System.out.println(getAdsInArea("Sofia"));
    }
}
