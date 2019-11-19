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

	public Effets(Connection con) throws SQLException {
		this.con = con;
	}

	public void addEffect(String effet, String name) throws SQLException {
		this.setTable(effet);
		PreparedStatement stmt = this.con.prepareStatement("INSERT INTO " + this.table + " (name_"+ table +") values(?)");
		stmt.setString(1, name);
		System.out.println(stmt);
		stmt.executeUpdate();
	}
	
	public void videTable() throws SQLException {
		PreparedStatement stmt = this.con.prepareStatement("DELETE FROM meffect where id_meffect > 0");
		stmt.executeUpdate();
		stmt.close();
		stmt = this.con.prepareStatement("DELETE FROM peffect where id_peffect > 0");
		stmt.executeUpdate();
		stmt.close();
		stmt = this.con.prepareStatement("DELETE FROM neffect where id_neffect > 0");
		stmt.executeUpdate();
		stmt.close();
	}
	
	private void setTable(String effet) {
		if (effet == "positive") {
			 this.table = "peffect";
		} else if (effet == "negative") {
			 this.table = "neffect";
		} else if (effet == "medical") {
			 this.table = "meffect";
		}
	}

	public List<String> listEffect(String effet) throws SQLException {

		this.setTable(effet);
		PreparedStatement stmt = con.prepareStatement("select name_"+ table +" from " + table);

		ResultSet rs = stmt.executeQuery();

		List<String> listeEffect = new ArrayList<String>();

		while (rs.next()) {
			String name = rs.getString("name_" + table);
			listeEffect.add(name);
		}
		return listeEffect;
	}
	
	/*public static void main(String[] args) throws SQLException {
		ConnectTable test = new ConnectTable();
		Effets test2 = new Effets("medical", "Fatigue",test.getConnection());
		List<String> vite = test2.listEffect();
		for (String string : vite) {
			System.out.println(string);
		}
	}*/
}
