import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Dating extends Application {
    Modele mod;
    Profil p1;
    Profil p2;
    public Dating(Modele mod, Profil p1, Profil p2){
        this.mod = mod;
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Selection.class.getResource("Dating.fxml"));
        primaryStage.setTitle("Dating");
        Scene scene = new Scene(fxmlLoader.load(), 1172 , 794);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.centerOnScreen();
        DatingControleur controller = new DatingControleur(mod, p1, p2);
        controller.init(scene,primaryStage);

    }
}
