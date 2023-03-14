import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Authentification extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Authentification.class.getResource("authentification.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 693 , 488);
        stage.setResizable(false);
        stage.setTitle("Authentification");
        stage.setScene(scene);
        stage.show();
    }
}
