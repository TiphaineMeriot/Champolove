import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Modele {
	public ArrayList<String> qualite;
	public ArrayList<String> defaut;
	public ArrayList<String> hobbies;
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
		qualdefhobbies();
    }
    public void enregistrer() {
		try {
			FileOutputStream fos =  new FileOutputStream("donnees/profil.dat");
			FileOutputStream fos2=new FileOutputStream("donnees/qualite.dat");
			FileOutputStream fos3=new FileOutputStream("donnees/defaut.dat");
			FileOutputStream fos4=new FileOutputStream("donnees/hobbies.dat");
			ObjectOutputStream oos= new ObjectOutputStream(fos);
			ObjectOutputStream oos2=new ObjectOutputStream(fos2);
			ObjectOutputStream oos3=new ObjectOutputStream(fos3);
			ObjectOutputStream oos4=new ObjectOutputStream(fos4);
			oos.writeObject(this.listeProfil);
			oos2.writeObject(this.qualite);
			oos3.writeObject(this.defaut);
			oos4.writeObject(this.hobbies);
			oos.close();
			oos2.close();
			oos3.close();
			oos4.close();
			fos.close();
			fos2.close();
			fos3.close();
			fos4.close();
		} catch (IOException e) {
			throw new RuntimeException("Impossible d'écrire les données");
		}
	}
	public void charger() throws IOException, ClassNotFoundException {
		try{
			FileInputStream fis = new FileInputStream("donnees/profil.dat");
			FileInputStream fis2 = new FileInputStream("donnees/qualite.dat");
			FileInputStream fis3 = new FileInputStream("donnees/defaut.dat");
			FileInputStream fis4= new FileInputStream("donnees/hobbies.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			ObjectInputStream ois2 = new ObjectInputStream(fis2);
			ObjectInputStream ois3 = new ObjectInputStream(fis3);
			ObjectInputStream ois4 = new ObjectInputStream(fis4);
			this.listeProfil = (TreeSet<Profil>)ois.readObject();
			this.qualite = (ArrayList<String>)ois2.readObject();
			this.defaut = (ArrayList<String>)ois3.readObject();
			this.hobbies=(ArrayList<String>) ois4.readObject();
			ois.close();
			ois2.close();
			ois3.close();
			ois4.close();
			fis.close();
			fis2.close();
			fis3.close();
			fis4.close();
		}catch(IOException | ClassNotFoundException e){
			throw new RuntimeException("Lecture des données impossibles ou données corrompues");
		}
	}
	public void qualdefhobbies(){
		String chemqual="donnees/qualites.csv";
		String chemdef="donnees/defaut.csv";
		String chemhobbies="donnees/hobbies.csv";
		aux_qualdefhobbies(chemqual, qualite);
		aux_qualdefhobbies(chemdef, defaut);
		aux_qualdefhobbies(chemhobbies,hobbies);
	}

	public void aux_qualdefhobbies(String chematr, ArrayList<String> attribut) {
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

