package helpers.SQL;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by penko.yordanov on 01-Jun-16.
 */
public class SQLConnectionBase {

    private static Statement stm;
    private static Connection con;
    private static ResultSet res;

    protected static void executeScriptNoReturnData(String query){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(JSONtoSQL.getConncectionString());
            stm = con.createStatement();
            stm.executeUpdate(query);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static ArrayList<String[]> getResultFromDB(String query) {
        ArrayList<String[]> records = new ArrayList<String[]>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(JSONtoSQL.getConncectionString());
            stm = con.createStatement();


            res = stm.executeQuery(query);
            ResultSetMetaData rsMetaData = res.getMetaData();

            int cols = rsMetaData.getColumnCount();

            while (res.next()) {

                String fields[] = new String[cols];
                int col = 0;
                for (int colIdx = 1; colIdx <= cols; colIdx++) {
                    fields[col] = res.getString(colIdx);
                    col++;
                }
                records.add(fields);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (res != null) try { res.close(); } catch(Exception e) {}
            if (stm != null) try { stm.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
        return records;
    }
}
