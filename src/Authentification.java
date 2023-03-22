import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashMap;



public class Authentification extends Application {
    // un dictionnaire qui associe un username(=String) à un password(=String)
    HashMap<String, String> data = new HashMap<String, String>();
    String username;
    String password;
    Button btn;
    ToggleButton btnDarkMode;
    TextField userField;
    PasswordField passwordField;
    public Authentification(){
        data.put("admin", "admin");
        data.put("user", "user");
        data.put("karen.mireille@gmail.com", "ChaiseBleuex79!");
        data.put("laurent.gabriel@gmail.com", "CeriseRosey47!");
        data.put("emilie.mouche@gmail.com", "LivreNoirz77!");
        data.put("sophie.sabine@gmail.com", "TasDeTerreu21!");
        data.put("alexandre.sacha@gmail.com", "CoupeDeLuneq12!");
        data.put("jean.pierre@gmail.com", "TableauVertj71!");
        data.put("julie.lola@gmail.com", "SoleilBlancj67!");
        data.put("pierre.antoine@gmail.com", "MontagneGrisw64!");
        data.put("paul.sylvain@gmail.com", "EtoileRougez31!");
        data.put("claire.justine@gmail.com", "FeuilleVertee12!");
        data.put("louise.maelle@gmail.com", "EtangBleuq74!");
        data.put("anna.julien@gmail.com", "RiviereNoirz85!");
        data.put("sandra.david@gmail.com", "OiseauJauneo77!");
        data.put("cecile.laurent@gmail.com", "ArcEnCielu91!");
        data.put("benjamin.louis@gmail.com", "NeigeBlancheb61!");
        data.put("thomas.anais@gmail.com", "RueRougeh44!");
        data.put("chloe.martin@gmail.com", "VentVertt12!");
        data.put("pauline.benoit@gmail.com", "RocheNoirx92!");
        data.put("kevin.robert@gmail.com", "FleurJauneg22!");
        data.put("victor.victoire@gmail.com", "LuneBlanchev54!");
    }



    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Authentification.class.getResource("authentification.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 693 , 488);
        stage.setResizable(false);
        stage.setTitle("Authentification");
        stage.setScene(scene);
        stage.show();

        //ajout d'une icone
        stage.getIcons().add(new javafx.scene.image.Image("images/logo.png"));
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
        //ajout d'une image de fond sur le bouton d'id "btnDarkMode"
        btnDarkMode = (ToggleButton) scene.lookup("#btnDarkMode");
        //ajout d'une image de fond sur btnDarkMode sans modifier le css
        btnDarkMode.setStyle("-fx-background-image: url('images/dark.png'); -fx-background-size: 100% 100%;");

        //quand le bouton "btnDarkMode" est cliqué
        //on change le css de la scene pour passer en mode sombre

        btnDarkMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(btnDarkMode.isSelected()){
                    //on passe en mode sombre
                    scene.getStylesheets().add("css/dark.css");
                }

            }
        });



    }
}
