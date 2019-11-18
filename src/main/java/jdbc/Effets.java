package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Effets {

	String effet;
	String name;
	Connection con;
	String table = ""; 

	public Effets(String effet, String name, Connection con) throws SQLException {
		this.effet = effet;
		this.name = name;
		this.con = con;
		this.addEffect();
	}

	public void addEffect() throws SQLException {
		
		this.setTable();
		PreparedStatement stmt = this.con.prepareStatement("INSERT INTO " + this.table + " (name_"+ table +") values(?)");
		stmt.setString(1, this.name);
		System.out.println(stmt);
		stmt.executeUpdate();

	}
	
	private void setTable() {
		if (this.effet == "positive") {
			 this.table = "peffect";
		} else if (this.effet == "negative") {
			 this.table = "neffect";
		} else if (this.effet == "medical") {
			 this.table = "meffect";
		}
	}

	public List<String> listEffect() throws SQLException {

		this.setTable();
		PreparedStatement stmt = con.prepareStatement("select name_"+ table +" from " + table);

		ResultSet rs = stmt.executeQuery();

		List<String> listeEffect = new ArrayList<String>();

		while (rs.next()) {
			String name = rs.getString("name_" + table);
			listeEffect.add(name);
		}
		return listeEffect;
	}
	
	public static void main(String[] args) throws SQLException {
		ConnectTable test = new ConnectTable();
		Effets test2 = new Effets("medical", "Fatigue",test.getConnection());
		List<String> vite = test2.listEffect();
		for (String string : vite) {
			System.out.println(string);
		}
	}
}
