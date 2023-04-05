
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
                    try {
                        // on instancie un nouveau profil avec les informations du profil cliqué
                        profilClick = new Profil(profil.nom, profil.prenom, profil.date_de_naissance, profil.genre, profil.statut, profil.ville, profil.recherche);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    // on ajoute dans le pane de droite les informations de profilClick avec des labels qui ont la police d'ecriture : Cambria
                    // on créé un label nomPrenom qui contient le nom et le prenom du profil cliqué avec la police d'ecriture : Cambria et un espace entre le nom et le prenom
                    Label labelNomPrenom = new Label(profilClick.prenom + " " + profilClick.nom);
                    labelNomPrenom.setStyle("-fx-font-family: Cambria");
                    //label date de Naissance qui contient la date de naissance et l'age du profil cliqué avec la police d'ecriture : Cambria et un espace entre la date de naissance et l'age
                    Label labelDateDeNaissance = new Label(profilClick.date_de_naissance + " (" + profilClick.age + " ans)");
                    labelDateDeNaissance.setStyle("-fx-font-family: Cambria");


                    //le label nomPrenom a une police de titre de taille 24 et est centré et en gras
                    labelNomPrenom.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-alignment: center;");
                    //le label date de naissance a une police de titre de taille 16 et est positionné a droite et en gras;
                    labelDateDeNaissance.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-alignment: center-right;");

                    GridPane gridPaneDroite = (GridPane) scene.lookup("#GridPaneDroite");
                    gridPaneDroite.add(labelNomPrenom, 1, 0);
                    gridPaneDroite.add(labelDateDeNaissance , 1, 1);

                }
            });
            i++;
        }



    }
}
