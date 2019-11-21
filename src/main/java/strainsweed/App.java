package strainsweed;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import strainsweed.api.Json;
import strainsweed.jdbc.ConnectTable;
import strainsweed.jdbc.Requetes;

/**
 * Classe d'entree du programme Gere le premier niveau du menu et dirige
 * utilisateur
 * 
 * @author Maureen Camille Florian
 *
 */
public class App {

	/**
	 * Variables
	 */
	static int choix = 0;
	static String nom = "";
	static String name;
	static String effect;
	static Connection con;
	static String plants;

	public static void main(String[] args) throws SQLException, MalformedURLException, IOException {
		ConnectTable connexion = new ConnectTable(); // initialise la connexion a la BDD pour le programme
		Requetes.viderTables(connexion.getConnection()); // vide les tables
		Json json = new Json(connexion.getConnection()); // initialise la class json
		json.jsonAddEffect(); // rempli les tables effets
		json.jsonAddPlants(); // rempli la table plant et les tables liaison
		InputUser inputUser = new InputUser(connexion.getConnection()); // initialise la class InputUser gerant les
																		// sous-menus

		while (choix == 0) {
			System.out.println("\nVeuillez determiner votre choix :\n" + "Selectionnez '1' pour Afficher les Plantes"
					+ "\nSelectionnez '2' pour Afficher les effets" + "\nSelectionnez '3' pour Ajouter une plante"
					+ "\nSelectionnez '4' pour Modifier une plante" + "\nSelectionnez '5' pour Supprimer une plante"
					+ "\nSelectionnez '6' pour Arreter l'application.");
			System.out.print("\nVotre choix : ");

			choix = inputUser.verifieLesChiffres(); // verifie la saisie et retourne le choix
			if (choix == 1) {
				System.out.println("Voici toutes les plantes repertoriees: ");
				inputUser.afficherPlants();
				choix = 0;
			} else if (choix == 2) {
				inputUser.afficherEffets();
				choix = 0;
			} else if (choix == 3) {
				System.out.println("Vous souhaitez ajouter une plante? C'est parti : \n");
				inputUser.ajouterPlantes();
				choix = 0;
			} else if (choix == 4) {
				inputUser.updatePlantes();
				choix = 0;
			} else if (choix == 5) {
				System.out.println("Quelle plante souhaitez vous supprimer? Votre choix est irr�versible.");
				inputUser.deletePlant();
				choix = 0;
			} else if (choix == 6) {
				System.out.println("Fin du programme.");
			} else {
				System.out.println("Mauvais Choix, Merci d'entrer une valeur correcte.");
				choix = 0;
			}
		}
	}
}