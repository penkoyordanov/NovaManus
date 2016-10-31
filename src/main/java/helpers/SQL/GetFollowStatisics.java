package helpers.SQL;

/**
 * Created by penko.yordanov on 03-Jun-16.
 */
public class GetFollowStatisics extends SQLConnectionBase {

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
}
