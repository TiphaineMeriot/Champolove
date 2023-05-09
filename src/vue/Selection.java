package vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modele.Modele;

public class Selection extends Application {
    Modele mod;

    public Selection(Modele mod){
        this.mod = mod;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Selection.class.getResource("fxml/Selection.fxml"));
        primaryStage.setTitle("vue.Selection");
        Scene scene = new Scene(fxmlLoader.load(), 831 , 505);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.centerOnScreen();
        controleur.SelectionControleur controller = new controleur.SelectionControleur(mod);
        controller.init(scene, primaryStage);


        
    }

}
