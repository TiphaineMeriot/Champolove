import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Modele {
	//Déclaration des Structures utilisées
	public ArrayList<String> qualite;
	public ArrayList<String> defaut;
	public ArrayList<String> hobbies;
	public ArrayList<String> prenomH;
	public ArrayList<String> prenomF;
	public ArrayList<String> nom;
	public ArrayList<Donnees> lieu;
    public TreeSet<Profil> listeProfil;
	public HashMap<String,TreeSet<Profil>> tripargenre; //Autre organisation des profils, utile dans Matching
	public ArrayList<File> listeImageH;
	public ArrayList<File> listeImageF;
	///

	//Cette méthode est indispensable pour pouvoir sérialiser le comparateur.
	//Elle fait la même chose qu'un comparateur classique à part qu'il faut dire chaque étape de la comparaison
	//Rien n'est implicite
	private static class SerializableComparator implements Comparator<Profil>, Serializable {
        @Override
        public int compare(Profil p1, Profil p2) {
            int result = p1.date_de_creation.compareTo(p2.date_de_creation);
            if (result == 0) {
                result = p1.nom.compareTo(p2.nom);
				if(result==0){
					result=p1.prenom.compareTo(p2.prenom);
				}
            }
            return result;
        }
    }
	///

	//Cette méthode permet de stocker le nom de la ville mais également la latitude et la longitude dans une seule liste
	public static class Donnees implements Serializable{
		String location;
		double latitude,longitude;
		public Donnees(String loc,double lat,double longi){
			this.location=loc;
			this.latitude=lat;
			this.longitude=longi;
		}
	}
	///

	///Constructeur de modèle qui instancie les objets mis en jeu.
    public Modele() {
		Comparator<Profil> ddc= new SerializableComparator(); // Compare en fonction de la date de création
        this.listeProfil=new TreeSet<>(ddc);
		this.qualite=new ArrayList<>();
		this.defaut=new ArrayList<>();
		this.hobbies=new ArrayList<>();
		this.nom=new ArrayList<>();
		this.prenomH=new ArrayList<>();
		this.prenomF=new ArrayList<>();
		this.lieu=new ArrayList<>();
		this.tripargenre=new HashMap<>();
		Path relativePathH= Paths.get("src","images","HOMME");
		Path relativePathF= Paths.get("src","images","FEMME");
		Path absolutePathH=relativePathH.toAbsolutePath();
		Path absolutePathF=relativePathF.toAbsolutePath();
		File dirF=new File(absolutePathF.toString());
		File dirH=new File(absolutePathH.toString());
		this.listeImageH=new ArrayList<>(dirH.listFiles().length);
		this.listeImageF=new ArrayList<>(dirF.listFiles().length);
		this.listeImageF.addAll(List.of(dirF.listFiles()));
		this.listeImageH.addAll(List.of(dirH.listFiles()));
		System.out.println(this.listeImageH.size());
		System.out.println(this.listeImageF.size());
		csv_transform(); //TODO faire ça que quand on a pas de .dat (sinon c'est inutile)
    }
	///

	//Cette méthode permet de généraliser l'enregistrement des données pour avoir un code plus épuré (il factorise en gros)
	public void aux_enregistrer(String chemin, Object liste) throws IOException {
		FileOutputStream fos = new FileOutputStream(chemin);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(liste);
		oos.close();
		fos.close();
	}
	///

	//Méthode permettant la sérialisation des données dans un fichier .dat
    public void enregistrer() {
		try {
			aux_enregistrer("donnees/profil.dat",this.listeProfil);
			aux_enregistrer("donnees/qualite.dat",this.qualite);
			aux_enregistrer("donnees/defaut.dat",this.defaut);
			aux_enregistrer("donnees/hobbies.dat",this.hobbies);
			aux_enregistrer("donnees/prenomH.dat",this.prenomH);
			aux_enregistrer("donnees/prenomF.dat",this.prenomF);
			aux_enregistrer("donnees/nom.dat",this.nom);
			aux_enregistrer("donnees/ville.dat",this.lieu);
		} catch (IOException e) {
			throw new RuntimeException("Impossible d'écrire les données");
		}
	}
	///

	//Cette méthode permet de généraliser le chargement des données pour avoir un code plus épuré (il factorise en gros)
	//T est un terme générique servant à récupérer le type de l'objet en question. C'est très pratique!
	@SuppressWarnings("unchecked")
	public <T> T aux_charger(String chemin) throws Exception {
		FileInputStream fis = new FileInputStream(chemin);
		ObjectInputStream ois = new ObjectInputStream(fis);
		T liste = (T) ois.readObject();
		ois.close();
		fis.close();
		return liste;
	}
	///

	//Méthode permettant la récupération des données sérialisées dans un fichier .dat
	public void charger() {
		try {
			this.listeProfil = aux_charger("donnees/profil.dat");
			this.qualite = aux_charger("donnees/qualite.dat");
			this.defaut = aux_charger("donnees/defaut.dat");
			this.hobbies = aux_charger("donnees/hobbies.dat");
			this.prenomH = aux_charger("donnees/prenomH.dat");
			this.prenomF = aux_charger("donnees/prenomF.dat");
			this.nom = aux_charger("donnees/nom.dat");
			this.lieu=aux_charger("donnees/ville.dat");
		} catch (Exception e) {
			throw new RuntimeException("Lecture des données impossibles ou données corrompues");
		}
	}
	///

	//Méthode permettant la transformation d'un fichier csv en une structure de données appropriées (que des listes)
	public void csv_transform(){
		String chemqual="donnees/qualites.csv";
		String chemdef="donnees/defaut.csv";
		String chemhobbies="donnees/hobbies.csv";
		String chemprenomF="donnees/prenomF.csv";
		String chemprenomH="donnees/prenomH.csv";
		String chemnom="donnees/nom.csv";
		String chemville="donnees/villes_france.csv";
		aux_csv_transform(chemqual, this.qualite);
		aux_csv_transform(chemdef, this.defaut);
		aux_csv_transform(chemhobbies,this.hobbies);
		aux_csv_transform(chemprenomF,this.prenomF);
		aux_csv_transform(chemprenomH,this.prenomH);
		aux_csv_transform(chemnom,this.nom);
		aux_csv_transform_loc(chemville,this.lieu);
	}
	///

	//auxilliaire de la méthode précédente toujours pour épurer le code.
	public void aux_csv_transform(String chematr, ArrayList<String> attribut) {
		String line;
		try (BufferedReader br=new BufferedReader(new FileReader(chematr))){
			while((line= br.readLine()) !=null){
				String[] columns=line.split(",");
				attribut.addAll(Arrays.asList(columns));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	///

	//auxilliaire également, mais que pour la location (j'aurais pu le faire dans csv_transform mais c'est plus joli x))
	public void aux_csv_transform_loc(String chematr,ArrayList<Donnees> lieu){
		String line;
		try (BufferedReader br=new BufferedReader(new FileReader(chematr))){
			while((line= br.readLine()) !=null){
				String[] columns=line.split(",");
				String location=columns[5].replace("\"", "");
				String longiSansGuillemets = columns[19].replace("\"", "");
				String latiSansGuillemets = columns[20].replace("\"", "");
				double longitude=Double.parseDouble(longiSansGuillemets);
				double latitude=Double.parseDouble(latiSansGuillemets);
				Donnees donnees=new Donnees(location,latitude,longitude);
				lieu.add(donnees);
			}
		} catch (IOException e) {
			System.out.println("AH");
			throw new RuntimeException(e);
		}
	}
	///
}

