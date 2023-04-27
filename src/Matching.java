import java.util.*;

public class Matching{
    //TODO Je me suis rendu compte qu'il fallait que ça marche dans les 2 sens pour qu'il y ait matching x)
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
    //TODO: On enlèvera cette partie quand on aura fait les tests et qu'on aura implanté matching
    public void print(HashSet<String> set){
        for(String s:set){
            System.out.print(s+",");
        }
        System.out.println();
    }
    ///

    //Teste si le match entre 2 personnes est possible en fonction de leurs exigences.
    public boolean condition_match(Profil p,Profil p1){
        int agemin=p1.exi.agemin;
        int agemax=p1.exi.agemax;
        int distancemax=p1.exi.distance;
        int ageminp=p.exi.agemin;
        int agemaxp=p.exi.agemax;
        int distancemaxp=p.exi.distance;
        boolean exi_p1=agemin<=p.age && p.age<=agemax && p1.compareTo(p)<=distancemax;
        boolean exi_p=p.exi.attirance.contains(p1.genre)
                && ageminp<=p1.age && p1.age<=agemaxp && p.compareTo(p1)<=distancemaxp;
        return exi_p && exi_p1;
    }
    ///

    //Teste si le profil p2 correspond au profil p1
    public double correspond(Profil p1,Profil p2){
        HashSet<String> recherchequal=p1.exi.choix_qualite;
        HashSet<String> S_hobbies=p1.exi.choix_hobbies;
        HashSet<String> pasdefaut=p1.exi.choix_defaut;
        double compatibilite = 0;
        for (String qualsearch : recherchequal) {
            if (p2.qualite.contains(qualsearch)) {
                compatibilite++;
            }
        }
        for (String notdefaut : pasdefaut) {
            if (p2.defaut.contains(notdefaut)) {
                compatibilite--;
            }
        }
        for (String hobsearch : S_hobbies) {
            if (p2.hobbies.contains(hobsearch)) {
                compatibilite++;
            }
        }
        return compatibilite;
    }
    public Matching(Modele mod){
        Matching.mod =mod;
    }
    //On dit que c'est une liste les qualités et défauts
    //TODO Il faut faire une option avec ce que la personne recherche
    //TODO Genre cherche homme >1.80m qui aime la musique et attentionné.
    //TODO Je ne veux pas qu'il soit con.
    //J'ai pensé à un dictionnaire pour le sexe que la personne recherche
    //ça serait un truc du genre:
    public TreeSet<Profil> matching1(Profil p1){

        //Je mets des termes plus courts pour pas refaire à chaque fois le même appel
        HashSet<String> recherchequal=p1.exi.choix_qualite;
        HashSet<String> S_hobbies=p1.exi.choix_hobbies;
        HashSet<String> pasdefaut=p1.exi.choix_defaut;
        HashSet<String> attirance=p1.exi.attirance;
        int agemin=p1.exi.agemin;
        int agemax=p1.exi.agemax;
        int distancemax=p1.exi.distance;
        ///

        p1.actif=false; // J'enlève le profil de la liste des profils trouvés (on sait jamais).

        //Je trie l'arbre en fonction des plus compatibles puis les plus près puis les noms et enfin les prénoms,
        //Afin d'être sûr que 2 personnes ayant la même compatibilité et dans la même ville ne soit compté comme doublons.
        Comparator<ScoreCompatibilite> compat = Comparator.comparingDouble(ScoreCompatibilite::getScore).reversed()
                .thenComparing(sc->sc.profil.compareTo(p1))
                .thenComparing(sc -> sc.profil.nom)
                .thenComparing(sc->sc.profil.prenom);

        TreeSet<ScoreCompatibilite> match=new TreeSet<>(compat);
        ///

        for(Profil profil: mod.listeProfil){
            String genre=profil.genre;
            if(!mod.tripargenre.containsKey(genre)){
                mod.tripargenre.put(genre, new TreeSet<>());
            }
            mod.tripargenre.get(genre).add(profil);
        }
        for(String genrer:attirance) {
            TreeSet<Profil> rechercher = mod.tripargenre.get(genrer);
            for (Profil profil : rechercher) {
                //Teste si la personne est active + si elle satisfait toutes les exigences + si elle est attirée par le genre de p1
                if (profil.actif && condition_match(profil,p1)) {
                    double alpha = 1.3;
                    double compatibilitep1 = (correspond(p1,profil) / (p1.exi.choix_hobbies.size() + p1.exi.choix_qualite.size()));
                    double compatibilitepro= (correspond(profil,p1) /(profil.exi.choix_hobbies.size()+profil.exi.choix_qualite.size()));
                    double compatibilite = Math.pow(alpha, (compatibilitep1+compatibilitepro)/2);
                    compatibilite = (compatibilite / alpha);
                    ScoreCompatibilite sc = new ScoreCompatibilite(profil, compatibilite);
                    sc.profil.compatibilité = compatibilite;
                    match.add(sc);
                }
            }
        }
        TreeSet<Profil> resultat=new TreeSet<>();
        for(ScoreCompatibilite scorec:match){
            resultat.add(scorec.getProfil());
        }
        p1.actif=true;
        return resultat;
        }



    public static void main(String[] args) throws Exception {
        //TODO ce que je vais mettre sera à enlever c'est juste pour tester matching sans avoir besoin de tout refaire.
        Generateur_profil g=new Generateur_profil();
        Modele mod=new Modele();
        mod.charger();
        Matching m=new Matching(mod);
        Profil p1 = new Profil("CHARLIES", "Tom", "01/03/1999", Genre.HOMME.name(), Statut.CELIBATAIRE.name(), "Bordeaux", new HashSet<>(List.of(Genre.HOMME.name())));
        p1.calcul_latitude_longitude();
        do{
            p1.exi.agemin=g.entierAlea(18,80);
            p1.exi.agemax=g.entierAlea(20,100);
        }while (p1.exi.agemin>p1.exi.agemax);
        p1.exi.distance=1000;
        g.qualdefhobAlea(p1.exi.choix_hobbies,mod.hobbies);
        g.qualdefhobAlea(p1.exi.choix_qualite,mod.qualite);
        g.qualdefhobAlea(p1.exi.choix_defaut,mod.defaut);
        System.out.println(p1.latitude);
        System.out.println(p1.longitude);
        System.out.println("RECHERCHE");
        m.print(p1.exi.attirance);
        m.print(p1.exi.choix_qualite);
        m.print(p1.exi.choix_defaut);
        m.print(p1.exi.choix_hobbies);
        System.out.println("entre "+p1.exi.agemin+" et "+p1.exi.agemax);
        System.out.println(p1.exi.distance);
        System.out.println("///////////////////////");
        for(Profil p:m.matching1(p1)){
            System.out.println(p.compatibilité);
            System.out.println(p.compareTo(p1));
            System.out.println(p);
            System.out.println(" ");
        }
    }
}
