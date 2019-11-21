package strainsweed.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import strainsweed.jdbc.Effets;
import strainsweed.jdbc.Requetes;
import strainsweed.model.Plants;

/**
 * Classe gerant les appel vers l'api Elle traite le retour json et le met en
 * forme pour l'inserer dans un objet Plants
 * 
 * @author Maureen Camille Florian
 *
 */
public class Json {

	/**
	 * Variables
	 */
	private Connection connexion;

	/**
	 * Construction
	 * 
	 * @param conn la connexion vers la base de donnees
	 */
	public Json(Connection conn) {
		this.connexion = conn;
	}

	/**
	 * Cette methode permet de recuperer, traiter et envoyer vers la base de donnees
	 * les liste des differents effets
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SQLException
	 */
	public void jsonAddEffect() throws MalformedURLException, IOException, SQLException {

		List<String> medical = new ArrayList<String>();
		List<String> positive = new ArrayList<String>();
		List<String> negative = new ArrayList<String>();
		String url = "http://strainapi.evanbusse.com/rOoW6pJ/searchdata/effects";
		String jsonText = IOUtils.toString(new URL(url), Charset.forName("UTF-8"));
		JSONArray jsonComplet = new JSONArray(jsonText);

		for (Object objet : jsonComplet) {
			JSONObject current = (JSONObject) objet;
			if (current.get("type").equals("medical")) {
				medical.add((String) current.get("effect"));
			}
			if (current.get("type").equals("positive")) {
				positive.add((String) current.get("effect"));
			}
			if (current.get("type").equals("negative")) {
				negative.add((String) current.get("effect"));
			}
		}

		new Effets(this.connexion, negative, "negative");
		new Effets(this.connexion, positive, "positive");
		new Effets(this.connexion, medical, "medical");
	}

	/**
	 * Cette methode permet de recuperer, traiter et envoyer vers la base de donnees
	 * les differentes plantes avec les informations liee dans un object plante
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SQLException
	 */
	public void jsonAddPlants() throws MalformedURLException, IOException, SQLException {
		List<String> medical = new ArrayList<String>();
		List<String> positive = new ArrayList<String>();
		List<String> negative = new ArrayList<String>();
		int compteur = 0; // pour limiter le travail JSON
		String url = "http://strainapi.evanbusse.com/rOoW6pJ/strains/search/all";
		String jsonText = IOUtils.toString(new URL(url), Charset.forName("UTF-8"));
		JSONObject jsonComplet = new JSONObject(jsonText);
		String[] name = JSONObject.getNames(jsonComplet); // recupere la liste des objets

		for (String nom : name) {
			medical.clear();
			positive.clear(); // vide les liste
			negative.clear();
			JSONObject plant = jsonComplet.getJSONObject(nom);
			String race = plant.getString("race");
			JSONObject effet = plant.getJSONObject("effects");
			JSONArray listPositive = effet.getJSONArray("positive");
			for (Object object : listPositive) {
				String current = (String) object;
				positive.add(current); // rempli la liste avecc les effets liee
			}
			JSONArray listNegative = effet.getJSONArray("negative");
			for (Object object : listNegative) {
				String current = (String) object;
				negative.add(current); // rempli la liste avecc les effets liee
			}
			JSONArray listMedical = effet.getJSONArray("medical");
			for (Object object : listMedical) {
				String current = (String) object;
				medical.add(current); // rempli la liste avecc les effets liee
			}
			Plants plante = new Plants(nom, race, medical, negative, positive, "");
			Requetes.addDB(plante, connexion); // ajoute l'objet a la base de donnees
			System.out.print("-");
			if (compteur >= 30) { // pour limiter le travail JSON
				break; // pour limiter le travail JSON
			}
			compteur++; // pour limiter le travail JSON
		}
	}
}
