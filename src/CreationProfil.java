import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreationProfil extends Application {
    Modele mod;
    public CreationProfil(){
        super();
    }
    public CreationProfil(Modele mod){
        this.mod=mod;
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("creationp.fxml"));
        Parent root = loader.load();
        CreationProfilControleur controleur =new CreationProfilControleur(mod);
        controleur.initialisationComboBox();
        controleur.init();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.show();
        //stage.setResizable(false);
        stage.setTitle("Créer un nouveau profil");


    }

    public static void main(String[] args) {
        launch(args);
    }
}
