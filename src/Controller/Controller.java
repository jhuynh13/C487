package Controller;

import Model.Inventory;
import javafx.application.Platform;
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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Johnny Huynh
 */

/** This is the main controller class for initializing logic for main scene. */
public class Controller implements Initializable {

    public TableView partsTable;
    public TableColumn partId;
    public TableColumn partName;
    public TableColumn partsInventory;
    public TableColumn partsCost;
    public TableView productsTable;
    public TableColumn productId;
    public TableColumn productName;
    public TableColumn productsInventory;
    public TableColumn productsCost;
    public Button partsAdd;
    public Button partsModify;
    public Button partsDelete;
    public Button productsAdd;
    public Button productsModify;
    public Button productsDelete;
    public TextField productQuery;
    public TextField partQuery;

    Inventory inv;

    /** Constructor for initializing inventory. */
    public Controller (Inventory inv){
        this.inv = inv;
    }

    /**
     * This method changes scene to add part scene.
     * @param actionEvent Button click on GUI
     * @throws IOException
     */
    @FXML
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/view/AddPart.fxml"));
        AddPartController controller = new AddPartController(inv);
        load.setController(controller);
        Parent root = load.load();

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 680, 400);
        stage.setTitle("Add Part Form Scene");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method changes scene to Add Products Form scene upon action event
     * @param actionEvent Button click on GUI
     * @throws IOException
     */
    @FXML
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/view/AddProduct.fxml"));
        AddProductController controller = new AddProductController(inv);
        load.setController(controller);
        Parent root = load.load();

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Add Product Form Scene");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method changes to modify part scene.
     * @param actionEvent Button click on GUI
     * @throws IOException
     */
    @FXML
    public void toModifyPart(ActionEvent actionEvent) throws IOException {

        Part p = (Part)partsTable.getSelectionModel().getSelectedItem();
        if(p != null){
            FXMLLoader load = new FXMLLoader(getClass().getResource("/view/ModifyPart.fxml"));
            ModifyPartController controller = new ModifyPartController(inv, p);
            load.setController(controller);
            Parent root = load.load();

            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 680, 400);
            stage.setTitle("Modify Part Form Scene");
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Unable to modify part");
            alert.setContentText("A part must be selected before clicking modify.");

            alert.showAndWait();
        }
    }

    /**
     * This method exits the program.
     * @param actionEvent Button click on GUI
     */
    @FXML
    private void exit(ActionEvent actionEvent){
        Platform.exit();
    }

    /**
     * This method moves to modify product scene.
     * @param actionEvent Button click on GUI
     * @throws IOException
     */
    @FXML
    public void toModifyProduct(ActionEvent actionEvent) throws IOException {

        Product p = (Product)productsTable.getSelectionModel().getSelectedItem();
        if(p != null){
            FXMLLoader load = new FXMLLoader(getClass().getResource("/view/ModifyProduct.fxml"));
            ModifyProductController controller = new ModifyProductController(inv, p);
            load.setController(controller);
            Parent root = load.load();

            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Modify Product Form Scene");
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Unable to modify product");
            alert.setContentText("A product must be selected before clicking modify.");

            alert.showAndWait();
        }
    }

    /**
     * This method removes a product from inventory.
     * @param actionEvent Button click on GUI
     * @throws Exception
     */
    @FXML
    public void removeProduct(ActionEvent actionEvent) throws Exception {

        Product p = (Product)productsTable.getSelectionModel().getSelectedItem();
        if(p == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Unable to remove product");
            alert.setContentText("A product must be selected before clicking remove.");

            alert.showAndWait();
            return;
        }

        if(!(p.getAllAssociatedParts().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Cannot delete product with associated part");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Removing Product Confirmation");
        alert.setHeaderText("Are you sure you want to remove?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            inv.getAllProducts().remove(p);
        } else {
            return;
        }
    }

    /**
     * This method removes a part from inventory.
     * @param actionEvent Button click on GUI
     */
    @FXML
    public void removePart(ActionEvent actionEvent){
        boolean check = false;
        Part p = (Part)partsTable.getSelectionModel().getSelectedItem();
        if(p == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Unable to remove");
            alert.setContentText("A part must be selected before clicking remove.");

            alert.showAndWait();
            return;
        }

        for(Product prod : inv.getAllProducts()){
            if(prod.getAllAssociatedParts().contains(p)){
                check = true;
            }
        }

        if(check == false){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Removing Part Confirmation");
            alert.setHeaderText("Are you sure you want to remove?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                inv.getAllParts().remove(p);
            } else {
                return;
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Unable to remove");
            alert.setContentText("This is an associated part with a product.");

            alert.showAndWait();
            return;
        }
    }

    /**
     * This method sets the table cells with values.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(inv.getAllProducts());
        partsTable.setItems(inv.getAllParts());
    }

    /**
     * This handler method tailors parts search results according to search input.
     * @param actionEvent Text field input
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
     * This method processes retrieval of list of strings to match parts search input
     * @param partialName Text field input
     * @return List of matching parts with same name
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
     * This method retrieves a part with matching parameter id.
     * @param id id of part
     * @return Part that has matching id
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
     * This method tailors products based on matching product description.
     * @param actionEvent Text field for products
     */
    public void showProductsHandler(ActionEvent actionEvent){
        String q = productQuery.getText();

        ObservableList<Product>products = searchByProductName(q);

        try{
            int id = Integer.parseInt(q);
            Product p = getProductWithId(id);
            if(p != null){
                products.add(p);
            }
        }
        catch (NumberFormatException e){

        }

        productsTable.setItems(products);
        productQuery.setText("");
    }

    /**
     * This method returns a list of products that match parameter name.
     * @param partialName String to match products criteria
     * @return Return matching product names
     */
    private ObservableList<Product> searchByProductName(String partialName){
        ObservableList<Product> productNames = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = inv.getAllProducts();

        for(Product p : allProducts){
            if(p.getName().contains(partialName)) {
                productNames.add(p);
            }
        }

        return productNames;
    }

    /**
     * This method returns product with matching parameter id.
     * @param id Id to search for
     * @return A product that has matching id
     */
    private Product getProductWithId(int id){
        ObservableList<Product> allProducts = inv.getAllProducts();

        for (Product p : allProducts){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }

}
