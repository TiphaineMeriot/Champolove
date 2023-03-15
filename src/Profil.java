import javafx.scene.control.Label;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

enum Statut{
        CELIBATAIRE,MARIE,VEUF
    }
    enum Genre{
        HOMME,FEMME,AUTRE
    }

public class Profil implements Comparable<Profil>{
    //C'est package par défaut, on verra si on les met en privé plus tard
    String nom,prenom;
    String genre;
    String date_de_naissance;
    String ville;
    String statut;
    String profession;
    String recherche;
    ArrayList<String> hobbies;
    int age;
    double taille;

    public Profil(String nom, String prenom, String date_de_naissance, String genre, String statut, String ville, String recherche) throws ParseException {

        this.nom = nom.toUpperCase(Locale.ROOT);
        this.prenom = prenom;
        this.date_de_naissance=date_de_naissance;
        this.genre=genre;
        this.statut = statut;

        this.ville = ville;
        this.recherche=recherche;
    }


    //Je calcule l'âge de la personne en fonction du mois, du jour et de l'année
    public void calcul_age() throws ParseException {
        String date_de_naissance=this.date_de_naissance;
        Calendar birth = new GregorianCalendar();
        Calendar today = new GregorianCalendar();
        //Là, je formate le string qu'on récupère en calendrier
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
        birth.setTime(s.parse(date_de_naissance));
        if (today.get(Calendar.MONTH) - birth.get(Calendar.MONTH) >= 0 && today.get(Calendar.DAY_OF_MONTH) - birth.get(Calendar.DAY_OF_MONTH) >= 0) {
            this.age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        } else {
            this.age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR) - 1;
        }
    }
    public String toString(){
        String exit=String.format("Nom:%s\nPrenom:%s\nSexe:%s\nAge:%d\nStatut:%s\nVille:%s\nRecherche:%s",
        this.nom,this.prenom,this.genre,this.age,this.statut,this.ville,this.recherche);
        return(exit);
    }


    public static void main (String[]args) throws ParseException {
        ArrayList<String> hobbies = new ArrayList<>();
        hobbies.add("les jeux vidéos");
        hobbies.add("manger");
        hobbies.add("regarder des animés");
       // Profil p=new Profil("IeqPa", "Nalyd",Genre.HOMME.name(), "01/02/2003", Statut.CELIBATAIRE.name(), "Chômage", "Albi", 1.85, hobbies,Genre.FEMME.name());
       // System.out.println(p);
    }
//À la base je voulais faire la distance entre 2 villes, mais pour ça il faudrait d'abord connaitre les coordonnées GPS des 2 villes ^^
// En attendant je fais en fonction de l'âge
    @Override
    public int compareTo(Profil o) {
        return(this.age-o.age);
    }
}