import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class Vue extends Application {
    Modele mod;
    Profil profil;
    public Vue(){
        super();
    }
    public Vue(Modele mod, Profil profil) {
        this.mod = mod;
        this.profil = profil;
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
        VueControleur controller = new VueControleur(this.mod, this.profil);
        controller.init(scene, stage);
    }
}
