package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Johnny Huynh
 */

/**
 * This class provides methods and logic for functionality to modify part scene.
 */
public class ModifyPartController implements Initializable {

    public TextField id;
    public TextField min;
    public TextField max;
    public TextField price;
    public TextField inventory;
    public TextField name;
    public RadioButton inHouse;
    public TextField uniqueSource2;
    public Label uniqueSource1;
    public RadioButton outsourced;

    Inventory inv;
    Part p;

    /**
     * This constructor initializes inventory and part.
     * @param inv
     * @param p
     */
    public ModifyPartController (Inventory inv, Part p){

        this.inv = inv;
        this.p = p;
    }

    /**
     * This method fills in text fields with values from inventory.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id.setText(String.valueOf(p.getId()));
        name.setText(p.getName());
        inventory.setText(String.valueOf(p.getStock()));
        price.setText(String.valueOf(p.getPrice()));
        max.setText(String.valueOf(p.getMax()));
        min.setText(String.valueOf(p.getMin()));

        if(p instanceof InHouse){
            uniqueSource2.setText(String.valueOf(((InHouse)p).getMachineID()));
            inHouse.setSelected(true);
        }
        else{
            uniqueSource2.setText(String.valueOf(((Outsourced)p).getCompanyName()));
            outsourced.setSelected(true);
        }
    }

    /**
     * This method changes label name to InHouse.
     * @param actionEvent
     */
    public void selectInHouse(ActionEvent actionEvent) {
        uniqueSource1.setText("Machine ID");
    }

    /**
     * This methods changes label name to Outsourced.
     * @param actionEvent
     */
    public void selectOutsourced(ActionEvent actionEvent) {

        uniqueSource1.setText("Company Name");
    }

    /**
     * This method changes scene back to main scene.
     * @param actionEvent
     * @throws IOException
     */
    public void toMain(ActionEvent actionEvent) throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/View/Main.fxml"));
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
     * This method stores all changes to inventory and returns to the main scene.
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
            if(inHouse.isSelected()){
                Part newPart = (new InHouse(p.getId(), name.getText(), Double.parseDouble(price.getText()),
                        Integer.parseInt(inventory.getText()), Integer.parseInt(min.getText()),
                        Integer.parseInt(max.getText()), Integer.parseInt(uniqueSource2.getText())));
                for(int i = 0; i < inv.getAllParts().size(); i++) {
                    if (inv.getAllParts().get(i).equals(p)) {
                        inv.updatePart(i, newPart);
                    }
                }
            }
            else{
                Part newPart = (new Outsourced(p.getId(), name.getText(), Double.parseDouble(price.getText()),
                        Integer.parseInt(inventory.getText()), Integer.parseInt(min.getText()),
                        Integer.parseInt(max.getText()), uniqueSource2.getText()));
                for(int i = 0; i < inv.getAllParts().size(); i++) {
                    if (inv.getAllParts().get(i).equals(p)) {
                        inv.updatePart(i, newPart);
                    }
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

        //Update stage with new data from inventory
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
