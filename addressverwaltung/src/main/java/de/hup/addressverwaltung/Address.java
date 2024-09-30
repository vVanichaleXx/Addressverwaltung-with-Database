package de.hup.addressverwaltung;

public class Address {
    private int id;
    private String street;
    private int postfach;
    private int plz;
    private String ort;

    public Address(int id, String street, int postfach, int plz, String ort) {
        this.id = id;
        this.street = street;
        this.postfach = postfach;
        this.plz = plz;
        this.ort = ort;
    }

    public int getId() {
        return id;
    }

    public String getStrandHausnummer() {
        return street;
    }

    public int getPostfach() {
        return postfach;
    }

    public int getPlz() {
        return plz;
    }

    public String getOrt() {
        return ort;
    }
    @Override
    public String toString() {
        return String.format("%d, %s, %d, %d, %s", id, street, postfach, plz, ort);
    }
}