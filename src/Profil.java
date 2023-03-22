import javafx.scene.image.Image;

import java.awt.*;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
    String signe;
    String ville;
    double latitude,longitude;
    String statut;
    String profession;
    String recherche;
    ArrayList<String> hobbies,qualite,defaut;
    int age;
    double taille;

    Image image;


    public Profil(String nom,String prenom,String date_de_naissance,String genre,String statut,String ville,String recherche) throws Exception {
        this.hobbies= new ArrayList<>();
        this.qualite= new ArrayList<>();
        this.defaut= new ArrayList<>();
        this.nom = nom.toUpperCase(Locale.ROOT);
        this.prenom = prenom;
        this.date_de_naissance=date_de_naissance;
        this.genre=genre;
        this.statut = statut;
        this.ville = ville;
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
        else if ((mois==2 && jour>=20) || (mois==3 && jour<=20)){
            this.signe="Poissons";
        }
        else if ((mois==3 && jour>=21) || (mois==4 && jour<=20)){
            this.signe="Bélier";
        }
        else if ((mois==4 && jour>=21) || (mois==5 && jour<=21)){
            this.signe="Taureau";
        }
        else if ((mois==5 && jour>=22) || (mois==6 && jour<=21)){
            this.signe="Gémeaux";
        }
        else if ((mois==6 && jour>=22) || (mois==7 && jour<=22)){
            this.signe="Cancer";
        }
        else if ((mois==7 && jour>=23) || (mois==8 && jour<=22)){
            this.signe="Lion";
        }
        else if ((mois==8 && jour>=23) || (mois==9 && jour<=22)){
            this.signe="Vierge";
        }
        else if ((mois==9 && jour>=23) || (mois==10 && jour<=22)){
            this.signe="Balance";
        }
        else if ((mois==10 && jour>=23) || (mois==11 && jour<=22)){
            this.signe="Scorpion";
        }
        else if ((mois==11 && jour>=23) || (mois==12 && jour<=21)){
            this.signe="Sagitaire";
        }
        else if ((mois==12 && jour>=22) || (mois==1 && jour<=20)){
            this.signe="Capricorne";
        }
    }
    public void calcul_latitude_longitude() throws Exception {
        URL url = new URL(String.format("https://api-adresse.data.gouv.fr/search/?q=%scity=%s",this.ville,this.ville));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream responseStream = connection.getInputStream();
        String text = new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);
        if(text.contains("coordinates")){
            int i=118;
            StringBuilder coord= new StringBuilder();
            while (!(text.charAt(i)==(']'))){
                coord.append(text.charAt(i));
                i++;
            }
            String[] s= coord.toString().split(",");
            this.latitude=Double.parseDouble(s[0]);
            this.longitude=Double.parseDouble(s[1]);
        }
        else{
            throw new ExceptionVilleInexistante();
        }
    }
    public String toString(){
        String exit=String.format("Nom:%s\nPrenom:%s\nSexe:%s\nAge:%d\nSigne Astrologique:%s\nStatut:%s\nVille:%s\nLatitude:%f\nLongitude:%f\nRecherche:%s",
        this.nom,this.prenom,this.genre,this.age,this.signe,this.statut,this.ville,this.latitude,this.longitude,this.recherche);
        if (this.hobbies.size()!=0){
            exit+="\nHobbies:";
            for (String hobby : this.hobbies) {
                exit += hobby+",";
            }
        }
        if (this.qualite.size()!=0){
            exit+="\nQualité:";
            for (String qualite : this.qualite) {
                exit += qualite+",";
            }
        }
        if (this.qualite.size()!=0){
            exit+="\nDéfaut:";
            for (String defaut : this.defaut) {
                exit += defaut+",";
            }
        }
        return(exit);
    }


    public static void main (String[]args) throws Exception{                        //Le .name() c'est pour avoir le String et pas l'énum
        Profil p=new Profil("IeqPa", "Nalyd", "23/12/2003",Genre.HOMME.name(), Statut.CELIBATAIRE.name(),"Toulouse", Genre.FEMME.name());
        p.qualite.add("Honnête");
        p.defaut.add("Désorganisé");
        p.hobbies.add("jeux vidéos");
        p.hobbies.add("manger");
        p.hobbies.add("j'adore rire HAHA");
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