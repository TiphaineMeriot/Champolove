package vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modele.Modele;

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
        FXMLLoader fxmlLoader = new FXMLLoader(CreationProfil.class.getResource("fxml/Edition.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 965 , 599);
        stage.setResizable(false);
        stage.setTitle("vue.Vue");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        controleur.EditionController controller = new controleur.EditionController(this.mod, this.profil);
        controller.init(scene, stage);
    }
}

