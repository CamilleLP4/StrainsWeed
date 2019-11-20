package strainsweed;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import strainsweed.api.Json;
import strainsweed.jdbc.ConnectTable;
import strainsweed.jdbc.Requetes;
import strainsweed.model.Plants;

/**
 * Cette classe va servir � ouvrir un menu pour qu'il est le choix de cherche
 * une plante et de trouver tous ces effets. Ou de chercher un effet
 * 
 * @param args
 */


public class App {

	static int choix = 0;
	static String nom = "";
	static String name;
	static String effect;
	static Connection con;
	static String plants;

	public static void main(String[] args) throws SQLException, MalformedURLException, IOException {
     
    ConnectTable connexion = new ConnectTable();
    Json json = new Json(connexion.getConnection());
    //Plants plant = new Plants(connexion.getConnection());
    Requetes.viderTables(connexion.getConnection());
    json.jsonAddEffect();
    json.jsonAddPlants();
    InputUser inputUser = new InputUser(connexion.getConnection());
    
		while (choix == 0) {
			System.out.println(
					"Veuillez determiner votre choix :\n"
					+ "Selectionnez '1' pour Afficher les Plantes"
					+ "\nSelectionnez '2' pour Afficher les effets"
					+ "\nSelectionnez '3' pour Ajouter une plante"
					+ "\nSelectionnez '4' pour Modifier les effets d'une plante"
					+ "\nSelectionnez '5' pour Supprimer une plante"
					+ "\nSelectionnez '6' pour Afficher une plante"
					+ "\nSelectionnez '7' pour Arreter l'application.");
			System.out.print("\nVotre choix : ");
			choix = inputUser.verifieLesChiffres();
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
				System.out.println("Quelle plante souhaitez vous Modifier? : ");
				//inputUser.updatePlant();

			} else if (choix == 5) {
				System.out.println("Quelle plante souhaitez vous supprimer? Votre choix est irr�versible.");
				//inputUser.deletePlant();
				
			} else if (choix == 6) {
				System.out.println("Quelle plante souhaitez vous voir en d�tail?");
				//inputUser.readPlant();

			} else if (choix == 7) {
				//inputUser.readEffect();

			} else {
				System.out.println("Mauvais Choix, Merci d'entrer une valeur correcte.");
				choix = 0;
			}
		}
		System.out.println("Fin du programme.");
	}
}