import java.util.*;

public class Matching {
    static Modele mod;

    public class ScoreCompatibilite {
        private Profil profil;
        private double score;

        public ScoreCompatibilite(Profil p, double s) {
            this.profil = p;
            this.score = s;
        }

        //Getter
        public Profil getProfil() {
            return profil;
        }

        public double getScore() {
            return score;
        }
    }
    //Teste si le profil p2 correspond au profil p1
    public double correspondV2(Profil p1, Profil p2) {
        double compatage = 0.5;
        int ageminp1 = p1.exi.agemin;
        int agemaxp1 = p1.exi.agemax;
        if (ageminp1 != 0 && agemaxp1 != 0) {
            if (ageminp1 > p2.age || p2.age > agemaxp1) {
                if (agemaxp1 < p2.age) {
                    double excesage = (double) (p2.age - agemaxp1) / agemaxp1;
                    compatage = -1 * excesage;
                } else {
                    compatage = (double) (p2.age - ageminp1) / ageminp1;
                }
            }
        }
        double qualiteJaccard = jaccard(p1.exi.choix_qualite, p2.qualite);
        double defautJaccard = jaccard(p1.exi.choix_defaut, p2.defaut);
        double hobbiesJaccard = jaccard(p1.exi.choix_hobbies, p2.hobbies);
        double poidsQualite = 0.25; //TODO Changer les poids
        double poidsDefaut = 0.25;
        double poidsHobbies = 0.25;
        double poidsAge = 0.25;
        double poidsDist = 0.25;
        double compatdist;
        System.out.println("/////////////////////////////////////////////////////////////");
        System.out.println("age:" + compatage * 0.25);
        System.out.println("qual:" + qualiteJaccard * 0.25);
        System.out.println("def:" + defautJaccard * 0.25);
        System.out.println("hob:" + hobbiesJaccard * 0.25);
        double distanceMaxPoss;
        if (p1.exi.distance != 0 && p2.exi.distance != 0) {
            distanceMaxPoss = Math.min(p1.exi.distance, p2.exi.distance);
        } else {
            distanceMaxPoss = Math.max(p1.exi.distance, p2.exi.distance);
        }
        if (distanceMaxPoss != 0 && p1.compareTo(p2) > distanceMaxPoss) {
            double excesdistance = (p1.compareTo(p2) - distanceMaxPoss) / distanceMaxPoss;
            compatdist = -1 * excesdistance;
        } else if (distanceMaxPoss != 0) {
            compatdist = (-1 * (p1.compareTo(p2) - distanceMaxPoss) / distanceMaxPoss) / 2;
        } else {
            compatdist = 0;
        }
        System.out.println("dist:" + compatdist);
        System.out.println("////////////////////////////////////////////////////////////");
        double auxcompat = poidsQualite * qualiteJaccard - poidsDefaut * defautJaccard + poidsHobbies * hobbiesJaccard
                + poidsAge * compatage + poidsDist * compatdist;
        int coef = 18;
        return 1 / (1 + Math.exp(-auxcompat * coef)) * 100; //sigmoid (c'est sympa pour recentrer autour de [0,100];
    }

    private double jaccard(HashSet<String> cherche, ArrayList<String> trouve) {
        Set<String> intersection = new HashSet<>(cherche);
        intersection.retainAll(trouve);
        Set<String> union = new HashSet<>(cherche);
        union.addAll(trouve);
        return (double) intersection.size() / union.size();
    }

    public Matching(Modele mod) {
        Matching.mod = mod;
    }

    public TreeSet<Profil> matching2(Profil p1) {
        TreeSet<Profil> resultat = new TreeSet<>();
        int ageMin = p1.exi.agemin;
        int scoremax = 0;


        // le but de cette fonction est de trouver les profils qui correspondent à p1 en se basant
        // sur les critères d'exigences de p1 ex p1.exi.agemin,p1.exi.choix_qualite,p1.exi.choix_hobbies,p1.exi.choix_defaut
        // et de les trier par score de compatibilité
        for (Profil profil : mod.listeProfil) {
            // on vérifie si le profil est du genre de p1.exi.attirance et s'il est actif
            if (p1.exi.attirance.contains(profil.genre) && profil.actif && p1 != profil) { //p1!=profil est inutile je te dirai pourquoi ^^
                // on calcule le score de compatibilité
                int score = 25;
                for (String q : p1.exi.choix_qualite) {
                    if (profil.qualite.contains(q)) {
                        score += 20;
                    }
                }
                for (String q : p1.exi.choix_defaut) {
                    if (profil.defaut.contains(q)) {
                        score -= 20;
                    }
                }
                for (String h : p1.exi.choix_hobbies) {
                    if (profil.hobbies.contains(h)) {
                        score += 20;
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
                            } else {
                                score -= 10;
                            }
                        } else {
                            if (profil.exi.distance < p1.compareTo(profil)) {
                                score += 10;
                            } else {
                                score -= 10;
                            }
                        }
                    }
                }
                // si l'age min / l'age max est pris en compte par p1
                // on enleve -10 si celui ci n'est pas respecté
                if (p1.exi.agemax != 0) {
                    if (profil.age > p1.exi.agemax) {
                        score -= 10;
                    } else {
                        score += 10;
                    }
                }
                if (p1.exi.agemin != 0) {
                    if (profil.age < p1.exi.agemin) {
                        score -= 10;
                    } else {
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

        // pour chaque profil on ramène le score de compatibilité sur 100 à l'aide du scoremax
        for (Profil pro : resultat) {
            pro.compatibilité = (pro.compatibilité * 99) / scoremax;
        }

        return resultat;
    }

    public TreeSet<Profil> matching1v2(Profil p1) {
        HashSet<String> attirance = p1.exi.attirance;
        p1.actif = false; // J'enlève le profil de la liste des profils trouvés (on sait jamais).
        ///

        //Je trie l'arbre en fonction des plus compatibles puis les plus près puis les noms et enfin les prénoms,
        //Afin d'être sûr que 2 personnes ayant la même compatibilité et dans la même ville ne soit compté comme doublons.
        Comparator<ScoreCompatibilite> compat = Comparator.comparingDouble(ScoreCompatibilite::getScore).reversed()
                .thenComparing(sc -> sc.profil.compareTo(p1))
                .thenComparing(sc -> sc.profil.nom)
                .thenComparing(sc -> sc.profil.prenom);

        TreeSet<ScoreCompatibilite> match = new TreeSet<>(compat);
        for (Profil profil : mod.listeProfil) {
            String genre = profil.genre;
            if (!mod.tripargenre.containsKey(genre)) {
                mod.tripargenre.put(genre, new TreeSet<>());
            }
            mod.tripargenre.get(genre).add(profil);
        }
        for (String genrer : attirance) {
            TreeSet<Profil> rechercher = mod.tripargenre.get(genrer);
            for (Profil profil : rechercher) {
                if (profil.actif && p1.exi.attirance.contains(profil.genre) && profil.exi.attirance.contains(p1.genre)) {
                    double compatibilite = (correspondV2(profil, p1) + correspondV2(p1, profil)) / 2;
                    ScoreCompatibilite sc = new ScoreCompatibilite(profil, compatibilite);
                    sc.profil.compatibilité = (int) compatibilite;
                    match.add(sc);
                }
            }
        }

        ///
        TreeSet<Profil> resultat = new TreeSet<>();
        for (ScoreCompatibilite scorec : match) {
            resultat.add(scorec.getProfil());
        }
        p1.actif = true;
        return resultat;
    }
}
