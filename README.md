# StrainsWeed

![Logo API](https://cdn.discordapp.com/attachments/636222432503660567/647024950187786240/kisspng-cannabis-ruderalis-leaf-medical-cannabis-clip-art-marijuana-5ab8bd8d654971.09532403152205658.png)

### App

Cette classe d'entrée du programme le menu et dirige l'utilisateur

L'utilisateur a la possibilité de : 
1. Afficher les plantes
2. Afficher les effets
3. Ajouter une plante
4. Modifier une plante
5. Supprimer une plante
6. Arrêter l'application

### InputUser 

Cette classe gére les sous menus et les scanners.

* La méthode verifieLesChiffres() récupère la saisie utilisation et vérifie sa compatibilité
* La méthode ajouterPlantes() récupère et met en forme les saisies utilisations pour créer un objet plante
* La méthode updatePlantes() met à jour une plante
* La méthode afficherEffets() recupère les listes d'effets par type et effectue l'affichage des listes
* La méthode afficherPlants() permet d'afficher la liste des plantes
* La méthode deletePlant() permet de supprimer une plante

## API

### JSON

Cette Classe gère les appels vers l'API, elle traite le retour Json et le met en forme pour l'insérer dans un objet PLANTS
Dans la Classe JSON nous avons plusieurs méthodes : 
* Le Constructeur Json, qui permet la connexion vers la base de données
* La méthode jsonAddEffect() qui permet de récuperer, traiter et envoyer vers la base de données et liste les différents effets.
* La méthode jsonAddPlants(), qui permet de récuperer, traiter et envoyer vers la base de données les différentes plantes avec les informations liées dans un objet plante


## JDBC

### ConnnectTable

Cette classe initialise une connexion vers la base de donnée.

* La méthode ConnectTable() initialise la connexion à la création. 
* La méthode getConnexion() retourne la connexion déjà initialisée.
* La méthode initConnection() initialise la connexion.

 *A savoir que l'URL, l'USER et le PASSWORD peuvent être modifié selon la BDD* 

### Effets

Cette classe gérent les effets dans la base de données

* Le contructeur Effets permet le remplissage des tables effets
* La méthode addEffect() ajoute un effet à la base de données
* La méthode videTable() vide les 3 tables effets.
* La méthode setTable() fait la conversion entre le type d'effet et le nom de la table.
* La méthode listEffect() récupère les effets en fonction du type demandé et retourne la liste des effets

### Requetes

Cette classe comprend les requètes vers la base de données

* La méthode Requetes vide complétement les tables ( Tables de liaison / Effets / Plantes )
* La méthode addDB() ajoute l'objet plante à la base de données.
* La méthode affichePlants() affiche la liste des plantes contenues dans la base de données
* La méthode affichePlantsSimple() affiche le nom, la race et la description de la plante
* La méthode deletePlants() supprime une plante
* La méthode updatePlants() change le nom, la race ou la description d'une plante

### TableLink

Cette classe alimente les tables de liaisons

* La méthode addLink() ajoute un lien entre les effets et les plantes
* La méthode videTable() vide les 3 tables de liaison. 

## MODEL

### Plants

Cette classe gère l'objet plante

* Le constructeur Plants génère le nom / la race / les effets (médical/positif/négatif) et la description
* La méthode getName() retourne le nom
* La méthode getRace() retourne la race
* La méthode getMedical() retourne les effets médicaux
* La méthode getNegative() retourne les effets négatifs
* La méthode getPositive() retourne les effets positifs
* La méthode getDescription() retourne la description

Défauts : 

Avoir une  BDD en local
Le build peut fonctionner si on l'exécute en LOCAL
# MCD
![MCD StrainsWeed](https://github.com/Maureendef/StrainsWeed/blob/master/sql/TPAPI.PNG?raw=true)
# DIAGRAMME
![Diagramme StrainsWeed](https://github.com/Maureendef/StrainsWeed/blob/master/diagram/diagrammedeclasse.PNG?raw=true)
# Java Doc
[Java Doc StrainsWeed](https://maureendef.github.io/StrainsWeed/)
