package Main;

import Model.InHouse;
import Model.Inventory;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Javadoc folder is a subfolder in C482 folder
 *
 * @author Johnny Huynh
 */

/** This is the main class containing initial data and fxml loader to launch view. */
public class Main extends Application {

    /** Method to set the stage for main scene.
     It creates an inventory with default values into the table.
     @param primaryStage The main stage for scenes
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Inventory inv = new Inventory();

        //Add initial parts
        inv.addPart(new InHouse(1,"Brakes",12.99,15,1,15, 1));
        inv.addPart(new InHouse(2,"Tire",14.99,15,1,15, 2));
        inv.addPart(new InHouse(3,"Rim",56.99,15,1,15, 3));

        //Add initial products
        inv.addProduct(new Product(1,"Giant Bicycle",15,299.99,1,15));
        inv.addProduct(new Product(2,"Scott Bicycle",15,199.99,1,15));
        inv.addProduct(new Product(3,"GT Bike",15,99.99,1,15));

        //Load scene builder
        FXMLLoader load = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
        Controller.Controller controller = new Controller.Controller(inv);
        load.setController(controller);
        Parent root = load.load();

        primaryStage.setTitle("Main Scene");
        primaryStage.setScene(new Scene(root, 950, 600));
        primaryStage.show();
    }

    /** This is the main method.
     This gets called when program initially runs.
     @param args Arguments for launch
     */
    public static void main(String[] args) {

        launch(args);
    }
}
