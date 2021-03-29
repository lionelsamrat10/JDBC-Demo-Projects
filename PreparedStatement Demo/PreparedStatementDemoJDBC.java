import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;
public class PreparedStatementDemoJDBC {

	public static void main(String[] args) throws Exception {
    //The database.properties file contains all the necessary info like url, username and password..
		FileReader reader=new FileReader("database.properties");
		Properties props = new Properties();
		props.load(reader);
		
		try {
			//Configure the Connection
			Connection myConn = DriverManager.getConnection(props.getProperty("DB_URL"), props.getProperty("DB_USERNAME"), props.getProperty("DB_PASSWORD"));
			//Prepared Statement Object creation
			PreparedStatement ps = myConn.prepareStatement("INSERT INTO SAM VALUES(?, ?)");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			//Run an infinite loop and take values until the user presses "NO"
			do {
				System.out.println("Enter the ID:");
				int id = Integer.parseInt(br.readLine());
				
				System.out.println("Enter the name:");
				String name = br.readLine();
				
				//Set the values
				ps.setInt(1, id);
				ps.setString(2, name);
				//Message showing Successful Connection
				System.out.println("Connection successfully established!!");
				//Execute the query
				int i = ps.executeUpdate();
				System.out.println(i + " records affected!!");
				
				System.out.println("Do you want to continue: y/n?");
				String s = br.readLine();
				
				if(s.startsWith("n")) {
					break;
				}
			}while(true);
			
			//Let us display the details from the SAM table here
			Statement myStmt = myConn.createStatement();
			String myQuery = "SELECT * FROM SAM";
			ResultSet rs = myStmt.executeQuery(myQuery);
			
			//Loop through the resultSet object and display all the results, retrieved from the Database
			while(rs.next()) {
				System.out.println(rs.getInt("ID") + " - " + rs.getString("NAME"));
			}
		}
		catch (Exception exp) {
			System.out.println("Connection unsuccessful!!");
			exp.printStackTrace();
		}
	}
}
