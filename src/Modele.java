import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Modele {
	public ArrayList<String> qualite;
	public ArrayList<String> defaut;
    public ArrayList<Profil> listeProfil;
	public ArrayList<File> listeImageH;
	public ArrayList<File> listeImageF;
    public Modele() {
        this.listeProfil=new ArrayList<>();
		this.qualite=new ArrayList<>();
		this.defaut=new ArrayList<>();
		File dirF=new File("C:\\Users\\Horoto\\IdeaProjects\\Champolove\\src\\images\\FEMME");
		File dirH=new File("C:\\Users\\Horoto\\IdeaProjects\\Champolove\\src\\images\\HOMME");
		this.listeImageH=new ArrayList<>(dirH.listFiles().length);
		this.listeImageF=new ArrayList<>(dirF.listFiles().length);
		this.listeImageF.addAll(List.of(dirF.listFiles()));
		this.listeImageH.addAll(List.of(dirH.listFiles()));
		qualetdef();
    }
    public void enregistrer() {
		try {
			FileOutputStream fos =  new FileOutputStream(new File("profil.dat"));;
			FileOutputStream fos2=new FileOutputStream(new File("qualite.dat"));
			FileOutputStream fos3=new FileOutputStream(new File("defaut.dat"));
			ObjectOutputStream oos= new ObjectOutputStream(fos);
			ObjectOutputStream oos2=new ObjectOutputStream(fos2);
			ObjectOutputStream oos3=new ObjectOutputStream(fos3);
			oos.writeObject(this.listeProfil);
			oos2.writeObject(this.qualite);
			oos3.writeObject(this.defaut);
			oos.close();
			oos2.close();
			oos3.close();
			fos.close();
			fos2.close();
			fos3.close();
		} catch (IOException e) {
			throw new RuntimeException("Impossible d'écrire les données");
		}
	}
	public void charger() throws IOException, ClassNotFoundException {
		try{
			FileInputStream fis = new FileInputStream(new File("profil.dat"));
			FileInputStream fis2 = new FileInputStream(new File("qualite.dat"));
			FileInputStream fis3 = new FileInputStream(new File("defaut.dat"));
			ObjectInputStream ois = new ObjectInputStream(fis);
			ObjectInputStream ois2 = new ObjectInputStream(fis2);
			ObjectInputStream ois3 = new ObjectInputStream(fis3);
			this.listeProfil = (ArrayList<Profil>)ois.readObject();
			this.qualite = (ArrayList<String>)ois2.readObject();
			this.defaut = (ArrayList<String>)ois3.readObject();
			ois.close();
			ois2.close();
			ois3.close();
			fis.close();
			fis2.close();
			fis3.close();
		}catch(IOException | ClassNotFoundException e){
			throw new RuntimeException("Lecture des données impossibles ou données corrompues");
		}
	}
	public void qualetdef(){
		String chemqual="src/qualites.csv";
		String chemdef="src/defaut.csv";
		aux_qualetdef(chemqual, qualite);
		aux_qualetdef(chemdef, defaut);
	}

	public void aux_qualetdef(String chematr, ArrayList<String> attribut) {
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

