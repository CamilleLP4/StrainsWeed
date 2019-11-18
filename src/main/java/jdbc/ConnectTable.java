package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Initialise une connexion vers la base à partir des informations se trouvant dans la classe Parametres.
 * @author Florian & Maureen   RPZ la street
 *
 */
public class ConnectTable {

	private Connection connection;
	
	private static String password = "root"; 
    private static String user = "root";
    private static String url = "jdbc:mysql://localhost:3306/simplon?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";

	/**
	 * Initialise la connection à la création.
	 */
	public ConnectTable() {
		try {
			initConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retourne la connection déjà initialisée.
	 * @return la connection déjà initialisée.
	 */
	public Connection getConnection() {
		return this.connection;
	}

	/**
	 * Initialise la connection.
	 * 
	 * @throws SQLException
	 */
	private void initConnection() throws SQLException {
		this.connection = DriverManager.getConnection(url, user, password);
	}
	
	
	
}
