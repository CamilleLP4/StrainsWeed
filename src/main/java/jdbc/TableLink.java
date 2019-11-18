package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableLink {
	
	Connection conn;
	
	public TableLink(Connection conn) {
		this.conn = conn;
	}

	public void addLink(String effect ,int idPlant , String typeEffect ) throws SQLException {
		
		String tableEffect;	
		switch (typeEffect) {
		   case "medical" : 
			  tableEffect = "meffect";
				break;
		   case "positive" : 
			 tableEffect = "peffect";
			    break;
			default :   
			 tableEffect = "neffect";
				break;
		}
		
		PreparedStatement stmt = this.conn.prepareStatement("select id_"+ tableEffect +" from "+ tableEffect +" where name_"+ tableEffect +" = ?");
		
		stmt.setString(1, effect);
		
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) {
		int id = rs.getInt("id_"+ tableEffect);
		System.out.println(id);
		
		stmt.close();
		
		stmt = this.conn.prepareStatement("INSERT INTO " + typeEffect +" values(?,?)");
		stmt.setInt(1, idPlant);
		stmt.setInt(2, id);
		stmt.executeUpdate();
		System.out.println(stmt);
		
		}
		
	}
	
	public static void main(String[] args) throws SQLException {
		ConnectTable test = new ConnectTable();
		TableLink test2 = new TableLink(test.getConnection());
		test2.addLink("Stress", 1, "medical");
	}
	
}
