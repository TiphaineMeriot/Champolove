import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

import static java.util.Calendar.*;

public class EditionController {

    Modele mod;
    Profil profil;
    ChoiceBox<String> cb;
    boolean villeModifiee = false;
    boolean professionModifie = false;
    boolean hobbiesModifie = false;
    boolean qualitesModifiees = false;
    boolean defautsModifies = false;

    public EditionController(Modele mod, Profil profil) {
        this.mod = mod;
        this.profil = profil;
        // on ajoute les modifs possibles


    }
    public void init(Scene scene, Stage stage) {

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
        cbGenre.setValue(this.profil.genre);
        grid.add(cbGenre, 1, 1);

        // Nom
        Label nom = new Label("Nom");
        grid.add(nom, 0, 2);
        // on va ajouter un textField
        TextField tfNom = new TextField();
        tfNom.setText(this.profil.nom);
        grid.add(tfNom, 1, 2);

        // Prenom
        Label prenom = new Label("Prenom");
        grid.add(prenom, 0, 3);
        // on va ajouter un textField
        TextField tfPrenom = new TextField();
        tfPrenom.setText(this.profil.prenom);
        grid.add(tfPrenom, 1, 3);

        // Date de naissance
        Label dateNaissance = new Label("Date de naissance");
        grid.add(dateNaissance, 0, 4);
        // on va ajouter une datePicker
        DatePicker datePicker = new DatePicker();
        String[] dateNaissanceSplit = this.profil.date_de_naissance.split("/");
        // le day sera le premier element du tableau
        int day = Integer.parseInt(dateNaissanceSplit[0]);
        // le month sera le deuxieme element du tableau
        int month = Integer.parseInt(dateNaissanceSplit[1]);
        // le year sera le troisieme element du tableau
        int year = Integer.parseInt(dateNaissanceSplit[2]);
        // on va créer un objet calendar
        Calendar calendar = getInstance();
        // on va lui set la date de naissance
        calendar.set(year, month, day);
        // on va créer un objet date
        Date date = calendar.getTime();
        // on va créer un objet simpleDateFormat
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        // on va formatter la date
        String dateNaissanceFormat = simpleDateFormat.format(date);
        // on va set la date de naissance
        datePicker.setValue(datePicker.getConverter().fromString(dateNaissanceFormat));
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
        cbVille.setValue(this.profil.ville);
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
            cbProfession.setValue(cb.getValue());
        });
        cbProfession.setValue(this.profil.profession);
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
        // on va mettre un textField et une comboBox a coté le textField comporte les hobbies du profil
        // la combo box comporte tous les hobbies du modele
        // des qu'on clique sur un des hobbies du modele, il est ajouté au textField et retiré de la comboBox
        // le textField est editable, on peut donc supprimer les hobbies qu'on veut mais pas en ajouter
        // il faut que le text Field soit équipé d'une scrollbar pour qu'il ne soit pas trop long

        // on va creer un textField
        TextField tfHobbies = new TextField();

        // on va ajouter les hobbies du profil dans le textField
        for (String s : this.profil.hobbies) {
            tfHobbies.setText(tfHobbies.getText() + s + ",");
        }
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
        for (String s : this.profil.qualite) {
            tfQualites.setText(tfQualites.getText() + s + ",");
        }
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
        for (String s : this.profil.defaut) {
            tfDefauts.setText(tfDefauts.getText() + s + ",");
        }
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
        for(String s:this.profil.exi.attirance){
            if(s.equals("HOMME")){
                cbAttirance.setValue("Homme");
            }
            if(s.equals("FEMME")){
                cbAttirance.setValue("Femme");
            }else{
                cbAttirance.setValue("Les deux");
            }
        }
        grid.add(cbAttirance, 1, 12);

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
        if(this.profil.exi.agemin==0 || this.profil.exi.agemin==1000){
            cbAgeMin.setValue("Desactivé pour ce profil");
        }else{
            for (int i = 18; i < 100; i++) {
                cbAgeMin.getItems().add(String.valueOf(i));
                cbAgeMax.getItems().add(String.valueOf(i));
            }
            cbAgeMin.setValue(String.valueOf(this.profil.exi.agemin));
            cbAgeMax.setValue(String.valueOf(this.profil.exi.agemax));

        }
        grid.add(cbAgeMin, 1, 13);
        grid.add(cbAgeMax, 2, 13);

        // Les qualités exigées
        // on va implémenter ça pareil que pr les qualités du profil sauf que la c'est pour les this.profil.exigence.qualite
        Label qualitesExi = new Label("Qualité(s)");
        grid.add(qualitesExi, 0, 14);
        TextField tfQualitesExi = new TextField();
        if(this.profil.exi.choix_qualite.size()==0){
            tfQualitesExi.setText("Aucune Exigée");
        }else{
            for (String s : this.profil.exi.choix_qualite) {
                tfQualitesExi.setText(tfQualitesExi.getText() + s + ",");
            }
        }
        ComboBox<String> cbQualitesExi = new ComboBox<>();
        for (String s : this.mod.qualite) {
            cbQualitesExi.getItems().add(s);
        }
        cbQualitesExi.setOnAction(event -> {
            tfQualitesExi.setText(tfQualitesExi.getText() + cbQualitesExi.getValue() + " ");
            cbQualitesExi.getItems().remove(cbQualitesExi.getValue());
        });
        grid.add(tfQualitesExi, 1, 14);
        grid.add(cbQualitesExi, 2, 14);

        // Les défauts exigés
        // on va implémenter ça pareil que pr les défauts du profil sauf que la c'est pour les this.profil.exigence.defaut
        Label defautsExi = new Label("Défaut(s)");
        grid.add(defautsExi, 0, 15);
        TextField tfDefautsExi = new TextField();
        if(this.profil.exi.choix_defaut.size()==0){
            tfDefautsExi.setText("Aucun Exigé");
        }else{
            for (String s : this.profil.exi.choix_defaut) {
                tfDefautsExi.setText(tfDefautsExi.getText() + s + ",");
            }
        }
        ComboBox<String> cbDefautsExi = new ComboBox<>();
        for (String s : this.mod.defaut) {
            cbDefautsExi.getItems().add(s);
        }
        cbDefautsExi.setOnAction(event -> {
            tfDefautsExi.setText(tfDefautsExi.getText() + cbDefautsExi.getValue() + " ");
            cbDefautsExi.getItems().remove(cbDefautsExi.getValue());
        });
        grid.add(tfDefautsExi, 1, 15);
        grid.add(cbDefautsExi, 2, 15);

        // on passe aux boutons
        // on met un bouton Valider qui va enregistrer les modifications
        // et un bouton Annuler qui va annuler les modifications et revenir au menu principal

        Button valider = (Button)scene.lookup("#valider");
        Button annuler = (Button)scene.lookup("#annuler");

        // si on clique sur le bouton on récupére toutes les valeurs des comboBox et textField et on remplace les valeurs correspondantes du this.profil
        valider.setOnAction(event -> {
            this.profil.nom = tfNom.getText();
            this.profil.prenom = tfPrenom.getText();
            // on transforme la date de DatePicker en String
            this.profil.date_de_naissance = datePicker.getValue().toString();
            // on instancie l'age en calculant la différence entre l'année actuelle et l'année de naissance
            this.profil.age = Year.now().getValue() - datePicker.getValue().getYear();
            this.profil.ville = cbVille.getValue();
            this.profil.profession = cbProfession.getValue();
            // on recupere uniquement le chemin d'accès de l'imageView
            if(imageView.getImage()!=null){
                this.profil.image = imageView.getImage().getUrl();
            }
            // on clear les listes de qualite et defaut
            /*
            this.profil.qualite.clear();
            this.profil.defaut.clear();
            // on les instancie avec les valeurs des textField
            for (String s : tfQualites.getText().split(",")) {
                this.profil.qualite.add(s);
            }
            for (String s : tfDefauts.getText().split(",")) {
                this.profil.defaut.add(s);
            }
            // on fait pareil pour les exigences
            this.profil.exi.attirance.clear();
            this.profil.exi.choix_qualite.clear();
            this.profil.exi.choix_defaut.clear();
            this.profil.exi.agemin = Integer.parseInt(cbAgeMin.getValue());
            this.profil.exi.agemax = Integer.parseInt(cbAgeMax.getValue());

            // on update l'attirance avec la cbAttirance
            this.profil.exi.attirance.add(cbAttirance.getValue());
            for (String s : tfQualitesExi.getText().split(",")) {
                this.profil.exi.choix_qualite.add(s);
            }
            for (String s : tfDefautsExi.getText().split(",")) {
                this.profil.exi.choix_defaut.add(s);
            }*/

            // on enregistre les modifications
            this.mod.enregistrer();
            // on revient au menu principal
            Selection s = new Selection(this.mod);
            try {
                s.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        annuler.setOnAction(event -> {
            // on revient au menu principal
            Selection s = new Selection(this.mod);
            try {
                s.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
