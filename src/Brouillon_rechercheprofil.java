import java.util.Scanner;

public class Brouillon_rechercheprofil {
    //ça c'est la version basique sans réelle réflexion donc on verra mais ça fonctionne c'est déjà ça x)
    //Le must ça serait de faire un arbre de recherche mais jsp si j'aurai le temps pour le coup...
    public static void main(String[] args) throws Exception {
        Modele mod=new Modele();
        mod.charger();
//        for(Profil p: mod.listeProfil){
//            System.out.println(p);
//        }
        Scanner scanner=new Scanner(System.in);
        String input=scanner.nextLine().toUpperCase();
        int compteur=0;
        for(Profil p: mod.listeProfil){
            if (p.actif && (input.contains(p.prenom.toUpperCase()) || input.contains(p.nom.toUpperCase()))){
                compteur++;
                System.out.println(p);
                System.out.println("=================================");
            }
        }
        if (compteur==0){
            System.out.println("Aucun profil ne correspond à votre requête (mal orthographié ou n'existe pas");
        }

    }
}
