import com.sun.webkit.Timer;

import java.math.BigDecimal;
import java.util.*;

public class Matching{
    static Modele mod;
    public class ScoreCompatibilite{
        private Profil profil;
        private double score;
        public ScoreCompatibilite(Profil p,double s){
            this.profil=p;
            this.score=s;
        }
        //Getter
        public Profil getProfil(){
            return profil;
        }
        public double getScore(){
            return score;
        }
    }
    private double compatibilite;
    //TODO: On enlèvera cette partie quand on aura fait les tests et qu'on aura implanté matching
    public void print(HashSet<String> set){
        for(String s:set){
            System.out.print(s+",");
        }
        System.out.println();
    }
    ////
    public Matching(Modele mod){
        Matching.mod =mod;
    }
    //On dit que c'est une liste les qualités et défauts
    //TODO Il faut faire une option avec ce que la personne recherche
    //TODO Genre cherche homme >1.80m qui aime la musique et attentionné.
    //TODO Je ne veux pas qu'il soit con.
    //J'ai pensé à un dictionnaire pour le sexe que la personne recherche
    //ça serait un truc du genre:
    public TreeSet<Profil> matching1(Profil p1, HashSet<String> recherchequal
            , HashSet<String> pasdefaut, HashSet<String> S_hobbies) {
        Comparator<ScoreCompatibilite> compat = Comparator.comparingDouble(ScoreCompatibilite::getScore).reversed()
                .thenComparing(sc->sc.profil.compareTo(p1))
                .thenComparing(sc -> sc.profil.nom)
                .thenComparing(sc->sc.profil.prenom);
        //Je trie le tree (c'est trop marrant)
        //plus sérieusement ça sert à avoir l'ensemble avec les plus compatibles en premier,puis les plus près.
        //La comparaison en fonction du nom permet d'éviter que 2 personnes avec la même compatibilité et dans la même
        //ville ne soit compté comme un doublon.
        TreeSet<ScoreCompatibilite> match=new TreeSet<>(compat);

        //TODO mettre ça ailleurs
        mod.tripargenre=new HashMap<>();
        for(Profil profil: mod.listeProfil){
            String genre=profil.genre;
            if(!mod.tripargenre.containsKey(genre)){
                mod.tripargenre.put(genre, new TreeSet<>());
            }
            mod.tripargenre.get(genre).add(profil);
        }
        TreeSet<Profil>rechercher=mod.tripargenre.get(p1.recherche);
        ///

        for(Profil profil:rechercher) {
            //Si la personne trouvée n'aime pas le genre de la personne, ça sert à rien de la mettre ^^
            if (profil.recherche.equals(p1.genre)) {
                double compatibilite = 0;
                for (String qualsearch : recherchequal) {
                    if (profil.qualite.contains(qualsearch)) {
                        compatibilite++;
                    }
                }
                for (String notdefaut : pasdefaut) {
                    if (profil.defaut.contains(pasdefaut)) {
                        compatibilite--;
                    }
                }
                for (String hobsearch : S_hobbies) {
                    if (profil.hobbies.contains(S_hobbies)) {
                        compatibilite++;
                    }
                }
                compatibilite = (compatibilite / (S_hobbies.size() + recherchequal.size())) + 0.5; //On pipe un peu x)
                ScoreCompatibilite sc=new ScoreCompatibilite(profil,compatibilite);
                sc.profil.compatibilité=compatibilite;
                match.add(sc);
            }
        }
        TreeSet<Profil> resultat=new TreeSet<>();
        for(ScoreCompatibilite scorec:match){
            resultat.add(scorec.getProfil());
        }
        return resultat;
        }



    public static void main(String[] args) throws Exception {
        //TODO ce que je vais mettre sera à enlever c'est juste pour tester matching sans avoir besoin de tout refaire.
        Generateur_profil g=new Generateur_profil();
        Modele mod=new Modele();
        mod.charger();
        Matching m=new Matching(mod);
        Profil p1 = new Profil("CHARLIES", "Tom", "01/03/1999", Genre.HOMME.name(), Statut.CELIBATAIRE.name(), "Bordeaux", Genre.HOMME.name());
        HashSet<String> S_qual = new HashSet<>();
        HashSet<String> S_def = new HashSet<>();
        HashSet<String> S_hob = new HashSet<>();
        g.qualdefhobAlea(S_qual,mod.qualite);
        g.qualdefhobAlea(S_def,mod.defaut);
        g.qualdefhobAlea(S_hob,mod.hobbies);
        System.out.println("RECHERCHE");
        m.print(S_qual);
        m.print(S_def);
        m.print(S_hob);
        System.out.println("///////////////////////");
        for(Profil p:m.matching1(p1,S_qual,S_def,S_hob)){
            System.out.println(p.compatibilité);
            System.out.println(p.compareTo(p1));
            System.out.println(p);
            System.out.println(" ");
        }
    }
}
