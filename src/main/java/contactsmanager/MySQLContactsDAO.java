package contactsmanager;

import config.Config;
import contactsmanager.Contact;
import contactsmanager.ContactsDAO;
import com.mysql.cj.jdbc.Driver;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLContactsDAO implements ContactsDAO {
    private Connection connection;

    public MySQLContactsDAO() {
       open();
    }

    @Override
    public List<Contact> fetchContacts() {
        List<Contact> contacts = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String sql = "SELECT name, phone FROM Contacts";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                Contact contact = new Contact(name, phone);
                contacts.add(contact);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching contacts from database: " + e.getMessage());
        }
        return contacts;
    }

    @Override
    public long insertContact(Contact contact) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO Contacts (name, phone) VALUES (?, ?)")) {
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getPhoneNumber());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0 ? 1 : 0;
        } catch (SQLException e) {
            System.out.println("Error inserting contact into database: " + e.getMessage());
            return 0;
        }
    }
    @Override
    public void deleteByName(String name) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Contacts WHERE name = ?")) {
            statement.setString(1, name.toLowerCase());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting contact from database: " + e.getMessage());
        }
    }
    @Override
    public List<Contact> searchContacts(String searchTerm) {
        List<Contact> foundContacts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT name, phone FROM Contacts WHERE LOWER(name) LIKE ?")) {
            statement.setString(1, "%" + searchTerm.toLowerCase() + "%");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                Contact contact = new Contact(name, phone);
                foundContacts.add(contact);
            }
        } catch (SQLException e) {
            System.out.println("Error searching contacts in database: " + e.getMessage());
        }
        return foundContacts;
    }
    @Override
    public void close() {
    }

    public void open () {
        try { DriverManager.registerDriver(new Driver() {
        });
            connection = DriverManager.getConnection(
                    Config.getUrl(),
                Config.getUser(),
                Config.getPassword());

            System.out.println("Connection created" );
        } catch (SQLException e) {
            System.out.println("Connection failed!!");
        }
    }

    private void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}