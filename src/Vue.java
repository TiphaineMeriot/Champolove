import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class Vue extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Modele mod=new Modele();
        FXMLLoader fxmlLoader = new FXMLLoader(CreationProfil.class.getResource("Vue.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200 , 800);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Vue");
        stage.setScene(scene);
        stage.show();
        VueControleur controller = new VueControleur(mod);
        controller.init(scene, stage);
        controller.test(); //là cette méthode marche po

    }
}
