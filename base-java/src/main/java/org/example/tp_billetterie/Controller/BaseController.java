package org.example.tp_billetterie.Controller;

import org.example.tp_billetterie.Entity.Address;
import org.example.tp_billetterie.Entity.Venue;
import org.example.tp_billetterie.Exception.NotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public abstract class BaseController<T> implements MenuController {
    protected final Scanner scanner;
    protected final String entityName;

    public BaseController(Scanner scanner, String entityName) {
        this.scanner = scanner;
        this.entityName = entityName;
    }

    @Override
    public boolean handleChoice(int choice) {
        try {
            return executeChoice(choice);
        } catch (NotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return true;
        }
    }

    protected abstract boolean executeChoice(int choice) throws Exception;

    protected void printCreateHeader() {
        System.out.println("\n=== CREATE " + entityName.toUpperCase() + " ===");
    }

    protected void printReadAllHeader() {
        System.out.println("\n=== ALL " + entityName.toUpperCase() + "S ===");
    }

    protected void printReadByIdHeader() {
        System.out.println("\n=== " + entityName.toUpperCase() + " DETAILS ===");
    }

    protected void printSuccessMessage(String action) {
        System.out.println(entityName + " " + action + " successfully!");
    }

    protected void printNoEntitiesFound() {
        System.out.println("No " + entityName.toLowerCase() + "s found.");
    }

    protected void printEntitiesList(List<T> entities) {
        if (entities.isEmpty()) {
            printNoEntitiesFound();
            return;
        }
        for (T entity : entities) {
            System.out.println(entity);
        }
    }

    protected int promptForId(String action) {
        System.out.print("Enter " + entityName.toLowerCase() + " ID to " + action + ": ");
        return Integer.parseInt(scanner.nextLine());
    }

    protected String promptForString(String field) {
        System.out.print(field + ": ");
        return scanner.nextLine().trim();
    }

    protected String promptForStringWithDefault(String field, String currentValue) {
        System.out.print("New " + field + " (current: " + currentValue + ", press Enter to keep): ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? currentValue : input;
    }

    protected int promptForInt(String field) {
        System.out.print(field + ": ");
        return Integer.parseInt(scanner.nextLine());
    }

    protected int promptForIntWithDefault(String field, int currentValue) {
        System.out.print("New " + field + " (current: " + currentValue + ", press Enter to keep): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return currentValue;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, keeping current value: " + currentValue);
            return currentValue;
        }
    }

    protected LocalDate promptForDateWithDefault(String field, LocalDate currentValue) {
        System.out.print("New " + field + " (current: " + currentValue + ", press Enter to keep): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return currentValue;
        }
        try {
            return LocalDate.parse(input);
        } catch (Exception e) {
            System.out.println("Invalid date format, keeping current value: " + currentValue);
            return currentValue;
        }
    }

    protected LocalTime promptForTimeWithDefault(String field, LocalTime currentValue) {
        System.out.print("New " + field + " (current: " + currentValue + ", press Enter to keep): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return currentValue;
        }
        try {
            return LocalTime.parse(input);
        } catch (Exception e) {
            System.out.println("Invalid time format, keeping current value: " + currentValue);
            return currentValue;
        }
    }

    protected boolean promptForConfirmation(String message) {
        System.out.print(message + " (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        return confirmation.equals("yes");
    }

    protected boolean validateDeletionWithTickets(int ticketCount) {
        if (ticketCount > 0) {
            String message = "This " + entityName.toLowerCase() + " has " + ticketCount +
                    " tickets. Are you sure you want to delete?";
            if (!promptForConfirmation(message)) {
                System.out.println("Operation cancelled.");
                return false;
            }
        }
        return true;
    }

    protected void showStandardMenu(String... extraOptions) {
        System.out.println("\n--- " + entityName.toUpperCase() + " MANAGEMENT ---");
        System.out.println("1. Create " + entityName);
        System.out.println("2. Read All " + entityName + "s");
        System.out.println("3. Read " + entityName + " By ID");
        System.out.println("4. Update " + entityName);
        System.out.println("5. Delete " + entityName);

        int optionNumber = 6;
        for (String option : extraOptions) {
            System.out.println(optionNumber + ". " + option);
            optionNumber++;
        }

        System.out.println(optionNumber + ". Back to Main Menu");
        System.out.print("Choose an option: ");
    }

    protected boolean handleStandardChoice(int choice, int extraOptionsCount) {
        try {
            switch (choice) {
                case 1: performCreate(); return true;
                case 2: performReadAll(); return true;
                case 3: performReadById(); return true;
                case 4: performUpdate(); return true;
                case 5: performDelete(); return true;
                default:
                    if (choice == 6 + extraOptionsCount) {
                        return false;
                    }
                    return handleExtraChoice(choice);
            }
        } catch (NotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return true;
        }
    }

    protected Address createAddress(String prefix) {
        System.out.println("\n--- " + prefix + " Information ---");
        String street = promptForString(prefix + " street");
        String city = promptForString(prefix + " city");
        String postalCode = promptForString(prefix + " postal code");
        String country = promptForString(prefix + " country");

        return new Address(street, city, postalCode, country);
    }

    protected Venue createVenue() {
        Address address = createAddress("Venue");
        int capacity = promptForInt("Venue capacity");
        return new Venue(address.getStreet(), address.getCity(),
                address.getPostalCode(), address.getCountry(), capacity);
    }

    protected Address updateAddressIfNeeded(Address currentAddress, String prefix) {
        if (promptForConfirmation("Do you want to update the " + prefix.toLowerCase() + "?")) {
            return createAddress(prefix);
        }
        return currentAddress;
    }

    protected LocalDate promptForDate(String field) {
        System.out.print(field + ": ");
        return LocalDate.parse(scanner.nextLine());
    }

    protected LocalTime promptForTime(String field) {
        System.out.print(field + ": ");
        return LocalTime.parse(scanner.nextLine());
    }

    protected abstract void performCreate();
    protected abstract void performReadAll();
    protected abstract void performReadById() throws NotFoundException;
    protected abstract void performUpdate() throws NotFoundException;
    protected abstract void performDelete() throws NotFoundException;

    protected boolean handleExtraChoice(int choice) {
        System.out.println("Invalid choice.");
        return true;
    }
}