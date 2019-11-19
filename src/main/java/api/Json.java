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
	
	public void addPlants() throws MalformedURLException, IOException {
		List<String> listName = new ArrayList<String>();
		List<Integer> listId = new ArrayList<Integer>();
		String url = "http://strainapi.evanbusse.com/rOoW6pJ/strains/search/race/hybrid";
		String jsonText = IOUtils.toString(new URL(url), Charset.forName("UTF-8"));

		JSONArray jsonParRace = new JSONArray(jsonText);
		for (Object object : jsonParRace) {
			JSONObject current = (JSONObject) object;
			listName.add((String) current.get("name"));
			listId.add((Integer) current.get("id"));
		}
		url = "http://strainapi.evanbusse.com/rOoW6pJ/strains/search/race/sativa";
		jsonText = IOUtils.toString(new URL(url), Charset.forName("UTF-8"));

		jsonParRace = new JSONArray(jsonText);
		for (Object object : jsonParRace) {
			JSONObject current = (JSONObject) object;
			listName.add((String) current.get("name"));
			listId.add((Integer) current.get("id"));
		}
		
		url = "http://strainapi.evanbusse.com/rOoW6pJ/strains/search/race/indica";
		jsonText = IOUtils.toString(new URL(url), Charset.forName("UTF-8"));

		jsonParRace = new JSONArray(jsonText);
		for (Object object : jsonParRace) {
			JSONObject current = (JSONObject) object;
			listName.add((String) current.get("name"));
			listId.add((Integer) current.get("id"));
		}
		
		
		for (String string : listName) {
			
		}
		
		url = "http://strainapi.evanbusse.com/rOoW6pJ/strains/search/all";
		jsonText = IOUtils.toString(new URL(url), Charset.forName("UTF-8"));

		JSONObject jsonComplet = new JSONObject(jsonText);
		JSONObject test = jsonComplet.getJSONObject(listName.get(0));
		//en cours

		/*for (String string : listName) {
			JSONArray userList = jsonComplet.getJSONArray(string);
			JSONObject current = (JSONObject) userList[0];
			for (Object object : userList) {
				JSONObject current = (JSONObject) object;
				System.out.println(current.getString("html_url"));
				
				/user/followers
				System.out.println(current.getString("followers_url"));
			}
		}*/
		
		
		for (int i = 0; i < listName.size(); i++) {
			url = "http://strainapi.evanbusse.com/rOoW6pJ/strains/data/effects/"+ listId.get(i);
		}
		
		
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
		test.addPlants();
	}

}
