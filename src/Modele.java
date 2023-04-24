import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Modele {
	public ArrayList<String> qualite;
	public ArrayList<String> defaut;
	public ArrayList<String> hobbies;
	public ArrayList<String> prenomH;
	public ArrayList<String> prenomF;
	public ArrayList<String> nom;
    public TreeSet<Profil> listeProfil;
	public HashMap<String,TreeSet<Profil>> tripargenre; //Autre organisation des profils, utile dans Matching
	public ArrayList<File> listeImageH;
	public ArrayList<File> listeImageF;

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
    public Modele() {
		Comparator<Profil> ddc= new SerializableComparator(); // Compare en fonction de la date de création
        this.listeProfil=new TreeSet<>(ddc);
		this.qualite=new ArrayList<>();
		this.defaut=new ArrayList<>();
		this.hobbies=new ArrayList<>();
		this.nom=new ArrayList<>();
		this.prenomH=new ArrayList<>();
		this.prenomF=new ArrayList<>();
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
		csv_transform(); //TODO faire ça que quand on a pas de .dat (sinon c'est inutile)
    }
	public void aux_enregistrer(String chemin, Object liste) throws IOException {
		FileOutputStream fos = new FileOutputStream(chemin);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(liste);
		oos.close();
		fos.close();
	}
    public void enregistrer() {
		try {
			aux_enregistrer("donnees/profil.dat",this.listeProfil);
			aux_enregistrer("donnees/qualite.dat",this.qualite);
			aux_enregistrer("donnees/defaut.dat",this.defaut);
			aux_enregistrer("donnees/hobbies.dat",this.hobbies);
			aux_enregistrer("donnees/prenomH.dat",this.prenomH);
			aux_enregistrer("donnees/prenomF.dat",this.prenomF);
			aux_enregistrer("donnees/nom.dat",this.nom);
		} catch (IOException e) {
			throw new RuntimeException("Impossible d'écrire les données");
		}
	}
	@SuppressWarnings("unchecked")
	public <T> T aux_charger(String chemin) throws Exception {
		FileInputStream fis = new FileInputStream(chemin);
		ObjectInputStream ois = new ObjectInputStream(fis);
		T liste = (T) ois.readObject();
		ois.close();
		fis.close();
		return liste;
	}

	public void charger() {
		try {
			this.listeProfil = aux_charger("donnees/profil.dat");
			this.qualite = aux_charger("donnees/qualite.dat");
			this.defaut = aux_charger("donnees/defaut.dat");
			this.hobbies = aux_charger("donnees/hobbies.dat");
			this.prenomH = aux_charger("donnees/prenomH.dat");
			this.prenomF = aux_charger("donnees/prenomF.dat");
			this.nom = aux_charger("donnees/nom.dat");
		} catch (Exception e) {
			throw new RuntimeException("Lecture des données impossibles ou données corrompues");
		}
	}
	public void csv_transform(){
		String chemqual="donnees/qualites.csv";
		String chemdef="donnees/defaut.csv";
		String chemhobbies="donnees/hobbies.csv";
		String chemprenomF="donnees/prenomF.csv";
		String chemprenomH="donnees/prenomH.csv";
		String chemnom="donnees/nom.csv";
		aux_csv_transform(chemqual, qualite);
		aux_csv_transform(chemdef, defaut);
		aux_csv_transform(chemhobbies,hobbies);
		aux_csv_transform(chemprenomF,prenomF);
		aux_csv_transform(chemprenomH,prenomH);
		aux_csv_transform(chemnom,nom);
	}

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
}

