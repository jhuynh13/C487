package Model;

/**
 * @author Johnny Huynh
 */

/**
 * This class defines InHouse with methods and attributes
 */
public class InHouse extends Part {

    private int machineId;

    /**
     * This constructor initializes InHouse instance with parameter values.
     * @param id Inhouse id
     * @param name Inhouse name
     * @param price Inhouse price
     * @param stock Inhouse stock
     * @param min Inhouse min
     * @param max Inhouse max
     * @param machineId Inhouse machine id
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {

        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setMachineId(machineId);
    }

    /**
     * This method sets machine id with parameter value.
     * @param machineId id
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * This method returns machine id
     * @return machine id
     */
    public int getMachineID() {
        return machineId;
    }
}