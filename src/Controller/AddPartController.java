package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Johnny Huynh
 */

/**
 * This class provides methods and logic for the functionality of add part scene.
 */
public class AddPartController implements Initializable {

    public Button cancelButton;
    public RadioButton inHouse;
    public ToggleGroup source;
    public RadioButton outsourced;
    public TextField min;
    public TextField max;
    public TextField price;
    public TextField inventory;
    public TextField name;
    public Label uniqueSource1;
    public Button save;
    public TextField uniqueSource2;

    Inventory inv;

    /**
     * This contructor initializes the inventory
     * @param inv
     */
    public AddPartController (Inventory inv){
        this.inv = inv;
    }

    /**
     * This method sets default values for the scene.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    /**
     * This method changes the scene back to main scene.
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
     * This method changes part type to InHouse.
     * @param actionEvent Radio button clicked on GUI
     */
    public void selectInHouse(ActionEvent actionEvent) {
        uniqueSource1.setText("Machine ID");
    }

    /**
     * This method changes part type to Outsourced.
     * @param actionEvent Radio button clicked on GUI
     */
    public void selectOutsourced(ActionEvent actionEvent) {

        uniqueSource1.setText("Company Name");
    }

    /**
     * This method creates a unique id when part is created.
     * @return Id of part
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
     * This method stores all input fields into inventory then returns back to main scene.
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
            if (inHouse.isSelected()) {
                inv.addPart(new InHouse(generateId(), name.getText(), Double.parseDouble(price.getText()),
                        Integer.parseInt(inventory.getText()), Integer.parseInt(min.getText()),
                        Integer.parseInt(max.getText()), Integer.parseInt(uniqueSource2.getText())));
            } else {
                inv.addPart(new Outsourced(generateId(), name.getText(), Double.parseDouble(price.getText()),
                        Integer.parseInt(inventory.getText()), Integer.parseInt(min.getText()),
                        Integer.parseInt(max.getText()), uniqueSource2.getText()));
            }
        }
        //Error handling integer and string mismatch typing
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
            if (inHouse.isSelected() && (uniqueSource2.getText().equals("") ||
                    (inHouse.isSelected() && !(uniqueSource2.getText().trim().matches("[0-9]*"))))){
                check = true;
                errors.add("Machine ID field must contain numbers and cannot be empty");
            }

            if (outsourced.isSelected() && (uniqueSource2.getText().equals("")) ||
                    (outsourced.isSelected() && uniqueSource2.getText().trim().matches("[0-9]*"))){

                check = true;
                errors.add("Company name must contain letters and cannot be empty");
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

        //Load new stage with updated inventory
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
