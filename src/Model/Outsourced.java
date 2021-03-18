package Model;

/**
 * @author Johnny Huynh
 */

/**
 * This class defines Outsourced with methods and attributes
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * Constructor defines class attributes with parameter values.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {

        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setCompanyName(companyName);
    }

    /**
     * Sets current company name with given company name.
     * @param companyName company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Returns current company name
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }
}