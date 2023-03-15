import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;



public class Authentification extends Application {
    // un dictionnaire qui associe un username(=String) à un password(=String)
    HashMap<String, String> data = new HashMap<String, String>();
    String username;
    String password;
    Button btn;
    TextField userField;
    PasswordField passwordField;
    public Authentification(){
        this.data.put("admin", "admin");
        this.data.put("user", "user");

    }



    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Authentification.class.getResource("authentification.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 693 , 488);
        stage.setResizable(false);
        stage.setTitle("Authentification");
        stage.setScene(scene);
        stage.show();

        //si le bouton "Se connecter" est cliqué
        //on récupère le username et le password
        //on vérifie si le username et le password sont dans le dictionnaire
        //si oui, on ouvre la vue
        //sinon, on affiche un message d'erreur
        btn = (Button) scene.lookup("#boutonSeConnecter");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //récupérer le username et le password
                    userField = (TextField) scene.lookup("#userField");
                    username = userField.getText();
                    passwordField = (PasswordField) scene.lookup("#passwordField");
                    password = passwordField.getText();
                    //vérifier si le username et le password sont dans le dictionnaire
                    if (data.containsKey(username) && data.get(username).equals(password)){
                        //si oui, on ouvre la vue
                        Vue vue = new Vue();
                        vue.start(stage);
                    }
                    else{
                        //sinon, on affiche un message d'erreur
                        System.out.println("Erreur");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
