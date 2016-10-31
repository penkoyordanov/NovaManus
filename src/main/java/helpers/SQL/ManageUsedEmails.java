package helpers.SQL;


public class ManageUsedEmails extends SQLConnectionBase {
	public ManageUsedEmails(){
		super();
	}

	public static String[] latestRegUser=null;


	public static boolean isEmailThere(String email) {
		boolean isEmailExisting = false;
		for (String[] s : getResultFromDB(JSONtoSQL.selectUsers())) {
			if (s[3].equals(email)) {
				isEmailExisting = true;
				break;
			}
		}
		return isEmailExisting;
	}
	
	public static String[] getLatestRegUser(String email) {
		for (String[] s : getResultFromDB(JSONtoSQL.selectUsers())) {
			if (s[3].equals(email)) {
				latestRegUser=s;
				break;
				
			}
		}
		return latestRegUser;
	}

	public static void printRecords() {
		for (String s : latestRegUser) {
			System.out.println(s);
		}
	}

}
