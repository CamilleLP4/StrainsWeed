package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Plants {
	
	private String name;
	private String race;
	private List<String> medical;
	private List<String> negative;
	private List<String> positive;
	private String description;
	private Connection connexion;
	
	public Plants(String name, String race, List<String> m, List<String> n, List<String> p, String desc, Connection conn) {
		this.name = name;
		this.race = race;
		this.medical = m;
		this.negative = n;
		this.positive = p;
		this.description = desc;
		this.connexion = conn;
	}
	
	public Plants(Connection conn, List<String> effets, String typeEffets) throws SQLException {
		Effets effet = new Effets(conn);
		for (String string : effets) {
			effet.addEffect(typeEffets, string);
		}
	}
	
	public void addDB() throws SQLException {
		int idPlant = 0;
		PreparedStatement stmt = this.connexion.prepareStatement("INSERT INTO plant (nom_plant, race_plant, description_plant) values(?,?,?)");
		stmt.setString(1, this.name);
		stmt.setString(2, this.race);
		stmt.setString(3, this.description);
		System.out.println(stmt); //test
		stmt.executeUpdate();
		stmt.close();
		stmt = this.connexion.prepareStatement("select id_plant from plant where nom_plant = ?");
		stmt.setString(1, this.name);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
		idPlant = rs.getInt("id_plant");
		System.out.println(idPlant); //test
		}
		stmt.close();
		TableLink liaison = new TableLink(this.connexion);
		if(!(this.medical.isEmpty())) {
			String typeEffet = "medical";
			for (String effet : medical) {
				liaison.addLink(effet, idPlant, typeEffet);
			}
			
		}
		if(!(this.negative.isEmpty())) {
			String typeEffet = "negative";
			for (String effet : negative) {
				liaison.addLink(effet, idPlant, typeEffet);
			}
			
		}
		if(!(this.positive.isEmpty())) {
			String typeEffet = "positive";
			for (String effet : positive) {
				liaison.addLink(effet, idPlant, typeEffet);
			}
			
		}
	}
	
	public static void main(String[] args) throws SQLException {
		ConnectTable test = new ConnectTable();
		List<String> stest = List.of("Pain","Depression");
		//Plants test2 = new Plants(test.getConnection(), stest, "medical");
		
	}
}
