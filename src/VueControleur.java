
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
    Profil profilClick;

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
            //si la souris clique sur l'image,on stocke le profil correspondant dans la variable profilClick
            imageView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent event) {
                    //TODO : Afficher le profil correspondant dans la zone de droite
                    try {
                        // on instancie un nouveau profil avec les informations du profil cliqué
                        profilClick = new Profil(profil.nom, profil.prenom, profil.date_de_naissance, profil.genre, profil.statut, profil.ville, profil.recherche);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    //on ajoute dans le pane de droite les informations de profilClick avec des labels
                    Label labelNom = new Label(profilClick.nom);
                    Label labelPrenom = new Label(profilClick.prenom);
                    Label labelGenre = new Label(profilClick.genre);
                    Label labelDateDeNaissance = new Label(profilClick.date_de_naissance);
                    Label labelStatut = new Label(profilClick.statut);
                    Label labelVille = new Label(profilClick.ville);
                    Label labelRecherche = new Label(profilClick.recherche);

                    // on clear le pane de droite
                    GridPane gridPaneDroite = (GridPane) scene.lookup("#GridPaneDroite");
                    gridPaneDroite.getChildren().clear();
                    //on ajoute les labels dans le pane de droite

                    gridPaneDroite.add(labelNom, 0, 0);
                    gridPaneDroite.add(labelPrenom, 0, 1);
                    gridPaneDroite.add(labelGenre, 0, 2);
                    gridPaneDroite.add(labelDateDeNaissance, 0, 3);
                    gridPaneDroite.add(labelStatut, 0, 4);
                    gridPaneDroite.add(labelVille, 0, 5);
                    gridPaneDroite.add(labelRecherche, 0, 6);

                }
            });
            i++;
        }

        //TODO : Ajouter un eventlistener sur les images pour afficher le profil correspondant dans la zone de droite



    }
}
