import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;



// on importe javax.activation.DataHandler de la librairie javax.mail



public class SelectionControleur {
    Modele mod;
    Profil profilCourant;

    public SelectionControleur(Modele mod) throws Exception {
        this.mod = mod;
        //si le modele est vide on le rempli avec 25 profils sinon on le charge


    }

    public void init(Scene scene,Stage primaryStage) throws Exception {

        // On récupère le GridPane du fichier FXML
        int i = 1;
        GridPane gridPane = (GridPane) scene.lookup("#grid");
        // On ajoute les profils à la vue
        for(Profil profil: this.mod.listeProfil){
            Label labelNomPrenom = new Label(profil.nom + " " + profil.prenom);
            // on lui met la couleur -->  #fb7434 et en gras
            labelNomPrenom.setStyle(" -fx-font-weight: bold; -fx-text-fill: #fb7434");
            Image image = null;
            try {
                //Là je teste si le chemin est valide
                image = new Image(profil.image);
            } catch (IllegalArgumentException e) {
                System.out.println("L'url est introuvable ou invalide:" + String.format("src/images/%s/%s_%s.jpeg", profil.genre, profil.nom, profil.prenom));
                System.out.println("Current working directory: " + System.getProperty("user.dir"));
            }
            gridPane.add(labelNomPrenom, 1, i);
            //charger l'image dans l'imageview d'id ImageView
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(70);
            imageView.setFitWidth(70);
            imageView = arrondirCoins(imageView, 70);
            gridPane.add(imageView, 0, i);

            // si le profil est cliqué
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent event) {
                    // le profil cliqué devient le profil courant
                    profilCourant = profil;

                }
            });
            i++;
        }
        // on récupere le bouton d'id "utiliser"
        Button utiliser = (Button) scene.lookup("#utiliser");
        utiliser.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                // on verifie d'abord si le profilcourant est null
                if(profilCourant != null){
                    // si oui on ouvre la Vue en lui passant le profil courant
                    Vue vue = new Vue(mod,profilCourant);
                    try {
                        vue.start(primaryStage);

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Erreur lors de l'ouverture de la vue");
                    }
                }else{
                    // on récupère le label d'id "erreur" et on lui met son opacité à 1
                    Label erreur = (Label) scene.lookup("#erreur");
                    erreur.setOpacity(1);
                }
            }
        });



    }
    public ImageView arrondirCoins(ImageView imageView, double radius) {
        // Créer un rectangle avec des coins arrondis
        Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
        clip.setArcWidth(radius);
        clip.setArcHeight(radius);

        // Appliquer le rectangle comme un masque pour l'image
        imageView.setClip(clip);
        return imageView;
    }
}
