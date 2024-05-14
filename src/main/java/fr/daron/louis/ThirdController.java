package fr.daron.louis;


//Importation des librairies nécessaires au bon fonctionnement du code  
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Page historique visiteurs

public class ThirdController extends Application {

    // Importation de tout les champs FXML de l'application

    @FXML
    private Button btnClot;

    @FXML
    private Button btnModif;

    @FXML
    private Button btnSave;

    @FXML
    private DatePicker dateHf1;

    @FXML
    private DatePicker dateHf2;

    @FXML
    private Text etatFiche;

    @FXML
    private TextField libHf1;

    @FXML
    private TextField libHf2;

    @FXML
    private MenuButton menuMois;

    @FXML
    private TextField montHf1;

    @FXML
    private TextField montHf2;

    @FXML
    private TextField montantKm;

    @FXML
    private TextField nuitee;

    @FXML
    private TextField qteKm;

    @FXML
    private TextField repasMid;

    @FXML
    private TextField totalKm;

    @FXML
    private TextField totalNuitee;

    @FXML
    private TextField totalRepasMid;

    // Initialisation d'attributs de la classe ThirdController 

    private String moisDebut;

    private String moisFin;

    private String matricule;

    String mdp;

    String idFiche;

    MenuItem[] tabItems = {};
    
    /* Méthode Initialize
        nom : initialize
        resultat : rien 
        objet de la méthode : La méthode initialize est une méthode de JavaFX qui se lance automatiquement au changement vers notre page,
        dans cette méthode on récupère le matricule de l'utilisateur de la classe utilisateur,
        on appelle la méthode "setNullTout", puis la méthode "remplirMenuMois"
        ensuite on crée une liste qui contient des "MenuItem" donc ce qui est contenu dans le menu des mois de la méthode : "remplirMenuMois",
        et si on clique sur un mois du menu cela appelle la méthode "selectMois" en fonction du mois du menu selectionné.
    */

    @FXML
    public void initialize() throws SQLException{
        matricule = utilisateur.getMatricule();
        setNullTout();
        remplirMenuMois();
        ObservableList<MenuItem> item = menuMois.getItems();
        item.forEach(menuItem ->{
            selectMois(menuItem);
        });
        }


    /* Méthode accueil
        Nom : accueil 
        Résultat : rien
        Paramètres : ActionEvent (qui est un événement déclenché par un bouton du clavier ou de la souris) nommé event
        Objet de la méthode : si le bouton auquel est attribué la méthode accueil est cliqué alors l'utilisateur est redirigé vers la page "accueilVisiteurs".
     */

    @FXML
    void accueil(ActionEvent event) throws IOException {
        App.setRoot("accueilVisiteurs");
    }

    /* Méthode remplirFiche
        Nom : remplirFiche 
        Résultat : rien
        Paramètres : ResultSet (format de résultat de requete SQL en Java) nommé res
        Objet de la méthode : sert a remplir les champs FXML de la page en fonction du résultat de la requete entré en paramètre
     */
    void remplirFiche(ResultSet res) throws SQLException{
        nuitee.setText(res.getString("ff_qte_nuitees"));
        repasMid.setText(res.getString("ff_qte_repas"));
        qteKm.setText(res.getString("ff_qte_km"));
        montantKm.setText(res.getString("prix_km"));
        Integer nuiteeNb = Integer.valueOf(nuitee.getText());
        totalNuitee.setText(String.valueOf(nuiteeNb*80));
        Integer repasNb = Integer.valueOf(repasMid.getText());
        totalRepasMid.setText(String.valueOf(repasNb*29));
        Integer kmNb = Integer.valueOf(qteKm.getText());
        double pxKm = Double.valueOf(montantKm.getText());
        totalKm.setText(String.valueOf(kmNb*pxKm));
        String reqHf = "SELECT hf_date,hf_libelle,hf_montant FROM gsb_etudiants.hors_forfait WHERE ff_id = '"+this.idFiche+"';";
        System.out.println("ici :"+idFiche);
        ResultSet resHf = Sqldb.executionRequete(reqHf);
        if (resHf.next()){
            System.out.println("ok");
            dateHf1.setValue(LocalDate.parse(resHf.getString("hf_date").toString(),DateTimeFormatter.ISO_DATE));
            libHf1.setText(resHf.getString("hf_libelle"));
            montHf1.setText(resHf.getString("hf_montant"));
            if(resHf.next()){
                System.out.println("ok2");
                dateHf2.setValue(LocalDate.parse(resHf.getString("hf_date").toString(),DateTimeFormatter.ISO_DATE));
                libHf2.setText(resHf.getString("hf_libelle"));
                montHf2.setText(resHf.getString("hf_montant"));
            }
        }
        
        
    
    }
    
    /* Méthode selectMois
        Nom : selectMois    
        Résultat : rien
        Paramètres : MenuItem (un des éléments d'un menuButton) nommé item
        Objet de la méthode : Si in MenuItem est cliqué alors cette fonction s'exécute, elle appelle la fonction setNullTout,
        fais une requete SELECT qui prend tt les champs necessaires à la fiche et les remplis 
     */
    private void selectMois(MenuItem item){
        item.setOnAction(event -> {
            setNullTout();
            String dateString = item.getText();
            String debutMois = dateString(dateString,01);
            this.moisDebut = debutMois;
            String finMois = dateString(dateString, 31);
            this.moisFin = finMois;
            System.out.println("Option "+item.getText()+" sélectionnée");
            String selectFevr = " SELECT ff_qte_nuitees, ff_qte_repas, ff_qte_km, prix_km,ff_id,ef_id FROM fiche_frais WHERE ff_mois BETWEEN '"+moisDebut+"' AND '"+moisFin+"' AND vi_matricule = '"+matricule+"'; ";
            System.out.println(selectFevr);
            try {
                ResultSet res = Sqldb.executionRequete(selectFevr);
                if (res.next()){
                    this.idFiche =  res.getString("ff_id");
                    remplirFiche(res);
                    System.out.println(selectFevr);
                    String etat = res.getString("ef_id");
                    System.out.println(etat);
                }else{
                    setNullTout();
                    System.out.println("Pas de fiche pour ce mois");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            try{
                String reqEtat = "SELECT ef.ef_libelle FROM fiche_frais AS ff \n" + //
                                "JOIN etat_fiche AS ef ON ff.ef_id = ef.ef_id \n" + //
                                "WHERE ff.ff_id = '"+this.idFiche+"';";
                    ResultSet resEtat = Sqldb.executionRequete(reqEtat);
                    if(resEtat.next()){
                        if(!resEtat.getString("ef_libelle").equals("créée")){
                            modifNon();
                        }else{
                            modifOk();
                        }
                        etatFiche.setText("Etat de la fiche : "+resEtat.getString("ef_libelle"));
                    }else{
                        System.out.println("bug");
                    }
            }catch (SQLException e){
                e.printStackTrace();
            }
        });
    }

    /* Méthode dateString
        Nom : dateString 
        Résultat : String (chaine de caracteres qui contient une date adaptée au format SQL)
        Paramètres : String date =  qui contient le mois et l'année du mois selectionné a partir de la liste du menu déroulant des mois au format texte.
                     Integer jour = qui contient le jour que l'on veut attribué au mois du 01 au 31 
        Objet de la méthode : La méthode permet de transfromer une date au format texte "humain" au format texte SQL 
     */
    String dateString(String date,Integer jour){
        String zero = "";
        if (jour<10){
            zero ="0";
        }else{
            zero = "";
        }
        LocalDate date1 = LocalDate.parse( zero +jour.toString()+ " " + date, DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH));
        // Formater la date en chaîne au format "AAAA-MM-JJ"
        String resultat = date1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(resultat);
        return resultat;
    }

    /* Méthode remplirMenuMois
        Nom : remplirMenuMois
        Résultat : rien
        Objet de la méthode : Permet de remplir la liste déroulante des 12 derniers mois à partir du mois courant 
     */
    void remplirMenuMois() throws SQLException{
        LocalDate dateActuelle = LocalDate.now();
        // Formatteur pour afficher les noms des mois
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        List<MenuItem> items = new ArrayList<>();

        // Ajout des 12 derniers mois sous forme de MenuItem
        for (int i = 0; i < 12; i++) {
            LocalDate moisPrecedent = dateActuelle.minusMonths(i);
            String nomMois = moisPrecedent.format(formatter);
            MenuItem menuItem = new MenuItem(nomMois);
            items.add(menuItem);
             
        }
        Collections.reverse(items);
        menuMois.getItems().addAll(items);
    }
    

    /* Méthode cloturer
        Nom : cloturer 
        Résultat : rien
        Paramètres : ActionEvent (qui est un événement déclenché par un bouton du clavier ou de la souris) nommé event
        Objet de la méthode : si le bouton auquel est attribué la méthode accueil est cliqué alors la fiche sera automatiquement cloturée donc avec le statut,
        ef_id à 2 avec la date de la cloture et appelle la méthode "modifNon" qui rend inéditable tout les champs et boutons
     */
    @FXML
    void cloturer(ActionEvent event) throws SQLException {  
        LocalDate dateajd =  LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateformat = dateajd.format(format);
        String clot = "UPDATE fiche_frais SET ff_date_cloture = '"+ dateformat +"' , ef_id = 2 WHERE ff_id = '"+idFiche+"'; ";
        System.out.println(clot);
        Sqldb.executionUpdate(clot);
        System.out.println("Cloturée ");
        modifNon();
    }

    /* Méthode sauvegarder
        Nom : Sauvegarder
        Résultat : rien
        Paramètres : ActionEvent (qui est un événement déclenché par un bouton du clavier ou de la souris) nommé event
        Objet de la méthode : Permet de sauvegarder toutes les modifications effectuées sur la fiche 
     */

    @FXML
    void sauvegarder(ActionEvent event) throws SQLException {
        Integer nbNuitee = Integer.valueOf(nuitee.getText());
        Integer nbRepas = Integer.valueOf(repasMid.getText());
        Integer quanteKm = Integer.valueOf(qteKm.getText());

        String hfLib1 = libHf1.getText();
        Double hfPx1 = 0.0;
        System.out.println(montHf1.getText());
        if (!montHf1.getText().equals("")){
            hfPx1 = Double.valueOf(montHf1.getText());
        }

        String hfLib2 = libHf2.getText();
        Double hfPx2 = 0.0;
        if (!montHf2.getText().equals("")){
            hfPx2 = Double.valueOf(montHf2.getText());
        }

        Integer cleEtrangPx = 1;
        String update = "UPDATE fiche_frais SET ff_qte_nuitees = "+nbNuitee+" ,ff_qte_repas = "+nbRepas+" ,ff_qte_km = "+quanteKm+" ,prix_km = "+cleEtrangPx+"\n" + //
                        "WHERE ff_id = '"+this.idFiche+"'; ";

        String getIdHf = "SELECT hf_id FROM hors_forfait WHERE ff_id = '"+this.idFiche+"';";
        System.out.println(getIdHf);
        ResultSet resIdHf = Sqldb.executionRequete(getIdHf);

        if (! resIdHf.next() && hfLib1 != null && hfPx1 != 0 && dateHf1.getValue() != null){
            String ajoutHf = String.format("INSERT INTO hors_forfait ( hf_date, hf_libelle, hf_montant,ff_id) VALUES ('%s','%s',%e,'%s')",dateHf1.getValue(),hfLib1,hfPx1,idFiche);
            System.out.println(ajoutHf);
            try {
                Sqldb.executionUpdate(ajoutHf);
            } catch (SQLException e ){
                e.printStackTrace();
            }
        }

        List<Integer> liste = new ArrayList<>();
        while(resIdHf.next()){
            liste.add(Integer.valueOf(resIdHf.getString("hf_id")));
            resIdHf.next();
        }

        System.out.println("hflib = "+hfLib2+ "  hfpx2 = "+hfPx2+"  datehf2 = "+ dateHf2.getValue());

        if (hfLib2 != null && hfPx2 != 0.0 && dateHf2.getValue() != null){
            String ajoutHf = String.format("INSERT INTO hors_forfait ( hf_date, hf_libelle, hf_montant,ff_id) VALUES ('%s','%s',"+hfPx2+", '%s')",dateHf2.getValue(),hfLib2,idFiche);
            System.out.println(ajoutHf);
            try {
                Sqldb.executionUpdate(ajoutHf);
            } catch (SQLException e ){
                e.printStackTrace();
            }
        }

        for (Integer i = 0; i < liste.size(); i++){
            String updateff = "UPDATE hors_forfait SET hf_libelle = '"+ hfLib1 +"' , hf_montant = "+ hfPx1 +", hf_date = '"+ dateHf1.getValue()+"' WHERE ff_id = '"+idFiche+"' AND hf_id = "+ liste.get(0)+";";
            System.out.println(updateff);
            try{
                Sqldb.executionUpdate(updateff);
            } catch(SQLException e){
                System.out.println(e);
            }
        }
        System.out.println(update);
        System.out.println(Sqldb.executionUpdate(update));
    }

    /* Méthode modifNon
        Nom : modifNon
        Résultat : rien
        Paramètres : ActionEvent (qui est un événement déclenché par un bouton du clavier ou de la souris) nommé event
        Objet de la méthode : Sert à rendre tout les champs FXML non-modifiables
     */
    void modifNon() {
        List<TextField> liste = new ArrayList<>(List.of(montantKm,nuitee,qteKm,repasMid,totalKm,totalNuitee,totalRepasMid));
        List<DatePicker> listeDate = new ArrayList<>(List.of(dateHf1,dateHf2));
        for (TextField element : liste){
            element.setEditable(false);
        }
        for (DatePicker elmnt : listeDate){
            elmnt.setEditable(true);
        }

        btnModif.setDisable(true);
        btnClot.setDisable(true);
        btnSave.setDisable(true);
    }

    /* Méthode setNullTout
        Nom : setNullTout
        Résultat : rien
        Paramètres : aucun
        Objet de la méthode : Sert à rendre tout les champs FXML vides en cas de changement de fiche
     */
    void setNullTout() {
        List<TextField> liste = new ArrayList<>(List.of(montantKm,nuitee,qteKm,repasMid,totalKm,totalNuitee,totalRepasMid,montHf1,montHf2,libHf1,libHf2));
        for (TextField element : liste){
            element.setText(" ");
        }
        dateHf1.setValue(null);
        dateHf2.setValue(null);
        etatFiche.setText("Etat de la fiche : n'existe pas ");
    }

    /* Méthode modifOk
        Nom : modifOk
        Résultat : rien
        Paramètres : ActionEvent (qui est un événement déclenché par un bouton du clavier ou de la souris) nommé event
        Objet de la méthode : Sert à rendre tout les champs FXML modifiables
     */
    void modifOk() {
        List<TextField> liste = new ArrayList<>(List.of(montantKm,nuitee,qteKm,repasMid,totalKm,totalNuitee,totalRepasMid));
        for (TextField element : liste){
            element.setEditable(true);
        }
        System.out.println("modification ok");
        btnModif.setDisable(false);
        btnClot.setDisable(false);
        btnSave.setDisable(false);
    }

    @Override
    public void start(@SuppressWarnings("exports") Stage arg0) throws Exception {

        throw new UnsupportedOperationException("Unimplemented method 'start'");

    }
}
