import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Fin extends Application {
    Modele mod;
    Profil profil1;
    Profil profil2;
    public Fin() {
        super();
    }
    public Fin(Modele mod, Profil profil1,Profil profil2){
        this.mod = mod;
        this.profil1 = profil1;
        this.profil2 = profil2;


    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Selection.class.getResource("Fin.fxml"));
        primaryStage.setTitle("Fin");
        Scene scene = new Scene(fxmlLoader.load(), 831 , 505);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.centerOnScreen();
        FinControleur controller = new FinControleur(mod,profil1,profil2);
        controller.init(scene, primaryStage);


    }

}
