import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Generateur_profil {
    public Generateur_profil(Modele mod) throws Exception {
        mod.listeProfil = new ArrayList<>();
        ArrayList<String> qualite=new ArrayList<>();

        String[] statut = {"CELIBATAIRE", "MARIE", "VEUF"};
        String[] recherche = {"HOMME", "FEMME", "AUTRE"};
        for (int i = 0; i < 27; i++) {
            URL url = new URL("https://randomuser.me/api/?nat=fr&inc=name,gender,location");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream responseStream = connection.getInputStream();
            String text = new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);
            int igenre = text.indexOf("gender") + 9;
            String genre;
            //TODO Ajouter un truc aléatoire pour avoir des non binaire x)
            if (text.charAt(igenre) == 'm') {
                genre = "HOMME";
            } else {
                genre = "FEMME";
            }
            int inom = text.indexOf("last") + 7;
            String nom = "";
            while (!(text.charAt(inom) == ('"'))) {
                nom += (text.charAt(inom));
                inom++;
            }
            int iprenom = text.indexOf("first") + 8;
            String prenom = "";
            while (!(text.charAt(iprenom) == ('"'))) {
                prenom += (text.charAt(iprenom));
                iprenom++;
            }
            int ilocation = text.indexOf("city") + 7;
            String location = "";
            while (!(text.charAt(ilocation) == ('"'))) {
                location += (text.charAt(ilocation));
                ilocation++;
            }
            int year = new Random().nextInt(1950, 2005);
            int month = new Random().nextInt(1, 12);
            int day = new Random().nextInt(1, 28); //TODO: faire un jour plus réaliste parce que pour l'instant on peut pas être né un 30 x)
            String ddn = String.format("%d/%d/%d", day, month, year);
            Profil p = new Profil(nom, prenom, ddn, genre, statut[new Random().nextInt(statut.length)], location,
                    recherche[new Random().nextInt(recherche.length)]);
//            System.out.println(p);
            Random r = new Random();
            System.out.println(p.genre);
            Path relativePath = Paths.get("src", "images", p.genre, String.format("%s_%s.jpeg", p.nom, p.prenom));
            Path absolutePath = relativePath.toAbsolutePath();
            File f = new File(absolutePath.toString());
            System.out.println(f);
            if (Objects.equals(p.genre, "HOMME")) {
                int rdm = r.nextInt(mod.listeImageH.size());
                mod.listeImageH.get(rdm).renameTo(f);
                mod.listeImageH.remove(mod.listeImageH.get(rdm));
                p.taille=r.nextDouble(1.6,2);
            } else if (Objects.equals(p.genre, "FEMME")) {
                int rdm = r.nextInt(mod.listeImageF.size());
                mod.listeImageF.get(rdm).renameTo(f);
                mod.listeImageF.remove(mod.listeImageF.get(rdm));
                p.taille=r.nextDouble(1.5,1.8);
            } else {
                int piece = r.nextInt(2);
                if (piece == 0) {
                    int rdm = r.nextInt(mod.listeImageF.size());
                    mod.listeImageF.get(rdm).renameTo(f);
                    mod.listeImageF.remove(mod.listeImageF.get(rdm));
                    p.taille=r.nextDouble(1.5,1.8);
                } else {
                    int rdm = r.nextInt(mod.listeImageH.size());
                    mod.listeImageH.get(rdm).renameTo(f);
                    mod.listeImageH.remove(mod.listeImageH.get(rdm));
                    p.taille=r.nextDouble(1.6,2);
                }
            }
            for(int j=0;j<r.nextInt(1,4);j++){
                int ind=r.nextInt(mod.qualite.size());
                p.qualite.add(mod.qualite.get(ind));
            }
            for(int j=0;j<r.nextInt(1,4);j++){
                int ind=r.nextInt(mod.defaut.size());
                p.defaut.add(mod.defaut.get(ind));
            }
            mod.listeProfil.add(p);
            System.out.println(p);
        }
    }
}
