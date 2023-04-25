import java.io.File;
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
        return(rdm.nextDouble(max-min)+min);
    }

    public void qualdefhobAlea(Collection<String> liste,ArrayList<String> source){
        Random r=new Random();
        for(int j=0;j<entierAlea(1,3);j++){
                int ind=r.nextInt(source.size());
                liste.add(source.get(ind));
            }
    }

    public double choix_images_et_taille(Profil p,String genreim,ArrayList<File> liste,double taillemin,double taillemax){
        Path relativePath = Paths.get("src", "images",genreim, String.format("%s_%s.jpeg", p.nom, p.prenom));
        Path absolutePath = relativePath.toAbsolutePath();
        File f = new File(absolutePath.toString());
        p.image=absolutePath.toString();
        Random r=new Random();
        int rdm=r.nextInt(liste.size());
        liste.get(rdm).renameTo(f);
        liste.remove(liste.get(rdm));
        return doubleAlea(taillemin,taillemax);
    }
    public void creation_Profil(Modele mod) throws Exception {
        String[] statut = {"CELIBATAIRE", "MARIE", "VEUF"};
        String[] recherche = {"HOMME", "FEMME", "AUTRE"};
        //Choix du genre (avec faible chance d'avoir des non-binaires)+ nom et prenom
        String genre,nom,prenom;
        String leprenom;
        int rdmbinaire=entierAlea(1,1000);
        if (rdmbinaire!=3){
            int rdmgenre=entierAlea(1,2);
            if(rdmgenre==1){
                genre="HOMME";
                int iprenom=entierAlea(0,mod.prenomH.size()-1);
                leprenom=mod.prenomH.get(iprenom);
            }
            else{
                genre="FEMME";
                int iprenom=entierAlea(0,mod.prenomF.size()-1);
                leprenom=mod.prenomF.get(iprenom);
            }
        }
        else{
            int piece=entierAlea(1,2);
            if (piece==1){
                int iprenom=entierAlea(0,mod.prenomF.size()-1);
                leprenom=mod.prenomF.get(iprenom);
            }
            else{
                int iprenom=entierAlea(0,mod.prenomH.size()-1);
                leprenom=mod.prenomH.get(iprenom);
            }
            genre="AUTRE";
        }
        String premierelettre=leprenom.substring(0,1);
        String resteduPrenom=leprenom.substring(1).toLowerCase();
        prenom=premierelettre+resteduPrenom;
        int inom=entierAlea(0,mod.nom.size()-1);
        nom=mod.nom.get(inom);
        ///

        //Choix du lieu
        int ilocation=entierAlea(0,mod.lieu.size()-1);
        String location=mod.lieu.get(ilocation).location;
        double latitude=mod.lieu.get(ilocation).latitude;
        double longitude=mod.lieu.get(ilocation).longitude;
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

        //Choix des tailles+images pour les profils
        Random r = new Random();
        Path relativePath = Paths.get("src", "images", p.genre, String.format("%s_%s.jpeg", p.nom, p.prenom));
        Path absolutePath = relativePath.toAbsolutePath();
        File f = new File(absolutePath.toString());
        if (Objects.equals(p.genre, "HOMME")) {
            choix_images_et_taille(p,p.genre,mod.listeImageH,1.6,2);
        } else if (Objects.equals(p.genre, "FEMME")) {
            p.image=absolutePath.toString();
            p.taille=choix_images_et_taille(p,p.genre,mod.listeImageF,1.5,1.8);
        } else {
            int piece = r.nextInt(2);
            if (piece == 0) {
                p.taille=choix_images_et_taille(p,p.genre,mod.listeImageF,1.5,1.8);
            } else {
                choix_images_et_taille(p,"HOMME",mod.listeImageH,1.6,2);
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

        //Ajout latitude et longitude
        p.latitude=latitude;
        p.longitude=longitude;
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
            System.exit(0);
        }
    }
}
