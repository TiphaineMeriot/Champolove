import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class CreationProfil extends Application {

    Button buttonCréer;

    @FXML
    private Label nom;

    public void créer(){

    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(CreationProfil.class.getResource("creationp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 602, 406);
        stage.setResizable(false);
        stage.setTitle("Créer un nouveau profil");
        stage.setScene(scene);
        stage.show();

        buttonCréer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                créer();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
