package strainsweed.model;

import java.util.List;

/**
 * Classe gerant l'objet Plants
 * 
 * @author Maureen Camille Florian
 *
 */
public class Plants {

	/**
	 * Variables
	 */
	private String name;
	private String race;
	private List<String> medical;
	private List<String> negative;
	private List<String> positive;
	private String description;

	/**
	 * Constructeur de l'objet Plants
	 * 
	 * @param name nom de la plante
	 * @param race espece de la plante
	 * @param m    list de chaine de caractere d'effet medicaux
	 * @param n    list de chaine de caractere d'effet negatif
	 * @param p    list de chaine de caractere d'effet positif
	 * @param desc la description
	 */
	public Plants(String name, String race, List<String> m, List<String> n, List<String> p, String desc) {
		this.name = name;
		this.race = race;
		this.medical = m;
		this.negative = n;
		this.positive = p;
		this.description = desc;
	}

	/**
	 * Retourne le nom
	 * 
	 * @return Retourne le nom
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retourne la race
	 * 
	 * @return Retourne la race
	 */
	public String getRace() {
		return race;
	}

	/**
	 * Retourne les effets medicaux
	 * 
	 * @return Retourne les effets medicaux
	 */
	public List<String> getMedical() {
		return medical;
	}

	/**
	 * Retourne les effets negatifs
	 * 
	 * @return Retourne les effets negatifs
	 */
	public List<String> getNegative() {
		return negative;
	}

	/**
	 * Retourne les effets positifs
	 * 
	 * @return Retourne les effets positifs
	 */
	public List<String> getPositive() {
		return positive;
	}

	/**
	 * Retourne la description
	 * 
	 * @return Retourne la description
	 */
	public String getDescription() {
		return description;
	}
}
