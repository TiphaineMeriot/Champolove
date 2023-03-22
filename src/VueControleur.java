
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class VueControleur {

    Button boutonCreerProfil;
    String profilRecherche;
    String profilClick;

    Generateur_profil generateurProfil;

    public void init(Scene scene, Stage stage) throws Exception {
        boutonCreerProfil = (Button) scene.lookup("#boutonCreerProfil");
        boutonCreerProfil.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    CreationProfil creationProfil = new CreationProfil();
                    creationProfil.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //stocker le texte de la zone de recherche dans la variable profilRecherche
        profilRecherche = String.valueOf(scene.lookup("#ZonedeRecherche"));

        //ajout de l'icone
        stage.getIcons().add(new Image("images/logo_invisible.png"));

        generateurProfil = new Generateur_profil();
        GridPane gridPane = (GridPane) scene.lookup("#GridProfils");

        //Ajout des profils dans le gridPane
        int i = 1;
        for (Profil profil : generateurProfil.listeProfil) {
            Label labelNomPrenom = new Label(profil.nom+" "+profil.prenom);
            //TODO : La ligne d'en dessous sera quand les images porteront le nom et le prénom du profil
            //Image image = new Image("images/" + profil.nom + "_" + profil.prenom + ".jpg");
            String indice = String.valueOf(i+1);
            Image image = new Image("images/"+indice+".jpeg");
            gridPane.add(labelNomPrenom, 1, i);
            //charger l'image dans l'imageview d'id ImageView
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(70);
            imageView.setFitWidth(70);
            gridPane.add(imageView, 0, i);
            i++;
        }

        //TODO : Ajouter un eventlistener sur les images pour afficher le profil correspondant dans la zone de droite


    }
}
