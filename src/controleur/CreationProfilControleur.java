package controleur;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;

import javafx.scene.control.*;
import javafx.scene.control.TextField;
import modele.Modele;
import vue.Genre;
import vue.Profil;
import vue.Statut;

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
    @FXML
    private ScrollPane scrolld;
    @FXML
    private ListView listd;

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
    public ArrayList<String> q;
    public ArrayList<String> d;
    private ObservableList<String> items;

    public ArrayList<String> qualite;
    public ArrayList<String> defaut;
    private ObservableList<String> qual;
    private ObservableList<String> def;
    Modele mod;
    public CreationProfilControleur(){
        super();
    }
    public CreationProfilControleur(Modele mod){
        this.mod=mod;
    }
    // Initialisation des combobox en fonction des enum de vue.Profil
    @FXML
    public void initialisationComboBox() {
        // vue.vue.Genre
        ArrayList<Genre> genre = new ArrayList();
        Collections.addAll(genre, Genre.values());
        EnumSet.allOf(Genre.class).forEach(g -> genretxt.getItems().addAll(String.valueOf((g))));

        // Recherche
        EnumSet.allOf(Genre.class).forEach(g -> recherchetxt.getItems().addAll(String.valueOf((g))));

        // vue.vue.Statut
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
        Modele m =new Modele();
        qualite = new ArrayList<>();
        defaut = new ArrayList<>();
        String chemqual="donnees/qualites.csv";
        String chemdef="donnees/defaut.csv";

        m.aux_csv_transform(chemqual, qualite);
        m.aux_csv_transform(chemdef, defaut);

        qual = FXCollections.observableArrayList (qualite);
        listqd.setItems(qual);
        scrollqd.setContent(listqd);
        scrollqd.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollqd.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        q = new ArrayList<>();
        listqd.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                q.add(observableValue.getValue());
            }

        });

        def = FXCollections.observableArrayList (defaut);
        listd.setItems(def);
        scrolld.setContent(listd);
        scrolld.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrolld.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        d = new ArrayList<>();
        listd.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                d.add(observableValue.getValue());
            }

        });
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
       // profession = professiontxt.getValue();
        recherche = recherchetxt.getValue();

        if(nom.equals("") || prénom.equals("") || date.equals("") || ville.equals("")|| genre == null || statut == null
                || recherche==null) {
            // TODO gérer erreur de date
            afficherMessage("Terme manquant");

        }else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dateFormatée = date.format(dateTimeFormatter);
            créer();
            afficherMessage("ok");
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
        Profil p = new Profil(nom, prénom, dateFormatée, genre, statut, ville);
        //p.hobbies.addAll(h);
        //p.qualite.addAll(qd);
        System.out.println(p);
        afficherMessage("Le profil a bien été créé");
        System.exit(0);
    }



}