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


    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(CreationProfil.class.getResource("Vue.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200 , 800);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Vue");
        stage.setScene(scene);
        stage.show();
        VueControleur controleur = new VueControleur();
        controleur.init(scene, stage);


    }

}
