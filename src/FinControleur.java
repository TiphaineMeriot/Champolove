import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FinControleur {
    Modele mod;
    Profil profil1;
    Profil profil2;


    public FinControleur(Modele mod, Profil profil1, Profil profil2) {
        this.mod = mod;
        this.profil1 = profil1;
        this.profil2 = profil2;
    }
    public void init(Scene scene, Stage stage) throws Exception{
        // on recupere le label d'id txt1
        // on recupere le label d'id txt2


        Label txt1 = (Label) scene.lookup("#txt1");
        Label txt2 = (Label) scene.lookup("#txt2");

        // on les set avec les prenoms nom des profils
        txt1.setText(profil1.prenom + " " + profil1.nom);
        txt2.setText(profil2.prenom + " " + profil2.nom);

        // on recupere les buttons d'id selection et d'id quitter
        Button selection = (Button) scene.lookup("#selection");
        Button quitter = (Button) scene.lookup("#quitter");

        // si selection est cliqué, on lance la vue selection
        selection.setOnAction(event -> {
            Selection selection1 = new Selection(mod);
            try {
                selection1.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // si quitter est cliqué, on ferme le stage et on arrete le programme
        quitter.setOnAction(event -> {
            stage.close();
            System.exit(0);
        });
    }
}
