import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreationProfil extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(CreationProfil.class.getResource("creationp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 602 , 406);
        stage.setTitle("Créer un nouveau profil");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
