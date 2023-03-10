import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Profil {
    //C'est package par défaut on verra si on les met en privé plus tard
    String nom,prenom,date_de_naissance,ville,rue,statut,profession,typeRelation;
    ArrayList<String> hobbies;
    int age;

    public Profil(String nom, String prenom, String date_de_naissance, String statut, String profession, String typeRelation, String ville, String rue, ArrayList<String> hobbies) throws ParseException {
        this.nom = nom;
        this.prenom = prenom;
        Calendar cal=new GregorianCalendar();
        System.out.println(cal.get(Calendar.YEAR));
        System.out.println(cal.getTime());
        SimpleDateFormat s =new SimpleDateFormat("dd/MM/yyyy");
        cal.setTime(s.parse(date_de_naissance));
        this.date_de_naissance=date_de_naissance;
        this.age=1;
        this.statut=statut;
        this.profession=profession;
        this.typeRelation=typeRelation;
        this.ville = ville;
        this.rue = rue;
        this.hobbies = hobbies;
    }

    public static void main(String[] args) throws ParseException {
        ArrayList<String> saucisse=new ArrayList<>();
        Calendar cal=new GregorianCalendar();
        new Profil("on","o","01/08/2003","o","o","o","o","o",saucisse);
    }
}

