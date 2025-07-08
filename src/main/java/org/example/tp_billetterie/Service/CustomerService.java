package org.example.tp_billetterie.Service;

import org.example.tp_billetterie.Entity.Customer;
import org.example.tp_billetterie.Entity.Ticket;
import org.example.tp_billetterie.Exception.NotFoundException;
import org.example.tp_billetterie.Repository.CustomerRepository;
import java.util.List;

public class CustomerService extends BaseCrudService<Customer> {
    private final CustomerRepository customerRepository;

    public CustomerService() {
        super("Customer");
        this.customerRepository = new CustomerRepository();
    }

    @Override
    public void create(Customer customer) {
        customerRepository.save(customer);
        logSuccess("created", getEntityIdentifier(customer));
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getById(int id) throws NotFoundException {
        Customer customer = customerRepository.findById(id);
        if (customer == null) {
            throwNotFound(id);
        }
        return customer;
    }

    @Override
    public void update(int id, Customer updatedCustomer) throws NotFoundException {
        Customer existingCustomer = getById(id);
        updateCustomerFields(existingCustomer, updatedCustomer);
        logSuccess("updated", getEntityIdentifier(existingCustomer));
    }

    @Override
    public void delete(int id) throws NotFoundException {
        Customer customer = getById(id);
        customerRepository.deleteById(id);
        logSuccess("deleted", getEntityIdentifier(customer));
    }

    @Override
    protected String getEntityIdentifier(Customer customer) {
        return customer.getFirstName() + " " + customer.getLastName();
    }

    private void updateCustomerFields(Customer existing, Customer updated) {
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setAddress(updated.getAddress());
        existing.setAge(updated.getAge());
        existing.setPhoneNumber(updated.getPhoneNumber());
    }

    public List<Ticket> getCustomerTickets(int customerId) throws NotFoundException {
        Customer customer = getById(customerId);
        return customer.getTicketList();
    }
}
