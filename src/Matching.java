import com.sun.source.tree.Tree;

import java.util.*;

public class Matching{
    //On dit que c'est une liste les qualités et défauts
    //TODO Il faut faire une option avec ce que la personne recherche
    //TODO Genre cherche homme >1.80m qui aime la musique et attentionné.
    //TODO Je ne veux pas qu'il soit con.
    //J'ai pensé à un dictionnaire pour le sexe que la personne recherche
    //ça serait un truc du genre:
    public static void matching1(Profil p1,double tailleminimale,int distancemin) throws Exception {
        //TODO A terme, tout ce qui concerne le tri par genre sera ailleurs (on va pas trier à chaque fois ^^)
        Comparator<Profil> tailleComparator = Comparator.comparingDouble(p -> p.taille);
        Generateur_profil g=new Generateur_profil();
        HashMap<String,TreeSet<Profil>> trigenre=new HashMap<>();
        for(Profil profil: g.listeProfil){
            String genre=profil.genre;
            if(!trigenre.containsKey(genre)){
                trigenre.put(genre, new TreeSet<>(tailleComparator));
            }
            trigenre.get(genre).add(profil);
        }
        TreeSet<Profil>rechercher=trigenre.get(p1.recherche);
        for(Profil profil:rechercher){
            if(profil.recherche.equals(p1.genre) && profil.taille>=tailleminimale && p1.compareTo(profil)<= distancemin){
                System.out.println(profil);
                System.out.println(" ");
            }
        }
        //TODO Maintenant, j'attribue des points à chaque fois qu'un critère concorde:


    }


    public static void main(String[] args) throws Exception {
        Profil p1 = new Profil("CHARLIES", "Tom", "01/03/1999", Genre.HOMME.name(), Statut.CELIBATAIRE.name(), "Bordeaux", Genre.FEMME.name());
        ArrayList<String> recherche=new ArrayList<>();
        recherche.add("homme");
        recherche.add("plus de 1m80");
        recherche.add("aime musique");
        recherche.add("attentionné");
        matching1(p1,1.2,300);

    }
}
