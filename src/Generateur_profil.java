import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
public class Generateur_profil {
    //Fonctions auxilliaires //////////////////////////////
    public int entierAlea(int min,int max){
        Random rdm=new Random();
        return(rdm.nextInt(max - min + 1) + min);
    }
    public double doubleAlea(double min,double max){
        Random rdm=new Random();
        return(rdm.nextDouble(max-min+1)+min);
    }

    public void qualdefhobAlea(ArrayList<String> liste,ArrayList<String> source){
        Random r=new Random();
        for(int j=0;j<entierAlea(1,3);j++){
                int ind=r.nextInt(source.size());
                liste.add(source.get(ind));
            }
    }
    //////////////////////////////////////////////////////
    public Generateur_profil(){

    }
    public Generateur_profil(Modele mod) throws Exception {

        String[] statut = {"CELIBATAIRE", "MARIE", "VEUF"};
        String[] recherche = {"HOMME", "FEMME", "AUTRE"};
        while (mod.listeImageF.size()!=0 && mod.listeImageH.size()!=0){
            URL url = new URL("https://randomuser.me/api/?nat=fr&inc=name,gender,location");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream responseStream = connection.getInputStream();
            String text = new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);
            int igenre = text.indexOf("gender") + 9;
            String genre;
            //TODO Ajouter un truc aléatoire pour avoir des non binaire x)
            if (text.charAt(igenre) == 'm') {
                genre = "HOMME";
            } else {
                genre = "FEMME";
            }
            int inom = text.indexOf("last") + 7;
            String nom = "";
            while (!(text.charAt(inom) == ('"'))) {
                nom += (text.charAt(inom));
                inom++;
            }
            int iprenom = text.indexOf("first") + 8;
            String prenom = "";
            while (!(text.charAt(iprenom) == ('"'))) {
                prenom += (text.charAt(iprenom));
                iprenom++;
            }
            int ilocation = text.indexOf("city") + 7;
            String location = "";
            while (!(text.charAt(ilocation) == ('"'))) {
                location += (text.charAt(ilocation));
                ilocation++;
            }
            int year = entierAlea(1950,2006);
            int month = entierAlea(1,12);
            int day = entierAlea(1,29); //TODO: faire un jour plus réaliste parce que pour l'instant on peut pas être né un 30 x)
            String ddn = String.format("%d/%d/%d", day, month, year);
            Profil p = new Profil(nom, prenom, ddn, genre, statut[new Random().nextInt(statut.length)], location,
                    recherche[new Random().nextInt(recherche.length)]);
            Random r = new Random();
            Path relativePath = Paths.get("src", "images", p.genre, String.format("%s_%s.jpeg", p.nom, p.prenom));
            Path absolutePath = relativePath.toAbsolutePath();
            File f = new File(absolutePath.toString());
            if (Objects.equals(p.genre, "HOMME")) {
                int rdm = r.nextInt(mod.listeImageH.size());
                mod.listeImageH.get(rdm).renameTo(f);
                mod.listeImageH.remove(mod.listeImageH.get(rdm));
                p.taille=doubleAlea(1.6,2);
            } else if (Objects.equals(p.genre, "FEMME")) {
                int rdm = r.nextInt(mod.listeImageF.size());
                mod.listeImageF.get(rdm).renameTo(f);
                mod.listeImageF.remove(mod.listeImageF.get(rdm));
                p.taille=doubleAlea(1.5,1.8);
            } else {
                int piece = r.nextInt(2);
                if (piece == 0) {
                    int rdm = r.nextInt(mod.listeImageF.size());
                    mod.listeImageF.get(rdm).renameTo(f);
                    mod.listeImageF.remove(mod.listeImageF.get(rdm));
                    p.taille = doubleAlea(1.5, 1.8);
                } else {
                    int rdm = r.nextInt(mod.listeImageH.size());
                    mod.listeImageH.get(rdm).renameTo(f);
                    mod.listeImageH.remove(mod.listeImageH.get(rdm));
                    p.taille = doubleAlea(1.6, 2);
                }
            }
            qualdefhobAlea(p.defaut,mod.defaut);
            qualdefhobAlea(p.hobbies,mod.hobbies);
            qualdefhobAlea(p.qualite,mod.qualite);

            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
            String date_de_creation=String.format("%d/%d/%d",entierAlea(1,28),entierAlea(1,4),2023);
            p.date_de_creation.setTime(s.parse(date_de_creation));
            mod.listeProfil.add(p);
        }
    }
}
