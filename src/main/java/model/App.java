package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

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

	public static void main(String[] args) throws SQLException {
    InputUser inputUser = new InputUser(); 
    
		while (choix == 0) {
			System.out.println(
					"Veuillez d�terminer votre choix :\nS�lectionnez '1' pour Afficher la Table Plante\nS�lectionnez '2' pour Afficher la Table effet\nS�lectionnez '3' pour Ajouter une plante\nS�lectionnez '4' pour Modifier une plante\nS�lectionnez '5' pour Supprimer une plante\nS�lectionnez '6' pour Afficher une plante sp�ciale\nS�lectionnez '7' pour Afficher un effet\nVotre choix : ");
				//choix = inputUser.verifInput();
			System.out.println("Vous avez choisi : " + choix);
			if (choix == 1) {
				System.out.println("Voici toutes les plantes r�pertori�es: ");
				
				PreparedStatement stmt = con.prepareStatement("select * from plants");
				stmt.setString(1, name);
			} else if (choix == 2) {
				System.out.println("Voici tous les effets repertori�s");
				
				PreparedStatement stmt = con.prepareStatement("select * from meffect,peffect,neffect");
				stmt.setString(1, effect);
			} else if (choix == 3) {
				System.out.println("Vous souhaitez ajouter une plante? C'est parti : ");
				
				PreparedStatement stmt=con.prepareStatement("insert into * from plant values (?,?,?,?)");  
				stmt.setInt(1, plants.getId());
				stmt.setString(3, plants.getName());
				stmt.setString(2, plants.getRace());
				stmt.setString(4, plants.getEffects());

			} else if (choix == 4) {
				System.out.println("Quelle plante souhaitez vous Modifier? : ");
				
				PreparedStatement stmt=con.prepareStatement("update * from plants where name=?,race=?,meffect=?,peffect=?,neffect=?");  
				stmt.setString("","","","","");
			} else if (choix == 5) {
				System.out.println("Quelle plante souhaitez vous supprimer? Votre choix est irr�versible.");
				
				PreparedStatement stmt=con.prepareStatement("delete from plants where id=?");  
				stmt.setInt(1, plants.getId());
			} else if (choix == 3) {
				System.out.println("Quelle plante souhaitez vous voir en d�tail?");
			
				PreparedStatement stmt = con.prepareStatement("select * from plants where name = ?");
				stmt.setString(1, name);
			} else if (choix == 3) {
				System.out.println("Quelle effet souhaitez vous voir? ");
			
				
				PreparedStatement stmt = con.prepareStatement("select * from meffect,peffect,neffect where effect = ?");
				stmt.setString(1, effect);

			} else {
				System.out.println("Erreur");
				choix = 0;
			}
		}

	}
}