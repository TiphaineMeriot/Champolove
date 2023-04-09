import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreationProfilControleur  {

    @FXML
    private TextField nomtxt;
    @FXML
    private TextField prénomtxt;
    @FXML
    private TextField villetxt;
    @FXML
    private DatePicker dateNaissancetxt;
    @FXML
    private ComboBox<String> genretxt;
    @FXML
    private ComboBox<String> statuttxt;
    @FXML
    private ComboBox<String> professiontxt;
    @FXML
    private ComboBox<String> recherchetxt;
    @FXML
    private ScrollPane scroll;
    @FXML
    private ListView list;
    @FXML
    private ScrollPane scrollqd;
    @FXML
    private ListView listqd;

    public String nom;
    public String prénom;
    public String ville;
    public LocalDate date;
    public String dateFormatée;
    public String genre;
    public String statut;
    public String profession;
    public String recherche;
    public ArrayList<String> h;
    public ArrayList<String> qd;
    private ObservableList<String> items;
    private ObservableList<String> qualitedefaut;
    // Initialisation des combobox en fonction des enum de Profil
    @FXML
    public void initialisationComboBox() {
        // Genre
        ArrayList<Genre> genre = new ArrayList();
        Collections.addAll(genre, Genre.values());
        EnumSet.allOf(Genre.class).forEach(g -> genretxt.getItems().addAll(String.valueOf((g))));

        // Recherche
        EnumSet.allOf(Genre.class).forEach(g -> recherchetxt.getItems().addAll(String.valueOf((g))));

        // Statut
        ArrayList<Statut> statut = new ArrayList();
        Collections.addAll(statut, Statut.values());
        EnumSet.allOf(Statut.class).forEach(s -> statuttxt.getItems().addAll(String.valueOf((s))));

    }

    @FXML
    public void init(){
        items = FXCollections.observableArrayList (
                "Sport", "Art", "Jeux vidéos", "Cinéma", "Culture", "Voyage", "Politique", "Musique");
        list.setItems(items);
        scroll.setContent(list);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        h = new ArrayList<>();
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                //afficherMessage(observableValue.getValue());
                h.add(observableValue.getValue());
            }

        });

        qualitedefaut = FXCollections.observableArrayList ("Gentil", "Intelligent", "Réservé");
        listqd.setItems(qualitedefaut);
        scrollqd.setContent(listqd);
        scrollqd.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollqd.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        qd = new ArrayList<>();
        listqd.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                qd.add(observableValue.getValue());
            }

        });
    }

    // Action du boutton créé, permet de récupérer les valeurs du formulaire
    @FXML
    private void buttonSuivantAction(ActionEvent event) throws IOException {
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("NouvelleFenetre.fxml"));
        Scene sceneNouvelleFenetre = new Scene(root);
        Stage myStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        myStage.setScene(sceneNouvelleFenetre);
        myStage.show();
    }



    // Action du boutton créé, permet de récupérer les valeurs du formulaire
    @FXML
    private void buttonCréerAction(ActionEvent event) throws Exception {
        nom = nomtxt.getText();
        prénom = prénomtxt.getText();
        ville = villetxt.getText();
        date =dateNaissancetxt.getValue();
        genre = genretxt.getValue();
        statut = statuttxt.getValue();
        profession = professiontxt.getValue();
        recherche = recherchetxt.getValue();

        if(nom.equals("") || prénom.equals("") || date.equals("") || ville.equals("")|| genre == null || statut == null
                || recherche==null || profession==null ) {
            // TODO gérer erreur de date
            afficherMessage("Terme manquant");
        }else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dateFormatée = date.format(dateTimeFormatter);

        }

    }

    // Affichage de fenêtre pop-up avec message
    private void afficherMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode qui créé le profil
    /*
    public void créer() throws Exception {
        Profil p = new Profil(nom, prénom, dateFormatée, genre, statut, ville, recherche);
        p.hobbies.addAll(h);
        p.qualite.addAll(qd);
        System.out.println(p);
        afficherMessage("Le profil a bien été créé");
        System.exit(0);

    }
*/
}
