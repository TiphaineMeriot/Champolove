import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;


public class CreationProfil extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("creationp.fxml"));
        Parent root = loader.load();
        CreationProfilControleur controleur = loader.getController();
        controleur.initialisationComboBox();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setResizable(false);
        stage.setTitle("Créer un nouveau profil");

    }

    public static void main(String[] args) {
        launch(args);
    }
}
