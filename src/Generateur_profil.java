import java.util.ArrayList;
import java.util.Arrays;

public class Generateur_profil {
    ArrayList<Profil> listeProfil;
    public Generateur_profil() throws Exception {
        listeProfil = new ArrayList<>();
        Profil p1 = new Profil("CHARLES", "Tom", "01/03/1999", Genre.HOMME.name(), Statut.CELIBATAIRE.name(), "Bordeaux", Genre.HOMME.name());
        Profil p2 = new Profil("FOURNIER", "Justine", "08/10/1978", Genre.FEMME.name(), Statut.VEUF.name(), "Paris", Genre.HOMME.name());
        Profil p3 = new Profil("MARTIN", "Kévin", "30/01/1987", Genre.HOMME.name(), Statut.CELIBATAIRE.name(), "Albi", Genre.FEMME.name());
        Profil p4 = new Profil("BERNARD", "Mathilde", "16/06/1981", Genre.FEMME.name(), Statut.CELIBATAIRE.name(), "Rodez", Genre.HOMME.name());
        Profil p5 = new Profil("MOREL", "Mélanie", "28/04/1979", Genre.FEMME.name(), Statut.CELIBATAIRE.name(), "Rodez", Genre.HOMME.name());
        Profil p6 = new Profil("LOPEZ", "Hugo", "29/08/1990", Genre.HOMME.name(), Statut.MARIE.name(), "Muret", Genre.FEMME.name());
        Profil p7 = new Profil("ROUSSEAU", "Simon", "09/05/1987", Genre.HOMME.name(), Statut.CELIBATAIRE.name(), "Toulouse", Genre.HOMME.name());
        Profil p8 = new Profil("ANDRE", "Julie", "21/12/1985", Genre.FEMME.name(), Statut.CELIBATAIRE.name(), "Balma", Genre.HOMME.name());
        p1.taille=1.8;
        p1.hobbies.addAll(Arrays.asList("Sport","Art","Culture","Cinéma"));
        p1.qualite.addAll(Arrays.asList("Joyeux","Attentionné","Cultivé"));
        p1.defaut.addAll(Arrays.asList("Timide","Egoïste","Pleurnichard"));
        p2.taille=1.7;
        p2.hobbies.addAll(Arrays.asList("Jeux vidéos","Art","Cinéma"));
        p2.qualite.addAll(Arrays.asList("Joyeux","Avenant","Cultivé"));
        p2.defaut.addAll(Arrays.asList("Triste","Idiot","Radin"));
        //flemme de faire les autres
        p3.taille=1.6;
        p4.taille=1.5;
        p5.taille=1.4;
        p6.taille=1.3;
        p7.taille=1.2;
        p8.taille=1.1;
        //J'en avais besoin pour mon matching
        listeProfil.add(p1);
        listeProfil.add(p2);
        listeProfil.add(p3);
        listeProfil.add(p4);
        listeProfil.add(p5);
        listeProfil.add(p6);
        listeProfil.add(p7);
        listeProfil.add(p8);
    }
}
