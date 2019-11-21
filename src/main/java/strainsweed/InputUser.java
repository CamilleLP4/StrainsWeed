package strainsweed;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import strainsweed.jdbc.Effets;
import strainsweed.jdbc.Requetes;
import strainsweed.model.Plants;

/**
 * Classe gerant les sous menus et les scanner
 * 
 * @author Maureen Camille Florian
 *
 */
public class InputUser {

	/**
	 * Variables
	 */
	Scanner scan = new Scanner(System.in);
	String choix;
	String name;
	String race;
	List<String> negative = new ArrayList<String>();
	List<String> medical = new ArrayList<String>();
	List<String> positive = new ArrayList<String>();
	Connection conn;

	/**
	 * Constructeur
	 * 
	 * @param conn la connexion a la BDD
	 */
	public InputUser(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Recupere la saisie utilisateur et verifie sa compatibilité
	 * 
	 * @return le choix utilisateur ou 0 en cas d'erreur
	 */
	public int verifieLesChiffres() {
		String str = scan.next();
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			System.out.println("Mauvais choix, Merci d'entree une valeur correcte");
			return 0;
		}
	}

	/**
	 * Recupere et met en forme les saisies utilisateurs pour creer un objet Plants
	 * 
	 * @throws SQLException
	 */
	public void ajouterPlantes() throws SQLException {
		int reponse = 0;
		int effet = 0;
		List<String> list = new ArrayList<String>();
		this.medical.clear(); // vide la liste
		this.positive.clear(); // vide la liste
		this.negative.clear(); // vide la liste
		Effets effets = new Effets(this.conn);
		System.out.println("Veuillez saisir le nom de votre plante :");
		String name = scan.next();
		System.out.println("Veuillez saisir la race :");
		String race = scan.next();
		/*
		 * System.out.println("Veuillez saisir une description :"); String desc =
		 * scan.next();
		 */

		do {
			System.out.println("Voulez-vous ajouter des effets si oui de quel type:");
			System.out.println("1 Effet Medical \n2 Effet Positifs\n3 Effet Negatifs\n4 Pas d'Effet");
			System.out.print("\nVotre choix : ");

			reponse = this.verifieLesChiffres();
			if (reponse == 1) {
				list = effets.listEffect("medical");
				for (int i = 0; i < list.size(); i++) {
					System.out.println((i + 1) + " " + list.get(i));
				}
				System.out.print("\nVotre effet : ");
				effet = this.verifieLesChiffres();
				if (effet != 0) {
					this.medical.add(list.get(effet - 1));
				}
			} else if (reponse == 2) {
				list = effets.listEffect("positive");
				for (int i = 0; i < list.size(); i++) {
					System.out.println((i + 1) + " " + list.get(i));
				}
				System.out.print("\nVotre effet : ");
				effet = this.verifieLesChiffres();
				if (effet != 0) {
					this.positive.add(list.get(effet - 1));
				}
			} else if (reponse == 3) {
				list = effets.listEffect("negative");
				for (int i = 0; i < list.size(); i++) {
					System.out.println((i + 1) + " " + list.get(i));
				}
				System.out.print("\nVotre effet : ");
				effet = this.verifieLesChiffres();
				if (effet != 0) {
					this.negative.add(list.get(effet - 1));
				}
			}
		} while (reponse != 4);
		Plants plante = new Plants(name, race, this.medical, this.negative, this.positive, "");
		Requetes.addDB(plante, conn); // ajoute l'objet Plants a la BDD
	}

	/**
	 * Met a jour une plante
	 * 
	 * @throws SQLException
	 */
	public void updatePlantes() throws SQLException {
		int reponse;
		String race = "";
		String description = "";
		String newname = "";
		System.out.println("Veuillez saisir le nom de la plante :");
		String name = scan.next();
		if (Requetes.affichePlantsSimple(conn, name)) {
			System.out.println("Que voulez-vous changer? (un choix possible)");
			System.out.println(
					"1 Nom de la plante \n2 Race de la plante\n3 Description de la plante\n4 Aucun changement");
			System.out.print("\nVotre choix : ");

			reponse = this.verifieLesChiffres();
			if (reponse == 1) {
				System.out.println("Nouveau nom :");
				newname = scan.next();
			} else if (reponse == 2) {
				System.out.println("Nouvelle race :");
				race = scan.next();
			} else if (reponse == 3) {
				System.out.println("Nouvelle description :");
				description = scan.next();
			}
			Requetes.updatePlants(name, newname, race, description, this.conn);
		}

	}

	/**
	 * Recupere les list d'effet par type et effectue l'affichage des listes
	 * 
	 * @throws SQLException
	 */
	public void afficherEffets() throws SQLException {
		int reponse = 0;
		List<String> list = new ArrayList<String>();
		Effets effets = new Effets(this.conn);
		do {
			System.out.println(
					"\nQue voulez-vous afficher comme effets :\n- 1 Les effets Medicaux\n- 2 Les effets Positifs\n- 3 Les effets Negatifs\n- 4 Quitter");
			System.out.print("\nVotre choix : ");

			reponse = this.verifieLesChiffres();
			if (reponse == 1) {
				list = effets.listEffect("medical");
				for (String string : list) {
					System.out.println(string);
				}
			} else if (reponse == 2) {
				list = effets.listEffect("positive");
				for (String string : list) {
					System.out.println(string);
				}
			} else if (reponse == 3) {
				list = effets.listEffect("negative");
				for (String string : list) {
					System.out.println(string);
				}
			}
		} while (reponse != 4);
	}

	/**
	 * Affiche la liste des plantes
	 * 
	 * @throws SQLException
	 */
	public void afficherPlants() throws SQLException {
		Requetes.affichePlants(this.conn);
	}

	/**
	 * Supprime une plante
	 * 
	 * @throws SQLException
	 */
	public void deletePlant() throws SQLException {
		String str = scan.next();
		Requetes.deletePlants(str, this.conn);
	}
}
