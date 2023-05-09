import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Vue extends Application {
    Modele mod;
    Profil profil;
    String recherche;
    public Vue(){
        super();
    }
    public Vue(Modele mod, Profil profil,String recherche) {
        this.mod = mod;
        this.profil = profil;
        this.recherche=recherche;
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(CreationProfil.class.getResource("Vue.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1150 , 800);
        stage.setResizable(false);
        stage.setTitle("Vue");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        VueControleur controller = new VueControleur(this.mod, this.profil,this.recherche);
        controller.init(scene, stage);
    }
}
