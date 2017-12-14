package fr.lr.iut.transportresolver.models;


/**
 * The Transport model class
 *
 * @author Daniel Medina
 * @since 10/12/2017
 */
public class Transport {
    // attributes
    private String number;
    private int capacity;
    private String color;

    // constructors
    public Transport(String number, int capacity, String color) {
        this.number = number;
        this.capacity = capacity;
        this.color = color;
    }

    // getters
    public String getNumber() {
        return number;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getColor() {
        return color;
    }
}
