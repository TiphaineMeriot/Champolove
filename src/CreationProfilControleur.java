
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumSet;

import javafx.scene.control.*;

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

    public String nom;
    public String prénom;
    public String ville;
    public LocalDate date;
    public String dateFormatée;
    public String genre;
    public String statut;
    public String profession;
    public String recherche;


    // Initialisation des combobox en fonction des enum de Profil
    @FXML
    public void initialisationComboBox() {
        // Genre
        ArrayList<Genre> genre = new ArrayList();
        for (Genre gen : Genre.values()) {
            genre.add(gen);
        }
        EnumSet.allOf(Genre.class).forEach(g -> genretxt.getItems().addAll(String.valueOf((g))));

        // Recherche
        EnumSet.allOf(Genre.class).forEach(g -> recherchetxt.getItems().addAll(String.valueOf((g))));

        // Statut
        ArrayList<Statut> statut = new ArrayList();
        for (Statut stat : Statut.values()) {
            statut.add(stat);
        }
        EnumSet.allOf(Statut.class).forEach(s -> statuttxt.getItems().addAll(String.valueOf((s))));
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
            créer();
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
    public void créer() throws Exception {
        Profil p = new Profil(nom, prénom, dateFormatée, genre, statut, ville, recherche);
        System.out.println(p);
        afficherMessage("Le profil a bien été créé");
        System.exit(0);
    }

}
