package org.example.tp_billetterie.Controller;

import org.example.tp_billetterie.Entity.*;
import org.example.tp_billetterie.Exception.NotFoundException;
import org.example.tp_billetterie.Service.CustomerService;
import java.util.List;
import java.util.Scanner;

public class CustomerController extends BaseController<Customer> {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService, Scanner scanner) {
        super(scanner, "Customer");
        this.customerService = customerService;
    }

    @Override
    public void showMenu() {
        showStandardMenu();
    }

    @Override
    protected boolean executeChoice(int choice) throws Exception {
        return handleStandardChoice(choice, 0);
    }

    @Override
    protected void performCreate() {
        printCreateHeader();

        String firstName = promptForString("First name");
        String lastName = promptForString("Last name");
        Address address = createAddress("Address");
        int age = promptForInt("Age");
        String phoneNumber = promptForString("Phone number");

        Customer customer = new Customer(lastName, firstName, address, age, phoneNumber);
        customerService.create(customer);
        printSuccessMessage("created");
    }

    @Override
    protected void performReadAll() {
        printReadAllHeader();
        List<Customer> customers = customerService.getAll();
        printEntitiesList(customers);
    }

    @Override
    protected void performReadById() throws NotFoundException {
        int id = promptForId("display");
        Customer customer = customerService.getById(id);

        printReadByIdHeader();
        System.out.println(customer);

        List<Ticket> tickets = customerService.getCustomerTickets(id);
        if (!tickets.isEmpty()) {
            System.out.println("\nTickets for this customer:");
            for (Ticket ticket : tickets) {
                System.out.println("  " + ticket);
            }
        } else {
            System.out.println("\nNo tickets found for this customer.");
        }
    }

    @Override
    protected void performUpdate() throws NotFoundException {
        int id = promptForId("update");
        Customer existingCustomer = customerService.getById(id);
        System.out.println("Current customer: " + existingCustomer);

        String firstName = promptForStringWithDefault("first name", existingCustomer.getFirstName());
        String lastName = promptForStringWithDefault("last name", existingCustomer.getLastName());
        Address newAddress = updateAddressIfNeeded(existingCustomer.getAddress(), "Address");
        int age = promptForIntWithDefault("age", existingCustomer.getAge());
        String phoneNumber = promptForStringWithDefault("phone number", existingCustomer.getPhoneNumber());

        Customer updatedCustomer = new Customer(lastName, firstName, newAddress, age, phoneNumber);
        customerService.update(id, updatedCustomer);
        printSuccessMessage("updated");
    }

    @Override
    protected void performDelete() throws NotFoundException {
        int id = promptForId("delete");
        Customer customer = customerService.getById(id);

        if (!validateDeletionWithTickets(customer.getTicketList().size())) {
            return;
        }

        customerService.delete(id);
        printSuccessMessage("deleted");
    }
}
