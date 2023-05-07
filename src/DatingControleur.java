import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DatingControleur {
    Modele mod;
    Profil p1;
    Profil p2;

    public DatingControleur(Modele mod, Profil p1, Profil p2) {
        this.mod = mod;
        this.p1 = p1;
        this.p2 = p2;
    }

    public void init(Scene scene, Stage primaryStage){
        ImageView image1 = (ImageView) scene.lookup("#Image1");
        ImageView remplacement = new ImageView(this.p1.image);
        image1.setImage(remplacement.getImage());
        // on lui met 120 x 120
        image1.setFitHeight(120);
        image1.setFitWidth(120);
        image1 = arrondirCoins(image1, 120);

        // pareil avec l'image 2
        ImageView image2 = (ImageView) scene.lookup("#Image2");
        remplacement = new ImageView(this.p2.image);
        image2.setImage(remplacement.getImage());
        image2.setFitHeight(120);
        image2.setFitWidth(120);
        image2 = arrondirCoins(image2, 120);

        // on va récupérer la ChoiceBox d'id "rdv" et lui ajouter des choix
        ChoiceBox<String> rdv = (ChoiceBox<String>) scene.lookup("#rdv");
        rdv.getItems().add("Restaurant");
        rdv.getItems().add("Cinéma");
        rdv.getItems().add("Bowling");
        rdv.getItems().add("Bar");
        rdv.getItems().add("Aquarium");
        rdv.getItems().add("Parc d'attraction");
        rdv.getItems().add("Parc");
        rdv.getItems().add("Musée");
        rdv.getItems().add("Piscine");
        rdv.getItems().add("Patinoire");
        rdv.getItems().add("Zoo");
        rdv.getItems().add("Concert");


        scene.lookup("#valider").setOnMouseClicked(event -> {
            // TODO: ici on enverra un mail aux deux personnes
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