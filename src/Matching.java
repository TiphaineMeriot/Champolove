import com.sun.source.tree.Tree;

import java.util.*;

public class Matching{
    //On dit que c'est une liste les qualités et défauts
    //TODO Il faut faire une option avec ce que la personne recherche
    //TODO Genre cherche homme >1.80m qui aime la musique et attentionné.
    //TODO Je ne veux pas qu'il soit con.
    //J'ai pensé à un dictionnaire pour le sexe que la personne recherche
    //ça serait un truc du genre:
    //push
    public static TreeSet<Profil> matching1(Profil p1,double tailleminimale,ArrayList<String> recherchequal
    ,ArrayList<String> pasdefaut,ArrayList<String> S_hobbies) throws Exception {
        Comparator<Profil> compat = Comparator.comparing((Profil p) -> p.compatibilité, Comparator.reverseOrder())
                                      .thenComparing(p -> p.compareTo(p1)).thenComparing(p -> p.nom);
        //Je trie le tree (c'est trop marrant)
        //plus sérieusement ça sert à avoir l'ensemble avec les plus compatibles en premier,puis les plus près.
        //La comparaison en fonction du nom permet d'éviter que 2 personnes avec la même compatibilité et dans la même
        //ville ne soit compté comme un doublon.
        TreeSet<Profil> match=new TreeSet<>(compat);
        //là je fais moi même les listes de ce que la personne attend.
        //TODO A terme, tout ce qui concerne le tri par genre sera ailleurs (on va pas trier à chaque fois ^^)
        Comparator<Profil> distanceComparator = Comparator.comparingDouble(p -> p.taille);
        Generateur_profil g=new Generateur_profil();
        HashMap<String,TreeSet<Profil>> trigenre=new HashMap<>();
        for(Profil profil: g.listeProfil){
            String genre=profil.genre;
            if(!trigenre.containsKey(genre)){
                trigenre.put(genre, new TreeSet<>(distanceComparator));
            }
            trigenre.get(genre).add(profil);

        }
        TreeSet<Profil>rechercher=trigenre.get(p1.recherche);

        for(Profil profil:rechercher) {
            if (profil.recherche.equals(p1.genre) && profil.taille>=tailleminimale) {
                //Si la personne trouvée n'aime pas le genre de la personne, ça sert à rien de la mettre ^^
                profil.compatibilité = 0; //J'aurai pu le faire plus haut mais déjà que c'est pas opti on va pas pousser x)
                //TODO Maintenant, j'attribue des points à chaque fois qu'un critère concorde: (c'est super crade)
                //TODO Après c'est le même principe pour défaut/hobbies mais la méthode me va pas(3 boucles imbriquées c'est
                //TODO Pas dingue...) en plus il faut réinitialiser profil.compatibilité à chaque fois c'est l'enfer...

                for (String qualsearch : recherchequal) {
                    for (String qual : profil.qualite) {
                        if (qualsearch.equals(qual)) {
                            profil.compatibilité++; //TODO Au lieu de +1/-1 on pourrait faire en sorte que la personne
                                                    //TODO choisisse l'importance des hobbies/qualité/defaut
                        }
                    }
                }
                for (String notdefaut : pasdefaut) {
                    for (String def : profil.defaut) {
                        if (notdefaut.equals(def)) {
                            profil.compatibilité--;
                        }
                    }
                }
                for (String hobsearch : S_hobbies) {
                    for (String hob : profil.hobbies) {
                        if (hobsearch.equals(hob)) {
                            profil.compatibilité++;
                        }
                    }
                }
                match.add(profil);
            }
        }
        return match;
        }



    public static void main(String[] args) throws Exception {
        Profil p1 = new Profil("CHARLIES", "Tom", "01/03/1999", Genre.HOMME.name(), Statut.CELIBATAIRE.name(), "Bordeaux", Genre.HOMME.name());
        ArrayList<String> S_qual = new ArrayList<>(Arrays.asList("Joyeux", "Attentionné", "Cultivé"));
        ArrayList<String> S_def = new ArrayList<>(Arrays.asList("Gaffeur","Bavard"));
        ArrayList<String> S_hob = new ArrayList<>(Arrays.asList("Sport","Art","Culture","Cinéma"));
                for(Profil p:matching1(p1,1.2,S_qual,S_def,S_hob)){
            System.out.println(p.compatibilité);
            System.out.println(p.compareTo(p1));
            System.out.println(p);
            System.out.println(" ");
        }

    }
}
