package contactsmanager;

import java.util.*;
import java.util.Scanner;
public class ContactsManager {
    private ContactsDAO contactsDAO;
    private Scanner scanner;

    public ContactsManager() {
        this.contactsDAO = new MySQLContactsDAO();
        this.scanner = new Scanner(System.in);
        this.contactsDAO.open();
    }

    public static void main(String[] args) {
        ContactsManager contactsManager = new ContactsManager();
        contactsManager.run();
        contactsManager.close();
    }

    public void run() {
        int choice;
        do {
            showMainMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    List<Contact> fetchedContacts = contactsDAO.fetchContacts();
                    if (fetchedContacts.isEmpty()) {
                        System.out.println("No contacts found.");
                    } else {
                        System.out.println("Contacts:");
                        for (Contact contact : fetchedContacts) {
                            System.out.println(contact.getName() + " | " + contact.getPhoneNumber());
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter the name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter the phone number: ");
                    String phoneNumber = scanner.nextLine();
                    Contact newContact = new Contact(name, phoneNumber);

                    long result = contactsDAO.insertContact(newContact);
                    if (result == 1) {
                        System.out.println("Contact inserted successfully.");
                    } else {
                        System.out.println("Error inserting contact. Please try again.");
                    }
                    break;
                case 3:
                    System.out.print("Enter the name to search: ");
                    String searchName = scanner.nextLine();
                    List<Contact> searchResults = contactsDAO.searchContacts(searchName);

                    if (searchResults.isEmpty()) {
                        System.out.println("No contacts found.");
                    } else {
                        System.out.println("Search results:");
                        for (Contact resultContact : searchResults) {
                            System.out.println(resultContact.getName() + " | " + resultContact.getPhoneNumber());
                        }
                    }
                    break;
                case 4:
                    System.out.print("Enter the name to delete: ");
                    String deleteName = scanner.nextLine();
                    contactsDAO.deleteByName(deleteName);
                    System.out.println("Contact deleted successfully.");
                    break;

                case 5:
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        } while (choice != 5);
    }

    private void showMainMenu() {
        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact by name.");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Exit.");
        System.out.print("Enter an option (1, 2, 3, 4, or 5): ");
    }
    private void close() {
        this.contactsDAO.close();
    }
}