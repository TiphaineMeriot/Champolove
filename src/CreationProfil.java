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
import java.text.ParseException;

public class CreationProfil extends Application {

    Button buttonCréer;
    String prenom ="vide";
    String date = "00/00/00";
    String genree="HOMME";
    String statut="HOMME";
    String ville="HOMME";
    String recherche="HOMME";


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(CreationProfil.class.getResource("creationp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 602, 406);
        stage.setResizable(false);
        stage.setTitle("Créer un nouveau profil");
        stage.setScene(scene);
        stage.show();
        buttonCréer = (Button) scene.lookup("#buttonCréer");
        buttonCréer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
