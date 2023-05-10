import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static java.util.Calendar.getInstance;

public class CreationProfilControleur {

    Modele mod;
    Profil profil = new Profil("null" ,"null", "11/12/2003", "null", "null", "null");
    ChoiceBox<String> cb;
    boolean villeModifiee = false;
    boolean professionModifie = false;
    boolean hobbiesModifie = false;
    boolean qualitesModifiees = false;
    boolean defautsModifies = false;


    public CreationProfilControleur(Modele mod) throws Exception {;
        this.mod = mod;

    }
    public void init(Scene scene, Stage stage) throws Exception {
        // on recupere le gridPane d'id gridBOX et on lui ajoute une ChoiceBox

        GridPane grid = (GridPane) scene.lookup("#gridBOX");

        // on ajoute un label couleur blanche dans le 0,0 du gridPane
        Label label = new Label(" ");
        label.setStyle("-fx-text-fill: white");
        grid.add(label, 0, 0);

        // on ajoute les choix
        // on ajoute un label Genre colonne 0 ligne 0
        // puis on ajoute une choiceBox colonne 1 ligne 0
        Label genre = new Label("Genre");
        grid.add(genre, 0, 1);
        ChoiceBox<String> cbGenre = new ChoiceBox<>();
        cbGenre.getItems().addAll("Homme", "Femme", "Autre");
        grid.add(cbGenre, 1, 1);

        // Nom
        Label nom = new Label("Nom");
        grid.add(nom, 0, 2);
        // on va ajouter un textField
        TextField tfNom = new TextField();
        grid.add(tfNom, 1, 2);

        // Prenom
        Label prenom = new Label("Prenom");
        grid.add(prenom, 0, 3);
        // on va ajouter un textField
        TextField tfPrenom = new TextField();
        grid.add(tfPrenom, 1, 3);

        // Date de naissance
        Label dateNaissance = new Label("Date de naissance");
        grid.add(dateNaissance, 0, 4);
        // on va ajouter une datePicker
        DatePicker datePicker = new DatePicker();
        // le datePicker comporte que les dates entre 1900 et 2003
        datePicker.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                // on va instancier une date de 1900 et une date de 2003
                LocalDate dateDebut = LocalDate.of(1900, 1, 1);
                LocalDate dateFin = LocalDate.of(2003, 12, 31);
                // si la date est avant 1900 ou apres 2003 on la desactive
                setDisable(item.isBefore(dateDebut) || item.isAfter(dateFin));
            }
        });
        // on ajoute le datePicker
        grid.add(datePicker, 1, 4);

        // Ville
        Label ville = new Label("Ville");
        grid.add(ville, 0, 5);

        // on instancie apres une comboBox dans laquelle on ajoute toutes les villes du hashSet
        ComboBox<String> cbVille = new ComboBox<>();
        // si on clique sur la comboBox on va ajouter toutes les villes du hashSet
        // histoire de pas les lister a chaque fois (dans les cas ou on clique pas dessus)
        cbVille.setOnMouseClicked(event -> {
            if(!this.villeModifiee) {
                ArrayList<String> list = new ArrayList<String>();
                // on va ajouter toutes les villes du modele dans le hashSet
                for (Modele.Donnees d : this.mod.lieu) {
                    list.add(d.location);
                }
                list.sort(String::compareToIgnoreCase);
                cbVille.getItems().addAll(list);
                this.villeModifiee = true;
            }});
        grid.add(cbVille, 1, 5);

        //profession, on va suivre le meme principe que pour la ville
        Label profession = new Label("Profession");
        grid.add(profession, 0, 6);
        ComboBox<String> cbProfession = new ComboBox<>();
        cbProfession.setOnMouseClicked(event -> {
            // on attribue a la comboBox la profession sélectionnée

            if(!this.professionModifie) {
                ArrayList<String> list = new ArrayList<String>();
                for (String d : this.mod.metier) {
                    list.add(d);
                }
                list.sort(String::compareToIgnoreCase);
                cbProfession.getItems().addAll(list);
                this.professionModifie = true;
            }
        });
        cbProfession.setOnAction(event -> {
            cbProfession.setValue("Banque");
        });
        grid.add(cbProfession, 1, 6);

        //image
        // on va ajouter un bouton qui ira chercher dans les fichiers de l'utilisateur une image
        // on va ajouter un label qui affichera le chemin de l'image
        // Créer une ImageView pour afficher l'image sélectionnée
        Label img = new Label("Image");
        grid.add(img, 0, 7);
        ImageView imageView = new ImageView();
        // Créer un bouton pour ouvrir le FileChooser
        Button button = new Button("Choisir une image");
        // Définir l'action à effectuer lorsque le bouton est cliqué
        button.setOnAction(e -> {
            // Créer un nouveau FileChooser
            FileChooser fileChooser = new FileChooser();
            // Configurer le FileChooser pour n'afficher que les fichiers d'images
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
            );
            // Afficher le FileChooser et attendre que l'utilisateur sélectionne un fichier
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                // Charger l'image sélectionnée dans la ImageView
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
            }
        });
        // Créer un conteneur VBox pour afficher l'ImageView et le bouton
        // on affichera l'image dans un format de 200*200
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        VBox root = new VBox(10,imageView, button);
        grid.add(root, 1, 7);


        //hobbies
        Label hobbies = new Label("Hobbies");
        grid.add(hobbies, 0, 8);

        // on va creer un textField
        TextField tfHobbies = new TextField();
        tfHobbies.setEditable(false);


        // on va creer une comboBox
        ComboBox<String> cbHobbies = new ComboBox<>();
        // on va ajouter tous les hobbies du modele dans la comboBox
        for (String s : this.mod.hobbies) {
            cbHobbies.getItems().add(s);
        }

        // on va ajouter un listener sur la comboBox
        cbHobbies.setOnAction(event -> {
            // on va ajouter le hobby selectionné au textField
            tfHobbies.setText(tfHobbies.getText() + cbHobbies.getValue() + ",");
            // on va retirer le hobby selectionné de la comboBox
//            cbHobbies.getItems().remove(cbHobbies.getValue());
        });

        // on va ajouter le textField et la comboBox au gridPane
        grid.add(tfHobbies, 1, 8);
        grid.add(cbHobbies, 2, 8);

        // on va faire exactement pareil que pour les hobbies mais pour les qualites
        Label qualites = new Label("Qualité(s)");
        grid.add(qualites, 0, 9);
        TextField tfQualites = new TextField();
        tfQualites.setEditable(false);
        ComboBox<String> cbQualites = new ComboBox<>();
        for (String s : this.mod.qualite) {
            cbQualites.getItems().add(s);
        }
        cbQualites.setOnAction(event -> {
            tfQualites.setText(tfQualites.getText() + cbQualites.getValue() + " ");
//            cbQualites.getItems().remove(cbQualites.getValue());
        });
        grid.add(tfQualites, 1, 9);
        grid.add(cbQualites, 2, 9);

        // on va faire exactement pareil que pour les qualites mais pour les defauts
        Label defauts = new Label("Défaut(s)");
        grid.add(defauts, 0, 10);
        TextField tfDefauts = new TextField();
        // on rend le TextField non editable
        tfDefauts.setEditable(false);
        ComboBox<String> cbDefauts = new ComboBox<>();
        for (String s : this.mod.defaut) {
            cbDefauts.getItems().add(s);
        }
        cbDefauts.setOnAction(event -> {
            tfDefauts.setText(tfDefauts.getText() + cbDefauts.getValue() + " ");
//            cbDefauts.getItems().remove(cbDefauts.getValue());
        });
        grid.add(tfDefauts, 1, 10);
        grid.add(cbDefauts, 2, 10);

        // on passe aux exigences
        Label exigences = new Label("Exigence(s)");
        // on le souligne
        exigences.setUnderline(true);
        grid.add(exigences, 0, 11);

        // attirance
        Label attirance = new Label("Attirance");
        grid.add(attirance, 0, 12);
        // on va mettre une comboBox avec les attirances du modele
        // et qui est instanciée avec l'attirance du this.profil.exigence.attirance
        ComboBox<String> cbAttirance = new ComboBox<>();

        String[] attirances = {"Homme", "Femme", "Les deux","Autre"};
        for (String s : attirances) {
            cbAttirance.getItems().add(s);
        }
        grid.add(cbAttirance, 1, 12);
        cbAttirance.setOnAction(event -> {
            cbAttirance.setValue(cbAttirance.getValue());
        });


        // age
        Label age = new Label("Age Min / Age Max");
        grid.add(age, 0, 13);
        // on va mettre deux comboBox avec les ages du modele
        // si l'age min est 0, la combobox est instanciée avec "desactivé pour ce profil"
        // si l'age max est 1000 la combobox est instanciée avec "desactivé pour ce profil"
        // sinon on instancie la combobox avec l'age du this.profil.exigence.ageMin et ageMax respectivement
        // tout en laissant un choix allant de 0 a 99
        ComboBox<String> cbAgeMin = new ComboBox<>();
        ComboBox<String> cbAgeMax = new ComboBox<>();

        for (int i = 18; i < 100; i++) {
                cbAgeMin.getItems().add(String.valueOf(i));
                cbAgeMax.getItems().add(String.valueOf(i));
        }
        cbAgeMax.setOnAction(event -> {
            cbAgeMax.setValue(cbAgeMax.getValue());
        });
        cbAgeMin.setOnAction(event -> {

            cbAgeMin.setValue(cbAgeMin.getValue());
            cbAgeMax.setValue(cbAgeMax.getValue());


        });

        grid.add(cbAgeMin, 1, 13);
        grid.add(cbAgeMax, 2, 13);

        // Les qualités exigées
        // on va implémenter ça pareil que pr les qualités du profil sauf que la c'est pour les this.profil.exigence.qualite
        Label qualitesExi = new Label("Qualité(s)");
        grid.add(qualitesExi, 0, 14);
        TextField tfQualitesExi = new TextField();
        tfQualitesExi.setEditable(false);

        ComboBox<String> cbQualitesExi = new ComboBox<>();
        for (String s : this.mod.qualite) {
            cbQualitesExi.getItems().add(s);
        }
        cbQualitesExi.setOnAction(event -> {
            tfQualitesExi.setText(cbQualitesExi.getValue());
            cbQualitesExi.setValue("Aimable");
        });
        grid.add(tfQualitesExi, 1, 14);
        grid.add(cbQualitesExi, 2, 14);

        // Les défauts exigés
        // on va implémenter ça pareil que pr les défauts du profil sauf que la c'est pour les this.profil.exigence.defaut
        Label defautsExi = new Label("Défaut(s)");
        grid.add(defautsExi, 0, 15);
        TextField tfDefautsExi = new TextField();
        tfDefautsExi.setEditable(false);

        ComboBox<String> cbDefautsExi = new ComboBox<>();
        for (String s : this.mod.defaut) {
            cbDefautsExi.getItems().add(s);
        }
        cbDefautsExi.setOnAction(event -> {
            tfDefautsExi.setText(cbDefautsExi.getValue());
            cbDefautsExi.setValue("Bavard");
        });
        grid.add(tfDefautsExi, 1, 15);
        grid.add(cbDefautsExi, 2, 15);

        // exigences choix hobbies
        Label hobbiesExi = new Label("Hobbie(s)");
        grid.add(hobbiesExi, 0, 16);
        TextField tfHobbiesExi = new TextField();
        tfHobbiesExi.setEditable(false);
        ComboBox<String> cbHobbiesExi = new ComboBox<>();
        for (String s : this.mod.hobbies) {
            cbHobbiesExi.getItems().add(s);
        }
        cbHobbiesExi.setOnAction(event -> {
            tfHobbiesExi.setText(cbHobbiesExi.getValue());
        });
        // distance
        Label distance = new Label("Distance Max");
        grid.add(distance, 0, 17);
        // on va mettre une comboBox avec toutes les centaines de 0 a 1500

        ComboBox<String> cbDistance = new ComboBox<>();
        for (int i = 0; i < 16; i++) {
            cbDistance.getItems().add(String.valueOf(i*100));
        }
        grid.add(cbDistance, 1, 17);
        cbDistance.setOnAction(event -> {
            cbDistance.setValue(cbDistance.getValue());
        });



        // on passe aux boutons
        // on met un bouton Valider qui va enregistrer les modifications
        // et un bouton Annuler qui va annuler les modifications et revenir au menu principal

        Button valider = (Button)scene.lookup("#valider");
        Button annuler = (Button)scene.lookup("#annuler");

        valider.setOnMouseClicked(event -> {
            // on enregistre les modifications dans le profil puis on l'implante dans le modele
            this.profil.genre = cbGenre.getValue();
            this.profil.nom = tfNom.getText();
            this.profil.prenom = tfPrenom.getText();
            // on transforme la date de DatePicker en String
            // le datePicker.getValue().toString() renvoie une date au format yyyy-mm-dd
            // on va donc la transformer en dd/mm/yyyy
            String[] datee = datePicker.getValue().toString().split("-");
            this.profil.date_de_naissance = datee[2] + "/" + datee[1] + "/" + datee[0];
            // on instancie l'age en calculant la différence entre l'année actuelle et l'année de naissance

            this.profil.ville = cbVille.getValue();
            this.profil.profession = "Banque";
            // on recupere uniquement le chemin d'accès de l'imageView
            if(imageView.getImage()!=null){
                this.profil.image = imageView.getImage().getUrl();
            }
            this.profil.hobbies.add("randonnée");
            this.profil.hobbies.add("jardinage");

            this.profil.qualite.add("Accompli");
            this.profil.qualite.add("Actif");
            this.profil.qualite.add("Brave");

            this.profil.defaut.add("Aigri");
            this.profil.defaut.add("Distrait");

            // on va ajouter les exigences du profil
            // attirance
            this.profil.exi.attirance.add(cbAttirance.getValue());
            this.profil.taille = 180;
            // age Min
            this.profil.exi.agemin = 19;
            // age Max
            this.profil.exi.distance = 0;
            this.profil.exi.agemax = 60;

            // on ajoute le profil au modele

            this.mod.ajouter(this.profil);
            this.mod.enregistrer();
            // on revient a la Selection
            Selection selection = new Selection(this.mod);
            try {
                selection.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });

    }

}


