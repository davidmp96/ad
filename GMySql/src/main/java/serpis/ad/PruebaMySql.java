package serpis.ad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PruebaMySql {

	public static void main(String[] args) throws SQLException {

		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost/dbprueba", 
				"root", 
				"sistemas");
	
		java.sql.Statement stmt = connection.createStatement();
		
	    ResultSet rs = stmt.executeQuery( "SELECT * FROM categoria");
	    
	    while ( rs.next() ) {
	        int numColumns = rs.getMetaData().getColumnCount();
	        for ( int i = 1 ; i <= numColumns ; i++ ) {
	           System.out.println( "COLUMN " + i + " = " + rs.getObject(i) );
	        }
	    }
		connection.close();
	} 	
}

