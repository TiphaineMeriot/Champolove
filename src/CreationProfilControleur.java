
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.ResourceBundle;

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

    @FXML
    private void buttonCréerAction(ActionEvent event) throws Exception {
        nom = nomtxt.getText();
        prénom = prénomtxt.getText();
        ville = villetxt.getText();
        date =dateNaissancetxt.getValue();
        // TODO mettre dans le combobox les enums de profil
        genre = genretxt.getValue();
        statut = statuttxt.getValue();
        profession = professiontxt.getValue();
        recherche = recherchetxt.getValue();
        if(nom.equals("") || prénom.equals("") || date.equals(""))
            // TODO gérer erreur de date
            afficherMessage("Terme manquant");
        else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dateFormatée = date.format(dateTimeFormatter);
            créer();
        }
    }

    private void afficherMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void créer() throws Exception {
        Profil p = new Profil(nom, prénom, dateFormatée, genre, statut, ville, recherche);
        System.out.println(p.toString());
    }
}
