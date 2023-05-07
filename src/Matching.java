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

    //Teste si le profil p2 correspond au profil p1
    public double correspond(Profil p1,Profil p2){
        int agemin=p1.exi.agemin;
        int agemax=p1.exi.agemax;
        HashSet<String> recherchequal=p1.exi.choix_qualite;
        HashSet<String> S_hobbies=p1.exi.choix_hobbies;
        HashSet<String> pasdefaut=p1.exi.choix_defaut;
        int compatibilite = 0;
        if (agemin<=p2.age && p2.age<=agemax){
            compatibilite+=10;
        }
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
        return (compatibilite/(10+S_hobbies.size()+pasdefaut.size()+recherchequal.size()));
    }
    public Matching(Modele mod){
        Matching.mod =mod;
    }
//    public TreeSet<Profil> matching1(Profil p1){
//
//        //Je mets des termes plus courts pour pas refaire à chaque fois le même appel
//        HashSet<String> recherchequal=p1.exi.choix_qualite;
//        HashSet<String> S_hobbies=p1.exi.choix_hobbies;
//        HashSet<String> pasdefaut=p1.exi.choix_defaut;
//        HashSet<String> attirance=p1.exi.attirance;
//        int agemin=p1.exi.agemin;
//        int agemax=p1.exi.agemax;
//        int distancemax=p1.exi.distance;
//        ///
//
//        p1.actif=false; // J'enlève le profil de la liste des profils trouvés (on sait jamais).
//
//        //Je trie l'arbre en fonction des plus compatibles puis les plus près puis les noms et enfin les prénoms,
//        //Afin d'être sûr que 2 personnes ayant la même compatibilité et dans la même ville ne soit compté comme doublons.
//        Comparator<ScoreCompatibilite> compat = Comparator.comparingDouble(ScoreCompatibilite::getScore).reversed()
//                .thenComparing(sc->sc.profil.compareTo(p1))
//                .thenComparing(sc -> sc.profil.nom)
//                .thenComparing(sc->sc.profil.prenom);
//
//        TreeSet<ScoreCompatibilite> match=new TreeSet<>(compat);
//        ///
//
//        for(Profil profil: mod.listeProfil){
//            String genre=profil.genre;
//            if(!mod.tripargenre.containsKey(genre)){
//                mod.tripargenre.put(genre, new TreeSet<>());
//            }
//            mod.tripargenre.get(genre).add(profil);
//        }
//        for(String genrer:attirance) {
//            TreeSet<Profil> rechercher = mod.tripargenre.get(genrer);
//            for (Profil profil : rechercher) {
//                //Teste si la personne est active + si elle satisfait toutes les exigences + si elle est attirée par le genre de p1
//                if (profil.actif && condition_match(profil,p1)) {
//                    double alpha = 1.3;
//                    double compatibilitep1 = (correspond(p1,profil) / (p1.exi.choix_hobbies.size() + p1.exi.choix_qualite.size()));
//                    double compatibilitepro= (correspond(profil,p1) /(profil.exi.choix_hobbies.size()+profil.exi.choix_qualite.size()));
//                    double compatibilite = Math.pow(alpha, (compatibilitep1+compatibilitepro)/2);
//                    compatibilite = (compatibilite / alpha);
//                    ScoreCompatibilite sc = new ScoreCompatibilite(profil, compatibilite);
//                    sc.profil.compatibilité = (int) compatibilite;
//                    match.add(sc);
//                }
//            }
//        }
//        TreeSet<Profil> resultat=new TreeSet<>();
//        for(ScoreCompatibilite scorec:match){
//            resultat.add(scorec.getProfil());
//        }
//        p1.actif=true;
//        return resultat;
//        }

    public TreeSet<Profil> matching2(Profil p1){
        TreeSet<Profil> resultat=new TreeSet<>();
        int ageMin = p1.exi.agemin;
        int scoremax = 0;


        // le but de cette fonction est de trouver les profils qui correspondent à p1 en se basant
        // sur les critères d'exigences de p1 ex p1.exi.agemin,p1.exi.choix_qualite,p1.exi.choix_hobbies,p1.exi.choix_defaut
        // et de les trier par score de compatibilité
        for (Profil profil : mod.listeProfil) {
            // on vérifie si le profil est du genre de p1.exi.attirance et s'il est actif
            if (p1.exi.attirance.contains(profil.genre) && profil.actif && p1!=profil) { //p1!=profil est inutile je te dirai pourquoi ^^
                // on calcule le score de compatibilité
                int score = 25;
                for(String q:p1.exi.choix_qualite){
                    if(profil.qualite.contains(q)){
                        score+=20;
                    }
                }
                for(String q:p1.exi.choix_defaut){
                    if(profil.defaut.contains(q)){
                        score-=20;
                    }
                }
                for(String h:p1.exi.choix_hobbies){
                    if(profil.hobbies.contains(h)){
                        score+=20;
                    }
                }
                // si la distance est prise en compte par l'un des deux profils
                // on enleve -10 si celle ci n'est pas respectée
                if (p1.exi.distance != 0 || profil.exi.distance != 0) {
                    if (p1.exi.distance != 0 && profil.exi.distance != 0) {
                        // on prend la plus petite distance --> celle du plus exigeant des deux
                        if (p1.exi.distance < profil.exi.distance) {
                            // puis on verifie si la distance est respectée
                            if (p1.exi.distance < profil.compareTo(p1)) {
                                score += 10;
                            }else{
                                score-=10;
                            }
                        } else {
                            if (profil.exi.distance < p1.compareTo(profil)) {
                                score += 10;
                            }else{
                                score-=10;
                            }
                        }}
                }
                // si l'age min / l'age max est pris en compte par p1
                // on enleve -10 si celui ci n'est pas respecté
                if(p1.exi.agemax!=0){
                    if(profil.age>p1.exi.agemax){
                        score-=10;
                    }else {
                        score += 10;
                    }
                }
                if(p1.exi.agemin!=0){
                    if(profil.age<p1.exi.agemin){
                        score-=10;
                    }else {
                        score += 10;
                    }
                }
                if (score > scoremax) {
                    scoremax = score;
                }
                profil.compatibilité = score;
                resultat.add(profil);
            }
        }

        // pour chaque profil on ramene le score de compatibilité sur 100 a l'aide du scoremax
        for (Profil pro : resultat) {
            pro.compatibilité = (pro.compatibilité * 99) / scoremax;
        }

        return resultat;
    }
    public TreeSet<Profil> matching1v2(Profil p1){
        //Je mets des termes plus courts pour pas refaire à chaque fois le même appel
        HashSet<String> recherchequal=p1.exi.choix_qualite;
        HashSet<String> S_hobbies=p1.exi.choix_hobbies;
        HashSet<String> pasdefaut=p1.exi.choix_defaut;
        HashSet<String> attirance=p1.exi.attirance;
        int agemin=p1.exi.agemin;
        int agemax=p1.exi.agemax;
        int distancemax=p1.exi.distance;
        p1.actif=false; // J'enlève le profil de la liste des profils trouvés (on sait jamais).
        ///

        //Je trie l'arbre en fonction des plus compatibles puis les plus près puis les noms et enfin les prénoms,
        //Afin d'être sûr que 2 personnes ayant la même compatibilité et dans la même ville ne soit compté comme doublons.
        Comparator<ScoreCompatibilite> compat = Comparator.comparingDouble(ScoreCompatibilite::getScore).reversed()
                .thenComparing(sc->sc.profil.compareTo(p1))
                .thenComparing(sc -> sc.profil.nom)
                .thenComparing(sc->sc.profil.prenom);

        TreeSet<ScoreCompatibilite> match=new TreeSet<>(compat);
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
                if (profil.actif && profil.exi.attirance.contains(p1.genre)){
                    double compatp1=correspond(p1,profil);
                    double compatpro=correspond(profil,p1);
                    double compatdist=0;
                    double maxcompatdist=1;
                    if (p1.exi.distance != 0 || profil.exi.distance != 0) {
                        maxcompatdist=20;
                        if (p1.exi.distance <= profil.exi.distance) {
                            // puis on verifie si la distance est respectée
                            if (p1.exi.distance <= profil.compareTo(p1)) {
                                compatdist += 10;
                            }else{
                                compatdist-=10;
                            }
                        } else {
                            if (profil.exi.distance <= p1.compareTo(profil)) {
                                compatdist += 10;
                            }else{
                                compatdist-=10;
                            }
                        }
                    }
                    double compatibilite=(compatp1+compatpro+(compatdist/maxcompatdist))/3;
                    ScoreCompatibilite sc=new ScoreCompatibilite(profil,compatibilite);
                    sc.profil.compatibilité=(int) compatibilite;
                    match.add(sc);
                }
            }
         }

        ///
        TreeSet<Profil> resultat=new TreeSet<>();
        for(ScoreCompatibilite scorec:match){
            resultat.add(scorec.getProfil());
        }
        p1.actif=true;
        return resultat;
    }



    public static void main(String[] args) throws Exception {

    }
}
