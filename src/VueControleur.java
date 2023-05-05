import com.sun.webkit.Timer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeSet;

public class VueControleur {
    Button boutonCreerProfil;
    String profilRecherche;
    Profil profilClick;
    Profil profilCourant;
    Modele mod;
    boolean DarkMode = false;
    boolean opacite = false;

    public VueControleur(Modele mod, Profil profil){
        this.mod=mod;
        this.profilCourant=profil;
    }

    public void init(Scene scene, Stage stage) throws Exception {



        boutonCreerProfil = (Button) scene.lookup("#boutonCreerProfil");
        boutonCreerProfil.setOnAction(event -> {
            try {
                CreationProfil creationProfil = new CreationProfil(mod);
                creationProfil.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //stocker le texte de la zone de recherche dans la variable profilRecherche
        profilRecherche = String.valueOf(scene.lookup("#ZonedeRecherche"));

        //ajout de l'icone
        stage.getIcons().add(new Image("images/logo_invisible.png"));
        //Modif dylan: ajout d'une instruction lors de la fermeture:
        stage.setOnCloseRequest(event -> {
            this.mod.enregistrer();
        });
        GridPane gridPane = (GridPane) scene.lookup("#GridProfils");
        ///

        //Ajout des profils dans le gridPane
        // les profils possèdent un

        int i = 1;

        // on va retourner un TreeSet de profils triés par compatibilité en fonction du profil courant
        Matching m = new Matching(this.mod);
        TreeSet<Profil> listeProfilTrie = m.matching2(this.profilCourant);

        for (Profil profil : listeProfilTrie) {
            Label labelNomPrenom = new Label(profil.nom + " " + profil.prenom);
            // on lui met la couleur -->  #fb7434 et en gras
            labelNomPrenom.setStyle(" -fx-font-weight: bold; -fx-text-fill: #fb7434");
            Image image = null;
            try {
                //Là je teste si le chemin est valide
                image = new Image(profil.image);

                // on donne a l'image des bordures arrondies

            } catch (IllegalArgumentException e) {
                System.out.println("L'url est introuvable ou invalide:" + String.format("src/images/%s/%s_%s.jpeg", profil.genre, profil.nom, profil.prenom));
                System.out.println("Current working directory: " + System.getProperty("user.dir"));
            }

            // on ajoute un ProgressIndicator pour chaque profil
            ProgressIndicator progressIndicator = new ProgressIndicator();
            // la compatibilité du profil est sur 100, on la divise par 100 pour avoir un resultat entre 0 et 1
            float compa = (float)(profil.compatibilité);
            compa = compa/100;
            progressIndicator.setProgress(compa);
            // on ajoute un label pour chaque profil qui contient le nom et le prenom du profil
            gridPane.add(progressIndicator, 2, i);
            gridPane.add(labelNomPrenom, 1, i);
            //charger l'image dans l'imageview d'id ImageView
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(70);
            imageView.setFitWidth(70);
            imageView = arrondirCoins(imageView, 70);
            gridPane.add(imageView, 0, i);
            //si la souris clique sur l'image,on stocke le profil correspondant dans la variable profilClick
            imageView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent event) {
                    if(!opacite){
                        // on recupere les imageView d'id "like" "default" "job" "taille" "gateau" "plan" et on passe leur opacité à 1
                        ImageView like = (ImageView) scene.lookup("#like");
                        like.setOpacity(1);
                        ImageView defaut = (ImageView) scene.lookup("#default");
                        defaut.setOpacity(1);
                        ImageView job = (ImageView) scene.lookup("#job");
                        job.setOpacity(1);
                        ImageView taille = (ImageView) scene.lookup("#taille");
                        taille.setOpacity(1);
                        ImageView gateau = (ImageView) scene.lookup("#gateau");
                        gateau.setOpacity(1);
                        ImageView plan = (ImageView) scene.lookup("#plan");
                        plan.setOpacity(1);
                        opacite = true;
                    }
                    // le profil cliqué devient le profilclick
                    profilClick = profil;

                    // l'imageView d'id ImageGrande est remplacé par l'image du profil cliqué
                    ImageView imageGrande = (ImageView) scene.lookup("#ImageGrande");
                    ImageView remplacement = new ImageView(profil.image);
                    imageGrande.setImage(remplacement.getImage());
                    imageGrande = arrondirCoins(imageGrande, 200);

                    // on ajoute dans le pane de droite les informations de profilClick avec des labels qui ont la police d'ecriture : Cambria
                    // on créé un label nomPrenom qui contient le nom et le prenom du profil cliqué avec la police d'ecriture : Cambria et un espace entre le nom et le prenom
                    Label labelNomPrenom = new Label(profil.prenom + " " + profil.nom);
                    labelNomPrenom.setStyle("-fx-font-family: Cambria");
                    //label date de Naissance qui contient la date de naissance et l'age du profil cliqué avec la police d'ecriture : Cambria et un espace entre la date de naissance et l'age
                    Label labelDateDeNaissance = new Label(profil.date_de_naissance + " (" + profil.age + " ans)");
                    labelDateDeNaissance.setStyle("-fx-font-family: Cambria");
                    //label ville qui contient la ville du profil cliqué
                    Label labelVille = new Label(profil.ville);
                    // label travail qui contient le travail du profil cliqué
                    Label labelTravail = new Label(profil.profession);
                    // on va créer un label qualitées qui sera rempli par les qualités du profil cliqué
                    // pour ça on fait une boucle qui va parcourir la liste des qualités du profil cliqué et les ajouter dans le label qualitées avec une virgule puis un espace entre chaque qualité
                    Label labelQualites = new Label();
                    for (int i = 0; i < profil.qualite.size(); i++) {
                        if (i == 0) {
                            labelQualites.setText(profil.qualite.get(i));
                        } else {
                            labelQualites.setText(labelQualites.getText() + ", " + profil.qualite.get(i));
                        }
                    }
                    // on va créer un label defauts qui sera rempli par les defauts du profil cliqué
                    // pour ça on fait une boucle qui va parcourir la liste des defauts du profil cliqué et les ajouter dans le label defauts avec une virgule puis un espace entre chaque defaut
                    Label labelDefauts = new Label();
                    for (int i = 0; i < profil.defaut.size(); i++) {
                        if (i == 0) {
                            labelDefauts.setText(profil.defaut.get(i));
                        } else {
                            labelDefauts.setText(labelDefauts.getText() + ", " + profil.defaut.get(i));
                        }
                    }
                    //label taille qui contient la taille du profil cliqué
                    String taille = String.format("%1.2fm",profil.taille);
                    Label labelTaille = new Label(taille);

                    //TODO : on modifie l'imageView avatar en fonction de l'image du profil cliqué
                    //imageViewAvatar.setImage(profilClick.image);

                    //le label nomPrenom a une police de titre de taille 30 et est centré a droite et en gras et une couleur de police #fb7434;
                    labelNomPrenom.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: #fb7434; -fx-alignment: center-right");
                    //le label date de naissance a une police de taille 20 et est centré a droite et en gras et une couleur de police #fb7434;
                    labelDateDeNaissance.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #fb7434; -fx-alignment: center-right");
                    //le label ville a une police de taille 20 et est centré a droite et en gras et une couleur de police #fb7434;
                    labelVille.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #fb7434; -fx-alignment: center-right");
                    //le label travail a une police de taille 20 et est centré a droite et en gras et une couleur de police #fb7434;
                    labelTravail.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #fb7434; -fx-alignment: center-right");
                    //le label qualitées a une police de taille 20 et est centré a droite et en gras et une couleur de police #fb7434;
                    labelQualites.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #fb7434; -fx-alignment: center-right");
                    //le label defauts a une police de taille 20 et est centré a droite et en gras et une couleur de police #fb7434;
                    labelDefauts.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #fb7434; -fx-alignment: center-right");
                    //le label taille a une police de taille 20 et est centré a droite et en gras et une couleur de police #fb7434;
                    labelTaille.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #fb7434; -fx-alignment: center-right");

                    GridPane gridPaneDroite = (GridPane) scene.lookup("#gridPaneNom");
                    //on clear le gridPaneDroite pour qu'il ne contienne que les informations du profil précédemment cliqué
                    gridPaneDroite.getChildren().clear();
                    //on ajoute les labels dans la colonne 1 de gridPaneDroite
                    gridPaneDroite.add(labelNomPrenom, 0, 0);

                    // on affiche tout les attributs du profil cliqué dans la console


                    GridPane gridPaneInfos = (GridPane) scene.lookup("#gridPaneInfos");
                    gridPaneInfos.getChildren().clear();
                    gridPaneInfos.add(labelDateDeNaissance, 0, 0);
                    gridPaneInfos.add(labelVille, 0, 1);
                    gridPaneInfos.add(labelTravail, 0, 2);
                    gridPaneInfos.add(labelQualites, 0, 3);
                    gridPaneInfos.add(labelDefauts, 0, 4);
                    gridPaneInfos.add(labelTaille, 0, 5);
                }
            });
            i++;
        }



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