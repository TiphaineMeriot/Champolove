package vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreationProfil extends Application {

        @Override
        public void start(Stage stage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/creationp.fxml"));
            Parent root = loader.load();
            controleur.CreationProfilControleur c = loader.getController();
            c.initialisationComboBox();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);
            stage.setTitle("Créer un nouveau profil");


        }

        public static void main(String[] args) {
            launch(args);
        }
    }