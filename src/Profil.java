import javafx.scene.image.Image;

import java.awt.*;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

enum Statut{
        CELIBATAIRE,MARIE,VEUF
    }
    enum Genre{
        HOMME,FEMME,AUTRE
    }
public class Profil implements Comparable<Profil>, Serializable {
    //C'est package par défaut, on verra si on les met en privé plus tard
    String nom,prenom;
    String genre;
    String date_de_naissance;
    String signe;
    String ville;
    double latitude,longitude;
    String statut;
    String profession;
    String recherche;
    Calendar date_de_creation;
    ArrayList<String> hobbies,qualite,defaut;
    int age;
    double taille;
    Image image;
    public int compatibilité;


    public Profil(String nom,String prenom,String date_de_naissance,String genre,String statut,String ville,String recherche) throws Exception {
        this.hobbies= new ArrayList<>();
        this.qualite= new ArrayList<>();
        this.defaut= new ArrayList<>();
        this.nom = nom.toUpperCase();
        this.prenom = prenom;
        this.date_de_naissance=date_de_naissance;
        this.date_de_creation=new GregorianCalendar();
        this.genre=genre;
        this.statut = statut;
        this.ville = ville.toUpperCase();
        this.recherche=recherche;
        //J'appelle la méthode pour calculer l'âge
        calcul_age();
        calcul_signe();
        calcul_latitude_longitude();
    }


    //Je calcule l'âge de la personne en fonction du mois, du jour et de l'année
    public void calcul_age() throws Exception {
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
    public void calcul_signe() throws Exception{
        //Bah comme son nom l'indique ça calcule le signe astrologique en fonction du jour/mois de naissance
        //C'est ma mère qui m'a fait penser à rajouter ça x)
        String date_de_naissance=this.date_de_naissance;
        Calendar birth = new GregorianCalendar();
        //Là, je formate le string qu'on récupère en calendrier
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
        birth.setTime(s.parse(date_de_naissance));
        int mois=birth.get(Calendar.MONTH)+1; //Je met plus 1 parce que pour calendar, le premier mois, c'est 0 (janvier)
        int jour=birth.get(Calendar.DAY_OF_MONTH);
        //Je voulais faire avec des modulos, mais je n'ai pas pu donc c'est dégueu.
        //Il y a une possible factorisation, mais là je ne la vois pas donc bon...
        if ((mois==1 && jour>=21) || (mois==2 && jour<=19)){
            this.signe="Verseau";
        }
        else if (mois == 2 || mois == 3 && jour <= 20){
            this.signe="Poissons";
        }
        else if (mois == 3 || mois == 4 && jour <= 20){
            this.signe="Bélier";
        }
        else if (mois == 4 || mois == 5 && jour <= 21){
            this.signe="Taureau";
        }
        else if (mois == 5 || mois == 6 && jour <= 21){
            this.signe="Gémeaux";
        }
        else if (mois == 6 || mois == 7 && jour <= 22){
            this.signe="Cancer";
        }
        else if (mois == 7 || mois == 8 && jour <= 22){
            this.signe="Lion";
        }
        else if (mois == 8 || mois == 9 && jour <= 22){
            this.signe="Vierge";
        }
        else if (mois == 9 || mois == 10 && jour <= 22){
            this.signe="Balance";
        }
        else if (mois == 10 || mois == 11 && jour <= 22){
            this.signe="Scorpion";
        }
        else if (mois == 11 || mois == 12 && jour <= 21){
            this.signe="Sagitaire";
        }
        else {
            this.signe="Capricorne";
        }
    }
    public void calcul_latitude_longitude() throws Exception {
        try{
            String urlEncodedVille;
            urlEncodedVille= URLEncoder.encode(this.ville, StandardCharsets.UTF_8);
            URL url = new URL(String.format("https://api-adresse.data.gouv.fr/search/?q=%s",urlEncodedVille));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream responseStream = connection.getInputStream();
            String text = new String(responseStream.readNBytes(500), StandardCharsets.UTF_8); //Je limite à 500 pour avoir que le premier résultat
            //(aussi un bout du second, mais osef)
            //Là, je cherche le score de la ville (son taux de chance d'être une vraie ville si vous voulez)
            int index=text.indexOf("score")+7; //Le +7 est là pour ne pas prendre en compte le mot en lui-même
            StringBuilder score=new StringBuilder();
            while  (!(text.charAt(index)==(','))){
                score.append(text.charAt(index));
                index++;
            }
            //Si le score est bas (moins que 0.9) bah ça passe à la trappe
            if(text.contains("coordinates") && Double.parseDouble(String.valueOf(score))>0.9){
                int i=118;
                StringBuilder coord= new StringBuilder();
                while (!(text.charAt(i)==(']'))){
                    coord.append(text.charAt(i));
                    i++;
                }
                String[] s= coord.toString().split(",");
                this.latitude=Double.parseDouble(s[0]);
                this.longitude=Double.parseDouble(s[1]);}
            else {
                throw new ExceptionVilleInexistante();
            }
        }catch (Exception e){
            System.out.println(this.ville);
            throw new ExceptionVilleInexistante();
        }
    }
    public String toString(){
        StringBuilder exit= new StringBuilder(String.format("Nom:%s\nPrenom:%s\nSexe:%s\nAge:%d\nTaille:%1.2fm\nSigne Astrologique:%s\nStatut:%s\nVille:%s\nLatitude:%f\nLongitude:%f\nRecherche:%s",
                this.nom, this.prenom,this.genre, this.age,this.taille, this.signe, this.statut, this.ville, this.latitude, this.longitude, this.recherche));
        if (this.hobbies.size()!=0){
            exit.append("\nHobbies:");
            for (String hobby : this.hobbies) {
                exit.append(hobby).append(",");
            }
        }
        if (this.qualite.size()!=0){
            exit.append("\nQualité:");
            for (String qualite : this.qualite) {
                exit.append(qualite).append(",");
            }
        }
        if (this.qualite.size()!=0){
            exit.append("\nDéfaut:");
            for (String defaut : this.defaut) {
                exit.append(defaut).append(",");
            }
        }
        return(exit.toString());
    }


    public static void main (String[]args) throws Exception{                        //Le .name() c'est pour avoir le String et pas l'énum
        Profil p=new Profil("IeqPa", "Nalyd", "23/12/2003",Genre.HOMME.name(), Statut.CELIBATAIRE.name(),"Toulouse", Genre.FEMME.name());
        p.qualite.add("Honnête");
        p.defaut.add("Désorganisé");
        System.out.println(p);
    }
//Distance entre 2 Profils (et donc 2 villes par extension)
    @Override
    public int compareTo(Profil o) {
        double L1=this.latitude;
        double l1=this.longitude;
        double L2=o.latitude;
        double l2=o.longitude;
        double x = (l2 - l1) * Math.cos((L1 + L2) / 2);
        double y = L2 - L1;
        double z = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        double k = 1.852 * 60;
        return (int) (k * z);
    }
}