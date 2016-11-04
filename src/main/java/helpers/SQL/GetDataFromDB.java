package helpers.SQL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by penko.yordanov on 01-Nov-16.
 */
public class GetDataFromDB extends SQLConnectionBase{
    public static Map<String,String> getUserDetails(String email) {
        ArrayList<String[]> results=getResultFromDB(JSONtoSQL.getUserDetailsSQL(email));
        String[] splitBirth=results.get(0)[6].split(" ");
        String[] splitBirthDDMMYY=splitBirth[0].split("-");
        Map<String,String> userDetails=new HashMap<>();
        userDetails.put("firstName",results.get(0)[1]);
        userDetails.put("lastName",results.get(0)[2]);
        userDetails.put("address",results.get(0)[3]);
        userDetails.put("phone",results.get(0)[4]);
        userDetails.put("language",results.get(0)[5]);
        userDetails.put("birthYY",splitBirthDDMMYY[0]);
        userDetails.put("birthMM",splitBirthDDMMYY[1]);
        userDetails.put("birthDD",splitBirthDDMMYY[2]);
        return userDetails;

    }

    public static int getAdsInArea(String areaName) {
        return  Integer.parseInt(getResultFromDB(JSONtoSQL.getAdsInArea(areaName)).get(0)[0]);
    }

    public static int getFollowersInArea(String areaName) {
        return Integer.parseInt(getResultFromDB(JSONtoSQL.getFollowersInAreaSQL(areaName)).get(0)[0]);
    }
    public static int getFollowersByKeyword(String keyword) {
        return Integer.parseInt(getResultFromDB(JSONtoSQL.getFollowersByKeywordSQL(keyword)).get(0)[0]);
    }

    public static int getAdsWithKeyword(String keyword) {
        return Integer.parseInt(getResultFromDB(JSONtoSQL.getAdsWithKeywordSQL(keyword)).get(0)[0]);
    }

    public static void main(String[] args) {
        //System.out.println(getFollowersByKeyword("iPhone 6s"));
        System.out.println(getAdsWithKeyword("iPhone 6s"));
    }

    public static String getRequestCode(String email) {
        return getResultFromDB(JSONtoSQL.getVerificationCode(email)).get(0)[0];
    }

}
