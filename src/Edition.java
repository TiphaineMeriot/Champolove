import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Edition extends Application {
    Modele mod;
    Profil profil;
    public Edition(){
        super();
    }
    public Edition(Modele mod, Profil profil) {
        this.mod = mod;
        this.profil = profil;
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(CreationProfil.class.getResource("Edition.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 807 , 496);
        stage.setResizable(false);
        stage.setTitle("Vue");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        EditionController controller = new EditionController(this.mod, this.profil);
        controller.init(scene, stage);
    }
}

