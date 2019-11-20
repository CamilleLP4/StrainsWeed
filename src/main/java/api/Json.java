package api;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
import org.json.JSONException;
import org.json.JSONObject;

import jdbc.ConnectTable;
import jdbc.Plants;

public class Json {
	
	private static final String jsonFileName = "sample.json";
	private Connection connexion;
	
	public Json () {
		ConnectTable test = new ConnectTable();
		this.connexion = test.getConnection();
	}
	
	public void jsonAddEffect() throws MalformedURLException, IOException, SQLException {
		
		List<String> medical = new ArrayList<String>();
		List<String> positive = new ArrayList<String>();
		List<String> negative = new ArrayList<String>();
		String url = "http://strainapi.evanbusse.com/rOoW6pJ/searchdata/effects";

		String jsonText = IOUtils.toString(new URL(url), Charset.forName("UTF-8"));
		
		writeJson(jsonText);
		
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
		new Plants(this.connexion, negative, "negative");
		new Plants(this.connexion, positive, "positive");
		new Plants(this.connexion, medical, "medical");	
	}
	
	public void jsonAddPlants() throws MalformedURLException, IOException, SQLException {
		List<String> medical = new ArrayList<String>();
		List<String> positive = new ArrayList<String>();
		List<String> negative = new ArrayList<String>();
		int compteur = 0;
		String url = "http://strainapi.evanbusse.com/rOoW6pJ/strains/search/all";
		String jsonText = IOUtils.toString(new URL(url), Charset.forName("UTF-8"));

		JSONObject jsonComplet = new JSONObject(jsonText);
		String[] name = JSONObject.getNames(jsonComplet);
		for (String nom : name) {
			medical.clear();
			positive.clear();
			negative.clear();
			JSONObject plant = jsonComplet.getJSONObject(nom);
			String race = plant.getString("race");
			JSONObject effet = plant.getJSONObject("effects");
			JSONArray listPositive = effet.getJSONArray("positive");
			for (Object object : listPositive) {
				String current = (String) object;
				positive.add(current);					
			}
			JSONArray listNegative = effet.getJSONArray("negative");
			for (Object object : listNegative) {
				String current = (String) object;
				negative.add(current);					
			}
			JSONArray listMedical = effet.getJSONArray("medical");
			for (Object object : listMedical) {
				String current = (String) object;
				medical.add(current);					
			}
			Plants plante = new Plants(nom, race, medical, negative, positive, "",this.connexion);
			plante.addDB();
			
			if(compteur >= 30) {
				break;
			}
			compteur++;
		}
		System.out.println("fin");
	}
	
	public static void writeJson(String jsonText) {
		BufferedWriter bw;

		try {
			bw = new BufferedWriter(new FileWriter(jsonFileName));
			bw.write(jsonText);
			bw.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) throws MalformedURLException, IOException, SQLException {
		Json test = new Json();
		test.jsonAddEffect();
		test.jsonAddPlants();
	}

}
