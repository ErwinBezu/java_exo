package org.example.tp_billetterie;

import org.example.tp_billetterie.Controller.*;
import org.example.tp_billetterie.Service.*;
import java.util.Scanner;

public class TicketManagementUI {
    private final Scanner scanner;
    private final EventController eventController;
    private final CustomerController customerController;
    private final TicketController ticketController;

    public TicketManagementUI() {
        this.scanner = new Scanner(System.in);

        EventService eventService = new EventService();
        CustomerService customerService = new CustomerService();
        TicketService ticketService = new TicketService(customerService, eventService);

        this.eventController = new EventController(eventService, scanner);
        this.customerController = new CustomerController(customerService, scanner);
        this.ticketController = new TicketController(ticketService, eventService, customerService, scanner);
    }

    public void start() {
        System.out.println("=== TICKET MANAGEMENT SYSTEM - CRUD OPERATIONS ===");

        while (true) {
            displayMainMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (!handleMainMenuChoice(choice)) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Event Management (CRUD)");
        System.out.println("2. Customer Management (CRUD)");
        System.out.println("3. Ticket Management (CRUD + Reservation)");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    private boolean handleMainMenuChoice(int choice) {
        switch (choice) {
            case 1: return handleSubMenu(eventController);
            case 2: return handleSubMenu(customerController);
            case 3: return handleSubMenu(ticketController);
            case 4:
                System.out.println("Goodbye!");
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
                return true;
        }
    }

    private boolean handleSubMenu(MenuController controller) {
        while (true) {
            controller.showMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (!controller.handleChoice(choice)) {
                    return true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static void main(String[] args) {
        TicketManagementUI ui = new TicketManagementUI();
        ui.start();
    }
}

