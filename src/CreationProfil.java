import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreationProfil extends Application {
    Modele mod;
    public CreationProfil(Modele mod){
        this.mod = mod;
    }

        @Override
        public void start(Stage stage) throws Exception {
            FXMLLoader fxmlLoader = new FXMLLoader(CreationProfil.class.getResource("Edition.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 965 , 599);
            stage.setResizable(false);
            stage.setTitle("Création de profil");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
            CreationProfilControleur controller = new CreationProfilControleur(this.mod);
            controller.init(scene, stage);

        }

        public static void main(String[] args) {
            launch(args);
        }
    }