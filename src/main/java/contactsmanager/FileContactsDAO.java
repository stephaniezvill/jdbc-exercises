package contactsmanager;
import contactsmanager.Contact;
import java.nio.file.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileContactsDAO implements ContactsDAO {
    private Path pathToContacts = Paths.get("contacts.txt");

    @Override
    public List<Contact> fetchContacts() {
        List<Contact> contacts = new ArrayList<>();
        try {
            List<String> contactsFromFile = Files.readAllLines(pathToContacts);
            for (String line : contactsFromFile) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String phoneNumber = parts[1].trim();
                    Contact contact = new Contact(name, phoneNumber);
                    contacts.add(contact);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
        return contacts;
    }

    @Override
    public long insertContact(Contact contact) {
        try {
            String contactString = contact.getName() + " | " + contact.getPhoneNumber() + System.lineSeparator();
            Files.write(pathToContacts, (contactString + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
            fetchContacts();
            return 1;
        } catch (IOException e) {
            System.out.println("Error inserting contact into file: " + e.getMessage());
            return 0;
        }
    }
    @Override
    public void deleteByName(String name) {
        try {
            List<String> lines = Files.readAllLines(pathToContacts);
            Iterator<String> iterator = lines.iterator();

            while (iterator.hasNext()) {
                String line = iterator.next();
                if (line.toLowerCase().contains(name.toLowerCase())) {
                    iterator.remove();
                    break;
                }
            }

            Files.write(pathToContacts, lines);
        } catch (IOException e) {
            System.out.println("Error deleting contact from file: " + e.getMessage());
        }
    }

    @Override
    public List<Contact> searchContacts(String searchTerm) {
        List<Contact> foundContacts = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(pathToContacts);

            for (String line : lines) {
                if (line.toLowerCase().contains(searchTerm.toLowerCase())) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 2) {
                        String name = parts[0].trim();
                        String phoneNumber = parts[1].trim();
                        Contact contact = new Contact(name, phoneNumber);
                        foundContacts.add(contact);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error searching contacts in file: " + e.getMessage());
        }
        return foundContacts;
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {
    }
}

