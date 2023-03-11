import javafx.application.Application;
import javafx.stage.Stage;

public class CreationProfil extends Application {

    @Override
    public void start(Stage p) throws Exception {
        p.setWidth(500);
        p.setHeight(500);
        p.setTitle("Créer un profil");
        p.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
