package strainsweed.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe alimentant les tables d'associations
 * 
 * @author Maureen Camille Florian
 *
 */
public class TableLink {

	/**
	 * Variables
	 */
	Connection conn;

	/**
	 * Constructeur
	 * 
	 * @param conn la connexion vers la base de donnees
	 */
	public TableLink(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Ajoute un lien entre les effet et les plantes
	 * 
	 * @param effect     effets a lie
	 * @param idPlant    id de la plante a lier
	 * @param typeEffect type d'effet pour le choix de la table
	 * @throws SQLException
	 */
	public void addLink(String effect, int idPlant, String typeEffect) throws SQLException {

		String tableEffect;
		switch (typeEffect) {
		case "medical":
			tableEffect = "meffect";
			break;
		case "positive":
			tableEffect = "peffect";
			break;
		default:
			tableEffect = "neffect";
			break;
		}

		// recuperation de l'id effet
		PreparedStatement stmt = this.conn.prepareStatement(
				"select id_" + tableEffect + " from " + tableEffect + " where name_" + tableEffect + " = ?");
		stmt.setString(1, effect);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			int id = rs.getInt("id_" + tableEffect);
			stmt.close();

			// insertion de l'association
			stmt = this.conn.prepareStatement("INSERT INTO " + typeEffect + " values(?,?)");
			stmt.setInt(1, idPlant);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			stmt.close();
		}

	}

	/**
	 * vide les 3 tables d'association
	 * 
	 * @throws SQLException
	 */
	public void videTable() throws SQLException {
		PreparedStatement stmt = this.conn.prepareStatement("DELETE FROM medical where id_plant > 0");
		stmt.executeUpdate();
		stmt.close();
		stmt = this.conn.prepareStatement("DELETE FROM positive where id_plant > 0");
		stmt.executeUpdate();
		stmt.close();
		stmt = this.conn.prepareStatement("DELETE FROM negative where id_plant > 0");
		stmt.executeUpdate();
		stmt.close();
	}
}
