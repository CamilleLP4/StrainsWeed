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

	public Effets(String effet, String name, Connection con) {
		this.effet = effet;
		this.name = name;
		this.con = con;
		this.addEffect();
	}

	public void addEffect() {
		// effet + id
	}

	public List<String> listEffect() throws SQLException {

		if (this.effet == "positive") {
			 this.table = "peffect";
		} else if (this.effet == "negative") {
			 this.table = "neffect";
		} else if (this.effet == "medical") {
			 this.table = "meffect";
		}

		PreparedStatement stmt = con.prepareStatement("select name_"+ table +" from " + table);

		ResultSet rs = stmt.executeQuery();

		List<String> listeEffect = new ArrayList<String>();

		while (rs.next()) {
			String name = rs.getString("name_" + table);
			listeEffect.add(name);
		}
		return listeEffect;
	}
}
