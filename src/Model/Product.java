package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Johnny Huynh
 */

/**
 * This class defines methods and attributes for product.
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, int stock, double price, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * This method deletes an associated part from the list.
     * @param selectedAssociatedPart Given part to delete
     * @return true or false of deletion
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(associatedParts.contains(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Returns list of associated parts.
     * @return associate parts list
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}
