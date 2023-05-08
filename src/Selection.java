import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Selection extends Application {
    Modele mod;

    public Selection(Modele mod){
        this.mod = mod;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Selection.class.getResource("Selection.fxml"));
        primaryStage.setTitle("Selection");
        Scene scene = new Scene(fxmlLoader.load(), 831 , 505);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.centerOnScreen();
        SelectionControleur controller = new SelectionControleur(mod);
        controller.init(scene, primaryStage);


        
    }

}
