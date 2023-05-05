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
    public TreeSet<Profil> matching2(Profil p1){
        TreeSet<Profil> resultat=new TreeSet<>();
        int ageMin = p1.agemin;
        int scoremax = 0;


        // le but de cette fonction est de trouver les profils qui correspondent à p1 en se basant
        // sur les critères d'exigences de p1 ex p1.agemin,p1.choix_qualite,p1.choix_hobbies,p1.choix_defaut
        // et de les trier par score de compatibilité
        for (Profil profil : mod.listeProfil) {
            // on vérifie si le profil est du genre de p1.attirance et s'il est actif
            if (p1.attirance.contains(profil.genre) && profil.actif==true) {
                // on calcule le score de compatibilité
                int score = 25;
                for(String q:p1.choix_qualite){
                    if(profil.qualite.contains(q)){
                        score+=20;
                    }
                }
                for(String q:p1.choix_defaut){
                    if(profil.defaut.contains(q)){
                        score-=20;
                    }
                }
                for(String h:p1.choix_hobbies){
                    if(profil.hobbies.contains(h)){
                        score+=20;
                    }
                }
                // si la distance est prise en compte par l'un des deux profils
                // on enleve -10 si celle ci n'est pas respectée
                if (p1.distance != 0 || profil.distance != 0) {
                    if (p1.distance != 0 && profil.distance != 0) {
                        // on prend la plus petite distance --> celle du plus exigeant des deux
                        if (p1.distance < profil.distance) {
                            // puis on verifie si la distance est respectée
                            if (p1.distance < profil.compareTo(p1)) {
                                score += 10;
                            }else{
                                score-=10;
                            }
                        } else {
                            if (profil.distance < p1.compareTo(profil)) {
                                score += 10;
                            }else{
                                score-=10;
                            }
                        }}
                }
                // si l'age min / l'age max est pris en compte par p1
                // on enleve -10 si celui ci n'est pas respecté
                if(p1.agemax!=0){
                    if(profil.age>p1.agemax){
                        score-=10;
                    }else {
                        score += 10;
                    }
                }
                if(p1.agemin!=0){
                    if(profil.age<p1.agemin){
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



    public static void main(String[] args) throws Exception {

    }
}
