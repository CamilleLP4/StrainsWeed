package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Scanner;

public class InputUser {
	
 Scanner scan = new Scanner(System.in);
 String choix;
 String name;
 String race;
 List<String> n;
 List<String> m;
 List<String> p;
 Connection conn;

	public InputUser(Scanner scan) {
		choix = scan.nextLine();
		
	}
	
	public int verifieLesChiffres(Scanner scan, String str) {
		
		try {
            return Integer.parseInt(choix);
        } catch (NumberFormatException e) {
            System.out.println("Mauvais choix, Merci d'entree une valeur correcte");
            return 0;
        }
	}
	
	public void ajouterPlantes(String name, String race, List<String> m, List<String> n, List<String> p, String desc, Connection conn) {

		PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + plant + " VALUES (?,?,?,?,?)");
		
		stmt.setString(1, name.toString());
		stmt.setString(2, race.toString());
		stmt.setString(3, m.toString());
		stmt.setString(4, n.toString());
		stmt.setString(5, p.toString());
		
		stmt.execute();
		
		
	}
	
	
	

	
	
	
	
}
