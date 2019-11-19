package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Cette classe va servir à ouvrir un menu pour qu'il est le choix de cherche
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
					"Veuillez déterminer votre choix :\nSélectionnez '1' pour Afficher la Table Plante\nSélectionnez '2' pour Afficher la Table effet\nSélectionnez '3' pour Ajouter une plante\nSélectionnez '4' pour Modifier une plante\nSélectionnez '5' pour Supprimer une plante\nSélectionnez '6' pour Afficher une plante spéciale\nSélectionnez '7' pour Afficher un effet\nVotre choix : ");
				//choix = inputUser.verifInput();
			System.out.println("Vous avez choisi : " + choix);
			if (choix == 1) {
				System.out.println("Voici toutes les plantes répertoriées: ");
				
				PreparedStatement stmt = con.prepareStatement("select * from plants");
				stmt.setString(1, name);
			} else if (choix == 2) {
				System.out.println("Voici tous les effets repertoriés");
				
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
				System.out.println("Quelle plante souhaitez vous supprimer? Votre choix est irréversible.");
				
				PreparedStatement stmt=con.prepareStatement("delete from plants where id=?");  
				stmt.setInt(1, plants.getId());
			} else if (choix == 3) {
				System.out.println("Quelle plante souhaitez vous voir en détail?");
			
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