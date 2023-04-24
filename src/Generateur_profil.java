import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
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
        return(rdm.nextDouble(max-min)+min);
    }

    public void qualdefhobAlea(ArrayList<String> liste,ArrayList<String> source){
        Random r=new Random();
        for(int j=0;j<entierAlea(1,3);j++){
                int ind=r.nextInt(source.size());
                liste.add(source.get(ind));
            }
    }

    public double choix_images_et_taille(File f,ArrayList<File> liste,double taillemin,double taillemax){
        Random r=new Random();
        int rdm=r.nextInt(liste.size());
        liste.get(rdm).renameTo(f);
        liste.remove(liste.get(rdm));
        return doubleAlea(taillemin,taillemax);
    }
    public void creation_Profil(Modele mod) throws Exception {
        String[] statut = {"CELIBATAIRE", "MARIE", "VEUF"};
        String[] recherche = {"HOMME", "FEMME", "AUTRE"};
        //Choix du genre (avec faible chance d'avoir des non-binaires
            String genre;
            int rdmbinaire=entierAlea(1,1000);
            if (rdmbinaire!=3){
                int rdmgenre=entierAlea(1,2);
                if(rdmgenre==1){
                    genre="HOMME";
                }
                else{
                    genre="FEMME";
                }
            }
            else{
                genre="AUTRE";
            }
            ///

            //Appel de l'api
            URL url = new URL("https://randomuser.me/api/?nat=fr&inc=name,location");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream responseStream = connection.getInputStream();
            String text = new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);
            ///

            //Choix du nom
            int inom = text.indexOf("last") + 7;
            String nom = "";
            while (!(text.charAt(inom) == ('"'))) {
                nom += (text.charAt(inom));
                inom++;
            }
            ///

            //Choix du prenom
            int iprenom = text.indexOf("first") + 8;
            String prenom = "";
            while (!(text.charAt(iprenom) == ('"'))) {
                prenom += (text.charAt(iprenom));
                iprenom++;
            }
            ///

            //Choix du lieu
            int ilocation = text.indexOf("city") + 7;
            String location = "";
            while (!(text.charAt(ilocation) == ('"'))) {
                location += (text.charAt(ilocation));
                ilocation++;
            }
            ///

            //Choix de la date de naissance
            int year = entierAlea(1950,2006);
            int month = entierAlea(1,12);
            int day = entierAlea(1,29);
            String ddn = String.format("%d/%d/%d", day, month, year);
            ///

            //Création du profil
            Profil p = new Profil(nom, prenom, ddn, genre, statut[new Random().nextInt(statut.length)], location,
                    recherche[new Random().nextInt(recherche.length)]);
            ///

            //Choix des images pour les profils+taille
            Random r = new Random();
            Path relativePath = Paths.get("src", "images", p.genre, String.format("%s_%s.jpeg", p.nom, p.prenom));
            Path absolutePath = relativePath.toAbsolutePath();
            File f = new File(absolutePath.toString());
            if (Objects.equals(p.genre, "HOMME")) {
                p.taille=choix_images_et_taille(f,mod.listeImageH,1.6,2);
            } else if (Objects.equals(p.genre, "FEMME")) {
                p.taille=choix_images_et_taille(f,mod.listeImageF,1.5,1.8);
            } else {
                int piece = r.nextInt(2);
                if (piece == 0) {
                    p.taille=choix_images_et_taille(f,mod.listeImageF,1.5,1.8);
                } else {
                    p.taille=choix_images_et_taille(f,mod.listeImageH,1.6,2);
                }
            }
            ///

            //Appel d'une fonction auxilliaire pour avoir des qualités, défauts et hobbies aléatoires
            qualdefhobAlea(p.defaut,mod.defaut);
            qualdefhobAlea(p.hobbies,mod.hobbies);
            qualdefhobAlea(p.qualite,mod.qualite);
            ///

            //Choix de la date de création du profil
            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
            String date_de_creation=String.format("%d/%d/%d",entierAlea(1,28),entierAlea(1,4),2023);
            p.date_de_creation.setTime(s.parse(date_de_creation));
            ///

            mod.listeProfil.add(p);
    }
    //////////////////////////////////////////////////////

    //Surcharge du constructeur qui permet de ne pas générer de profil, mais d'avoir accès aux méthodes liées
    public Generateur_profil(){

    }
    ///

    //Constructeur de base (sans limitation de profils)
    public Generateur_profil(Modele mod) throws Exception {
        while (mod.listeImageF.size()!=0 && mod.listeImageH.size()!=0){
            creation_Profil(mod);
        }
    }
    ///

    //Constructeur avec limitation de profils
    public Generateur_profil(Modele mod,int nbrProfil) throws Exception{
        if (nbrProfil< (mod.listeImageF.size()+mod.listeImageH.size()) /2){ //Je prends la moyenne des 2 tailles comme ça je suis sûr que ça passera
            for(int i=0;i<nbrProfil;i++){
                creation_Profil(mod);
            }
        }
        else{
            System.out.println("Le nombre de profil dépasse la limite d'images présentes dans un des 2 dossiers");
        }
    }
}
