package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Johnny Huynh
 */

/**
 * This class defines Inventory with methods and attributes
 */
public class Inventory {

    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * This method adds a new part into parts list.
     * @param newPart new part
     */
    public void addPart(Part newPart) {
        if (newPart != null) {
            allParts.add(newPart);
        }
    }

    /**
     * This method adds a new product into products list.
     * @param newProduct new product
     */
    public void addProduct(Product newProduct) {
        if (newProduct != null) {
            this.allProducts.add(newProduct);
        }
    }

    /**
     * This method returns a target part with given id.
     * @param partId target part id
     * @return part with matching id
     */
    public Part lookUpPart(int partId) {
        for (int x = 0; x < allParts.size(); x++) {
            if (allParts.get(x).getId() == partId) {
                return allParts.get(x);
            }
        }

        return null;
    }

    /**
     * This method returns product based on id.
     * @param productId target product id
     * @return product with matching id
     */
    public Product lookUpProduct(int productId) {

        for (int x = 0; x < allProducts.size(); x++) {
            if (allProducts.get(x).getId() == productId) {
                return allProducts.get(x);
            }
        }

        return null;
    }

    /**
     * This method returns a list of parts with matching parameter name.
     * @param partName target name to look for
     * @return list of parts with matching name
     */
    public ObservableList<Part> lookUpPart(String partName) {
        ObservableList<Part> tempParts = FXCollections.observableArrayList();

        for (Part p : allParts){
            if(p.getName().equals(partName)){
                tempParts.add(p);
            }
        }
        return tempParts;
    }

    /**
     * This method returns a list of products with matching target name.
     * @param productName target product name
     * @return list of products with matching target name
     */
    public ObservableList<Product> lookUpProduct(String productName) {
        ObservableList<Product> tempProducts = FXCollections.observableArrayList();

        for (Product p : allProducts){
            if(p.getName().equals(productName)){
                tempProducts.add(p);
            }
        }
        return tempProducts;
    }

    /**
     * This method changes a part with parameter part at given index.
     * @param index location of part
     * @param selectedPart new part to replace
     */
    public void updatePart(int index, Part selectedPart) {
        if(index >= 0 && index < allParts.size()){
            allParts.set(index, selectedPart);
        }
    }

    /**
     * This methods changes a product with paramenter product at given index.
     * @param index location of product
     * @param newProduct new product to replace
     */
    public void updateProduct(int index, Product newProduct) {

        if(index >= 0 && index < allProducts.size()){
            allProducts.set(index, newProduct);
        }

    }

    /**
     * This method returns true if target part exists and deletes it from list.
     * @param selectedPart target part to look for
     * @return true or false based on successfully finding part
     */
    public boolean deletePart(Part selectedPart) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == selectedPart.getId()) {
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns true if target product exists and deletes it from list.
     * @param selectedProduct target product to look for
     * @return true or false based on successfully finding product
     */
    public boolean deleteProduct(Product selectedProduct) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == selectedProduct.getId()) {
                allProducts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns current list of parts
     * @return list of parts
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This method returns current list of products
     * @return list of products
     */
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
