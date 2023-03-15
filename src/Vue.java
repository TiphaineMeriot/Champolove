import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Vue extends Application {
    Button boutonCreerProfil;
    String profilRecherche;
    String profilClick;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(CreationProfil.class.getResource("Vue.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200 , 800);
        stage.setResizable(false);
        stage.setTitle("Vue");
        stage.setScene(scene);
        stage.show();

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
        stage.getIcons().add(new Image("images/logo.png"));

        //boucle pour ajouter les profils dans la gridpane
        GridPane gridPane = (GridPane) scene.lookup("#GridProfils");
        for (int i = 0; i < 5; i++) {
            Label label = new Label("Label " + i);
            //setup le texte du label
            label.setText("Profil " + i);
            gridPane.add(label, 1, i);
        }
        //charger l'imageView d'id "BigImage"
        javafx.scene.image.ImageView imageview = (javafx.scene.image.ImageView) scene.lookup("#BigImage");
        //charger l'image
        Image img = new Image("images/pdp.png");
        //setup l'image
        imageview.setImage(img);

        //charger le Label d'id "BigLabel" avec le String profilClick
        Label label = (Label) scene.lookup("#BigLabel");
        label.setText(profilClick);

        //boucle pour ajouter des ImagesView dans la gridpane
        for (int i = 0; i < 5; i++) {
            Image image = new Image("images/pdp.png");
            javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
            imageView.setFitHeight(70);
            imageView.setFitWidth(70);
            gridPane.add(imageView, 0, i);
        }

        //boucle pour ajouter des progressbar dans la gridpane
        for (int i = 0; i < 5; i++) {
            //nombre random entre 0 et 1
            double random = Math.random();
            javafx.scene.control.ProgressBar progressBar = new javafx.scene.control.ProgressBar();
            progressBar.setProgress(random);
            gridPane.add(progressBar, 2, i);
        }



    }



    public static void main(String[] args) {
        launch();

    }
}
