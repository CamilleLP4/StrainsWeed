package strainsweed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import strainsweed.jdbc.Effets;
import strainsweed.jdbc.Requetes;
import strainsweed.model.Plants;


 public class InputUser {
	
 Scanner scan = new Scanner(System.in);
 String choix;
 String name;
 String race;
 List<String> negative = new ArrayList<String>();
 List<String> medical = new ArrayList<String>();
 List<String> positive = new ArrayList<String>();
 Connection conn;

	public InputUser(Connection conn) {
		this.conn = conn;
	}
	
	public int verifieLesChiffres() {
		String str = scan.next();
		try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            System.out.println("Mauvais choix, Merci d'entree une valeur correcte");
            return 0;
        }
	}
	
	public void ajouterPlantes() throws SQLException {
		int reponse = 0;
		int effet = 0;
		List<String> list = new ArrayList<String>();
		this.medical.clear();
		this.positive.clear();
		this.negative.clear();
		Effets effets = new Effets(this.conn);
		System.out.println("Veuillez saisir le nom de votre plante :");
		String name = scan.next();
		System.out.println("Veuillez saisir la race :");
		String race = scan.next();
		/*System.out.println("Veuillez saisir une description :");
		String desc = scan.next();*/
		
		do {
			System.out.println("Voulez-vous ajouter des effets si oui de quel type:");
			System.out.println(
					"1 Effet Medical \n2 Effet Positifs\n3 Effet Negatifs\n4 Pas d'Effet");
			System.out.print("\nVotre choix : ");
			reponse = this.verifieLesChiffres();
			if (reponse == 1) {
				list = effets.listEffect("medical");
				for (int i = 0; i < list.size(); i++) {
					System.out.println((i+1) + " " + list.get(i));
				}
				System.out.print("\nVotre effet : ");
				effet = this.verifieLesChiffres();
				if (effet != 0) {
					this.medical.add(list.get(effet-1));
				}
			} else if (reponse == 2) {
				list = effets.listEffect("positive");
				for (int i = 0; i < list.size(); i++) {
					System.out.println((i+1) + " " + list.get(i));
				}
				System.out.print("\nVotre effet : ");
				if (effet != 0) {
					this.positive.add(list.get(effet-1));
				}
			} else if (reponse == 3) {
				list = effets.listEffect("negative");
				for (int i = 0; i < list.size(); i++) {
					System.out.println((i+1) + " " + list.get(i));
				}
				System.out.print("\nVotre effet : ");
				if (effet != 0) {
					this.negative.add(list.get(effet-1));
				}
			}
		} while (reponse != 4);
		Plants plante = new Plants(name, race, this.medical, this.negative, this.positive, "",this.conn);
		Requetes.addDB(plante, conn);
		}
	
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
	
	public void afficherPlants() throws SQLException {
		Requetes.affichePlants(conn);
	}

}
