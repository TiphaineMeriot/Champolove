package vue;

import modele.Modele;

import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

public class Recherche_Profil {
    Modele mod;
    public Recherche_Profil(Modele mod){
        this.mod=mod;
    }
        public class Precision {
        private Profil profil;
        private int precision;

        public Precision(Profil p) {
            this.profil = p;
            this.precision=0;
        }

        //Getter
        public Profil getProfil() {
            return profil;
        }

        public int getPrecision() {
            return precision;
        }
        public double getCompatibilite(){ return profil.compatibilité;}
    }
    //ça c'est la version basique sans réelle réflexion donc on verra mais ça fonctionne c'est déjà ça x)
    //Le must ça serait de faire un arbre de recherche mais jsp si j'aurai le temps pour le coup...
    public TreeSet<Profil> rechercheProfil(String input){
            input=input.toUpperCase();
        //Je trie l'arbre en fonction des plus précis puis  puis les noms et enfin les prénoms,
        //Afin d'être sûr que 2 personnes ayant la même précision ne soit compté comme doublons.
       Comparator<Precision> prec = Comparator.comparingInt(Precision::getPrecision).reversed()
                .thenComparing(Comparator.comparingDouble(Precision:: getCompatibilite).reversed())
                .thenComparing(pr -> pr.profil.nom)
                .thenComparing(pr -> pr.profil.prenom);
        TreeSet<Precision> recherche=new TreeSet<>(prec);
        for(Profil p: mod.listeProfil){
            if (p.actif){
                Precision pr=new Precision(p);
                // Divisez l'entrée en mots
                String[] inputWords = input.split("\\+");

                // Parcourez chaque mot de l'entrée
                for (String word : inputWords) {
                    if (word.equals(p.signe.toUpperCase())) {
                        pr.precision++;
                        System.out.println("s" + word);
                    }
                    if (word.equals(p.profession.toUpperCase())) {
                        pr.precision++;
                        System.out.println("p" + word);
                    }
                    if (word.equals(p.prenom.toUpperCase())) {
                        pr.precision++;
                        System.out.println("pr" + word);
                    }
                    if (word.equals(p.nom.toUpperCase())) {
                        pr.precision++;
                        System.out.println("n" + word);
                    }
                    for (String s : p.hobbies) {
                        if (word.equals(s.toUpperCase())) {
                            pr.precision++;
                            System.out.println("h" + word);
                        }
                    }
                    for (String s : p.qualite) {
                        if (word.equals(s.toUpperCase())) {
                            pr.precision++;
                            System.out.println("q" + word);
                        }
                    }
                    if (word.equals(p.ville.toUpperCase())){
                        pr.precision++;
                        System.out.println("v"+word);
                    }
                }

                p.precision=pr.precision;
                recherche.add(pr);
            }
        }
        TreeSet<Profil> trouve=new TreeSet<>();
        for (Precision pr:recherche){
            trouve.add(pr.profil);
        }
        return trouve;
    }
    public static void main(String[] args) throws Exception {
        Modele mod=new Modele();
        Recherche_Profil b=new Recherche_Profil(mod);
        mod.charger();
//        for(vue.Profil p: mod.listeProfil){
//            System.out.println(p);
//        }
        Scanner scanner=new Scanner(System.in);
        String input=scanner.nextLine().toUpperCase();
        for(Profil p:b.rechercheProfil(input)){
            System.out.println(p);
        }
    }
}
