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
    String ville;
    double latitude,longitude;
    String statut;
    String profession;
    String recherche;
    ArrayList<String> hobbies;
    int age;
    double taille;

    public Profil(String nom,String prenom,String date_de_naissance,String genre,String statut,String ville,String recherche) throws Exception {
        this.nom = nom.toUpperCase(Locale.ROOT);
        this.prenom = prenom;
        this.date_de_naissance=date_de_naissance;
        this.genre=genre;
        this.statut = statut;
        this.ville = ville;
        this.recherche=recherche;
        //J'appelle la méthode pour calculer l'âge
        calcul_age();
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
            String exit;
            this.latitude=Double.parseDouble(s[0]);
            this.longitude=Double.parseDouble(s[1]);
        }
        else{
            throw new ExceptionVilleInexistante();
        }

        System.out.println(text);
    }
    public String toString(){
        String exit=String.format("Nom:%s\nPrenom:%s\nSexe:%s\nAge:%d\nStatut:%s\nVille:%s\nLatitude:%f\nLongitude:%f\nRecherche:%s",
        this.nom,this.prenom,this.genre,this.age,this.statut,this.ville,this.latitude,this.longitude,this.recherche);
        return(exit);
    }


    public static void main (String[]args) throws Exception {
        ArrayList<String> hobbies = new ArrayList<>();
        hobbies.add("les jeux vidéos");
        hobbies.add("manger");
        hobbies.add("regarder des animés");
        Profil p=new Profil("IeqPa", "Nalyd", "01/02/2003",Genre.HOMME.name(), Statut.CELIBATAIRE.name(),"Toulouse", Genre.FEMME.name());
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