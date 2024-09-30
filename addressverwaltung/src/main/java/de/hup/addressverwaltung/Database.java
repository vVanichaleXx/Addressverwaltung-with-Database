package de.hup.addressverwaltung;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Database implements AutoCloseable {
    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final Connection connection;

    public Database() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:7000/postgres", "postgres", "password");
    }

    public List<Address> getAddresses() {
        List<Address> addresses = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM addresses")) {
                while (resultSet.next()) {
                    Address address = new Address(resultSet.getInt("id"),
                    resultSet.getString("strAndHausnummer"),
                    resultSet.getInt("postfach"),
                    resultSet.getInt("plz"),
                    resultSet.getString("ort"));
                    addresses.add(address);
                }
            }
        } catch (SQLException e) {
            log.severe(e.getMessage());
        }
        return addresses;
    }


    public void saveAddress(Address address) throws SQLException {
        String query = "INSERT INTO addresses (strAndHausnummer, postfach, plz, ort) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, address.getStrandHausnummer());
            preparedStatement.setInt(2, address.getPostfach());
            preparedStatement.setInt(3, address.getPlz());
            preparedStatement.setString(4, address.getOrt());
            preparedStatement.executeUpdate();
        }
    }

    public void updateAddressStreet(int id, String street) throws SQLException {
        String query = "UPDATE addresses SET strandhausnummer = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, street);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
    }

    public void updateAddressPostfach(int id, int postfach) throws SQLException {
        String query = "UPDATE addresses SET postfach = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, postfach);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
    }

    public void updateAddressPlz(int id, int plz) throws SQLException {
        String query = "UPDATE addresses SET plz = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, plz);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
    }

    public void updateAddressOrt(int id, String ort) throws SQLException {
        String query = "UPDATE addresses SET ort = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, ort);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
    }

    public int deleteAddress(String strAndHausnummer) throws SQLException {
        String query = """
            DELETE FROM addresses
            WHERE LOWER(strAndHausnummer) LIKE CONCAT('%', LOWER(?), '%')""";

        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, strAndHausnummer);
            return statement.executeUpdate();
        }
    }

    public void savePerson(String name, int addressId) throws SQLException {
        String query = "INSERT INTO people (name, address_id) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, addressId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
