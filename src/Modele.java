import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Modele {
    public ArrayList<Profil> listeProfil;
	public ArrayList<File> listeImageH;
	public ArrayList<File> listeImageF;
    public Modele() {
        this.listeProfil=new ArrayList<>();
		File dirF=new File("C:\\Users\\Horoto\\IdeaProjects\\Champolove\\src\\images\\FEMME");
		File dirH=new File("C:\\Users\\Horoto\\IdeaProjects\\Champolove\\src\\images\\HOMME");
		this.listeImageH=new ArrayList<>(dirH.listFiles().length);
		this.listeImageF=new ArrayList<>(dirF.listFiles().length);
		this.listeImageF.addAll(List.of(dirF.listFiles()));
		this.listeImageH.addAll(List.of(dirH.listFiles()));
    }
    public void enregistrer() {
		try {
			FileOutputStream fos =  new FileOutputStream(new File("profil.dat"));;
			ObjectOutputStream oos= new ObjectOutputStream(fos);
			oos.writeObject(this.listeProfil);
			oos.close();
			fos.close();
		} catch (IOException e) {
			throw new RuntimeException("Impossible d'écrire les données");
		}
	}
	public void charger() throws IOException, ClassNotFoundException {
		try{
			FileInputStream fis = new FileInputStream(new File("profil.dat"));
			ObjectInputStream ois = new ObjectInputStream(fis);
			this.listeProfil = (ArrayList<Profil>)ois.readObject();
			ois.close();
			fis.close();
		}catch(IOException | ClassNotFoundException e){
			throw new RuntimeException("Lecture des données impossibles ou données corrompues");
		}
	}
}

