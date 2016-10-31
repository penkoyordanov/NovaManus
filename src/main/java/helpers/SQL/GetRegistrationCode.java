package helpers.SQL;

/**
 * Created by penko.yordanov on 01-Jun-16.
 */
public class GetRegistrationCode extends SQLConnectionBase{

    public static String getRequestCode(String email) {
      return getResultFromDB(JSONtoSQL.getVerificationCode(email)).get(0)[0];
    }

}
