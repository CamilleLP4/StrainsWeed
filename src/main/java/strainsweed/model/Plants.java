package strainsweed.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import strainsweed.jdbc.ConnectTable;
import strainsweed.jdbc.Effets;
import strainsweed.jdbc.TableLink;

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
	
	public Plants(Connection conn) {
		this.connexion = conn;
	}
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public List<String> getMedical() {
		return medical;
	}

	public void setMedical(List<String> medical) {
		this.medical = medical;
	}

	public List<String> getNegative() {
		return negative;
	}

	public void setNegative(List<String> negative) {
		this.negative = negative;
	}

	public List<String> getPositive() {
		return positive;
	}

	public void setPositive(List<String> positive) {
		this.positive = positive;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
