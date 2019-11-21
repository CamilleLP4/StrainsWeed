package strainsweed.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import strainsweed.model.Plants;

/**
 * Classe comprenant les requetes vers la base de donnees
 * 
 * @author Maureen Camille Florian
 *
 */
public class Requetes {

	/**
	 * Methode vidant completement les tables
	 * 
	 * @param connexion la connexion vers la base de donnees
	 * @throws SQLException
	 */
	static public void viderTables(Connection connexion) throws SQLException {
		TableLink supprlink = new TableLink(connexion);
		supprlink.videTable(); // vide les tables de liaison avant pour supprimer les FK
		Effets supprEffet = new Effets(connexion);
		supprEffet.videTable(); // vide les tables d'effets
		PreparedStatement stmt = connexion.prepareStatement("DELETE FROM plant where id_plant > 0");
		stmt.executeUpdate(); // vide la table plant
		stmt.close();
	}

	/**
	 * Methode ajoutant l'objet Plants a la BDD
	 * 
	 * @param plant     objet a ajouté dans la BDD
	 * @param connexion la connexion vers la base de donnees
	 * @throws SQLException
	 */
	static public void addDB(Plants plant, Connection connexion) throws SQLException {
		int idPlant = 0;
		// preparation de l'insertion dans plant
		PreparedStatement stmt = connexion
				.prepareStatement("INSERT INTO plant (nom_plant, race_plant, description_plant) values(?,?,?)");
		stmt.setString(1, plant.getName());
		stmt.setString(2, plant.getRace());
		stmt.setString(3, plant.getDescription());
		stmt.executeUpdate(); // insertion
		stmt.close();
		// preparation de la recuperation de l'id auto
		stmt = connexion.prepareStatement("select id_plant from plant where nom_plant = ?");
		stmt.setString(1, plant.getName());
		ResultSet rs = stmt.executeQuery(); // recuperation
		if (rs.next()) {
			idPlant = rs.getInt("id_plant"); // extraction de l'id de la plante dans la BDD
		}
		stmt.close();

		TableLink liaison = new TableLink(connexion);
		if (!(plant.getMedical().isEmpty())) {
			String typeEffet = "medical";
			for (String effet : plant.getMedical()) {
				liaison.addLink(effet, idPlant, typeEffet); // envoi des infos pour alimenté la table de liaison
			}

		}
		if (!(plant.getNegative().isEmpty())) {
			String typeEffet = "negative";
			for (String effet : plant.getNegative()) {
				liaison.addLink(effet, idPlant, typeEffet); // envoi des infos pour alimenté la table de liaison
			}

		}
		if (!(plant.getPositive().isEmpty())) {
			String typeEffet = "positive";
			for (String effet : plant.getPositive()) {
				liaison.addLink(effet, idPlant, typeEffet); // envoi des infos pour alimenté la table de liaison
			}

		}
	}

	/**
	 * Methode affichant la liste des plantes contenu dans la BDD
	 * 
	 * @param connexion la connexion vers la base de donnees
	 * @throws SQLException
	 */
	static public void affichePlants(Connection connexion) throws SQLException {
		PreparedStatement stmt = connexion.prepareStatement("select nom_plant, id_plant, description_plant FROM plant");
		ResultSet rs = stmt.executeQuery(); // recupere les plantes
		while (rs.next()) {
			String name = rs.getString("nom_plant"); // extrait le nom
			System.out.print("\n\n" + name);
			String description = rs.getString("description_plant"); // extrait la description
			System.out.print("\t" + description);
			int id = rs.getInt("id_plant"); // extrait l'id

			// utilise l'id pour recuperer les effets grace au table de liaison
			PreparedStatement link = connexion
					.prepareStatement("select id_meffect FROM medical Where id_plant = " + id);
			ResultSet rswhere = link.executeQuery();
			System.out.print("\n___________________________________________________________\nMedical\n");
			while (rswhere.next()) {
				int idEffet = rswhere.getInt("id_meffect");
				PreparedStatement effet = connexion
						.prepareStatement("select name_meffect FROM meffect Where id_meffect = " + idEffet);
				ResultSet rsfinal = effet.executeQuery();
				// System.out.println(rsfinal.getFetchSize());
				if (rsfinal.next()) {
					System.out.print("\t" + rsfinal.getString("name_meffect")); // affiche les effets
				}
			}
			rswhere.close();

			// utilise l'id pour recuperer les effets grace au table de liaison
			link = connexion.prepareStatement("select id_peffect FROM positive Where id_plant = " + id);
			rswhere = link.executeQuery();
			System.out.print("\n___________________________________________________________\nPositif\n");
			while (rswhere.next()) {
				int idEffet = rswhere.getInt("id_peffect");
				PreparedStatement effet = connexion
						.prepareStatement("select name_peffect FROM peffect Where id_peffect = " + idEffet);
				ResultSet rsfinal = effet.executeQuery();
				if (rsfinal.next()) {
					System.out.print("\t" + rsfinal.getString("name_peffect")); // affiche les effets
				}
			}
			rswhere.close();

			// utilise l'id pour recuperer les effets grace au table de liaison
			link = connexion.prepareStatement("select id_neffect FROM negative Where id_plant = " + id);
			rswhere = link.executeQuery();
			System.out.print("\n___________________________________________________________\nNegatif\n");
			while (rswhere.next()) {
				int idEffet = rswhere.getInt("id_neffect");
				PreparedStatement effet = connexion
						.prepareStatement("select name_neffect FROM neffect Where id_neffect = " + idEffet);
				ResultSet rsfinal = effet.executeQuery();
				if (rsfinal.next()) {
					System.out.print("\t" + rsfinal.getString("name_neffect")); // affiche les effets
				}
			}
			rswhere.close();
		}
	}

	/**
	 * affiche le nom la race et la description de la plant
	 * 
	 * @param connexion la connexion vers la BDD
	 * @param nom       le nom de la plante a afficher
	 * @return retourne true si la plante est dans la table
	 * @throws SQLException
	 */
	static public boolean affichePlantsSimple(Connection connexion, String nom) throws SQLException {
		PreparedStatement stmt = connexion
				.prepareStatement("select nom_plant, race_plant, description_plant FROM plant where nom_plant = ?");
		stmt.setString(1, nom);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			String name = rs.getString("nom_plant"); // extrait le nom
			System.out.println("Nom : " + name);
			String race = rs.getString("race_plant"); // extrait la race
			System.out.println("Race : " + race);
			String description = rs.getString("description_plant"); // extrait la description
			System.out.println("Description : " + description);
			return true;
		} else {
			System.out.println("Cette plante n'existe pas dans la base");
			return false;
		}
	}

	/**
	 * Supprime une plante
	 * 
	 * @param name      nom de la plante a supprimer
	 * @param connexion la connexion a la BDD
	 * @throws SQLException
	 */
	static public void deletePlants(String name, Connection connexion) throws SQLException {
		int id = 0;
		PreparedStatement stmt = connexion.prepareStatement("select id_plant FROM plant where nom_plant = ?");
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			id = rs.getInt("id_plant");
		}
		stmt.close();
		stmt = connexion.prepareStatement("delete FROM medical where id_plant = ?");
		stmt.setInt(1, id);
		stmt.executeUpdate();
		stmt.close();
		stmt = connexion.prepareStatement("delete FROM positive where id_plant = ?");
		stmt.setInt(1, id);
		stmt.executeUpdate();
		stmt.close();
		stmt = connexion.prepareStatement("delete FROM negative where id_plant = ?");
		stmt.setInt(1, id);
		stmt.executeUpdate();
		stmt.close();
		stmt = connexion.prepareStatement("delete FROM plant where id_plant = ?");
		stmt.setInt(1, id);
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * change le nom la race ou la description d'un plante
	 * 
	 * @param name        le nom de la plante
	 * @param newname     le nouveau de la plante
	 * @param race        la nouvelle race de la plante
	 * @param description la nouvelle description de la plante
	 * @param connexion   la connexion vers la BDD
	 * @throws SQLException
	 */
	public static void updatePlants(String name, String newname, String race, String description, Connection connexion)
			throws SQLException {
		String raceUpdate;
		String descriptionUpdate;
		String nameUpdate;
		System.out.println(newname);
		PreparedStatement stmt = connexion
				.prepareStatement("select nom_plant, race_plant, description_plant FROM plant where nom_plant = ?");
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery(); // recupere les plantes
		if (rs.next()) {
			if (newname != "") {
				nameUpdate = newname;
			} else {
				nameUpdate = rs.getString("nom_plant");
			}
			if (description != "") {
				descriptionUpdate = description;
			} else {
				descriptionUpdate = rs.getString("description_plant");
			}
			if (race != "") {
				raceUpdate = race;
			} else {
				raceUpdate = rs.getString("race_plant");
			}
			stmt.close();
			stmt = connexion.prepareStatement(
					"UPDATE plant SET nom_plant = ?, race_plant = ?, description_plant = ? WHERE nom_plant = ?");
			stmt.setString(1, nameUpdate);
			stmt.setString(2, raceUpdate);
			stmt.setString(3, descriptionUpdate);
			stmt.setString(4, name);
			stmt.executeUpdate();
			stmt.close();
		}

	}
}
