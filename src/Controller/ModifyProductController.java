package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Johnny Huynh
 */

/**
 * This class provides methods and logic for functionality of modify product scene.
 */
public class ModifyProductController implements Initializable {

    public TableView partsTable;
    public TableColumn partId;
    public TableColumn partName;
    public TableColumn partsInventory;
    public TableColumn partsCost;
    public TableView partsTable2;
    public TableColumn partId2;
    public TableColumn partName2;
    public TableColumn partsInventory2;
    public TableColumn partsCost2;
    public TextField partQuery;
    public Button addButton;
    public Button removeButton;
    public Button cancel;
    public TextField id;
    public TextField min;
    public TextField max;
    public TextField price;
    public TextField inventory;
    public TextField name;

    Inventory inv;
    ObservableList<Part> assocParts;
    Product p;

    /**
     * This constructor initializes inventory and product.
     * @param inv Inventory
     * @param p Part
     */
    public ModifyProductController (Inventory inv, Product p){

        this.inv = inv;
        this.p = p;
        assocParts = p.getAllAssociatedParts();
    }

    /**
     * This method sets the tables and text fields for data.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        partId2.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName2.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventory2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsCost2.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(inv.getAllParts());

        partsTable2.setItems(assocParts);

        id.setText(String.valueOf(p.getId()));
        name.setText(p.getName());
        inventory.setText(String.valueOf(p.getStock()));
        price.setText(String.valueOf(p.getPrice()));
        max.setText(String.valueOf(p.getMax()));
        min.setText(String.valueOf(p.getMin()));


    }

    /**
     * This method creates a unique id for part.
     * @return unique part id
     */
    public int generateId(){
        if(inv.getAllParts().size() == 0){
            return 1;
        }
        else{
            return inv.getAllParts().size()+1;
        }
    }

    /**
     * This method returns back to main scene.
     * @param actionEvent Button clicked on GUI
     * @throws IOException
     */
    public void toMain(ActionEvent actionEvent) throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
        Controller controller = new Controller(inv);
        load.setController(controller);
        Parent root = load.load();

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 950, 550);
        stage.setTitle("Main Scene");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method returns a list of parts with matching parameter name.
     * @param partialName name to search for
     * @return list of parts with matching partial names
     */
    private ObservableList<Part> searchByPartName(String partialName){
        ObservableList<Part> partNames = FXCollections.observableArrayList();

        ObservableList<Part> allParts = inv.getAllParts();

        for(Part p : allParts){
            if(p.getName().contains(partialName)) {
                partNames.add(p);
            }
        }

        return partNames;
    }

    /**
     * This method filters parts through criteria in text field.
     * @param actionEvent Text field for filter
     */
    public void showPartsHandler(ActionEvent actionEvent){
        String query = partQuery.getText();

        ObservableList<Part>parts = searchByPartName(query);

        try{
            int id = Integer.parseInt(query);
            Part p = getPartWithId(id);
            if(p != null){
                parts.add(p);
            }
        }
        catch (NumberFormatException e){

        }

        partsTable.setItems(parts);
        partQuery.setText("");
    }

    /**
     * This method returns part with matching parameter id.
     * @param id part id to search for
     * @return part with matching id
     */
    private Part getPartWithId(int id){
        ObservableList<Part> allParts = inv.getAllParts();

        for (Part p : allParts){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }

    /**
     * This method takes selected part and adds it to associated parts list.
     * @param actionEvent Button clicked on GUI
     * @throws IOException
     */
    @FXML
    public void addAssocPart(ActionEvent actionEvent) throws IOException {

        Part p = (Part)partsTable.getSelectionModel().getSelectedItem();
        if(p == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Unable to add associated part");
            alert.setContentText("A part must be selected before clicking add.");

            alert.showAndWait();
            return;
        }
        else{
            assocParts.add(p);

            partsTable2.setItems(assocParts);
        }
    }

    /**
     * This method removes associated part from the table and list.
     * @param actionEvent Button clicked on GUI
     * @throws IOException
     */
    @FXML
    public void removeAssoc(ActionEvent actionEvent) throws IOException {
        Part p = (Part)partsTable2.getSelectionModel().getSelectedItem();
        if(p == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Unable to add associated part");
            alert.setContentText("A part must be selected before clicking add.");
            alert.showAndWait();
            return;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Removing Associated Part Confirmation");
            alert.setHeaderText("Are you sure you want to remove?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                assocParts.remove(p);
            } else {
                return;
            }
        }
    }

    /**
     * This method stores changes to textfields and tables into inventory and returns to main scene.
     * @param actionEvent Button clicked on GUI
     * @throws IOException
     */
    public void save (ActionEvent actionEvent) throws IOException {

        boolean check = false;
        ArrayList<String> errors = new ArrayList<String>();

        try{
            //Logical validation
            if (Double.parseDouble(inventory.getText()) < Double.parseDouble(min.getText()) ||
                    Double.parseDouble(inventory.getText()) > Double.parseDouble(max.getText())) {
                check = true;
                errors.add("Inventory number should be between min and max");
            }
            if (Integer.parseInt(min.getText()) >= Integer.parseInt(max.getText())) {
                check = true;
                errors.add("Min should be less than max");
            }
            if (Integer.parseInt(max.getText()) <= Integer.parseInt(min.getText())) {
                check = true;
                errors.add("max should be greater than min");
            }

            if (check) {
                String message = "";
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Look, an Error Dialog");
                for (String temp : errors) {
                    message = message + temp + "\n";
                }

                alert.setContentText(message);

                alert.showAndWait();
                return;
            }

            //Instantiate part object
            Product prod = new Product(p.getId(), name.getText(), Integer.parseInt(inventory.getText()),
                    Double.parseDouble(price.getText()), Integer.parseInt(min.getText()),
                    Integer.parseInt(max.getText()));
            for(Part p : assocParts){
                prod.addAssociatedPart(p);
            }
            for(int i = 0; i < inv.getAllProducts().size(); i++){
                if(inv.getAllProducts().get(i).equals(prod)){
                    inv.updateProduct(i,prod);
                }
            }
        }
        catch(NumberFormatException e){

            //Input format validation
            if (name.getText().equals("")) {
                check = true;
                errors.add("Name field cannot be empty");
            }
            if ((!(price.getText().trim().matches("^\\d*\\.\\d+|\\d+\\.\\d*$")) &&
                    !price.getText().trim().matches("[0-9]*")) || price.getText().equals("")) {
                check = true;
                errors.add("Price/Cost field should only contain numbers");
            }

            if (inventory.getText().isEmpty() || !inventory.getText().trim().matches("[0-9]*")) {
                check = true;
                errors.add("Inventory field should only contain numbers");
            }
            if (max.getText().equals("") || !(max.getText().trim().matches("[0-9]*"))) {
                check = true;
                errors.add("Max field should only contain numbers");
            }

            if (min.getText().equals("") || !(min.getText().trim().matches("[0-9]*"))) {
                check = true;
                errors.add("Min field should only contain numbers");
            }

            if((max.getText().trim().matches("[0-9]*") && (min.getText().trim().matches("[0-9]*")))){
                if(!max.getText().equals("") && !min.getText().equals("")){
                    if (Integer.parseInt(min.getText()) >= Integer.parseInt(max.getText())) {
                        check = true;
                        errors.add("Min should be less than max");
                    }
                    if (Integer.parseInt(max.getText()) <= Integer.parseInt(min.getText())) {
                        check = true;
                        errors.add("max should be greater than min");
                    }
                }
            }
            if (check) {
                String message = "";
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Look, an Error Dialog");
                for (String temp : errors) {
                    message = message + temp + "\n";
                }

                alert.setContentText(message);

                alert.showAndWait();
                return;
            }
        }

        //Update stage with new data in inventory
        FXMLLoader load = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
        Controller controller = new Controller(inv);
        load.setController(controller);
        Parent root = load.load();
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 950, 550);
        stage.setTitle("Main Scene");
        stage.setScene(scene);
        stage.show();

    }
}
