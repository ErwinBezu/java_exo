package com.magasin.Service;

import com.magasin.DAO.ClientDAO;
import com.magasin.Entity.Client;

import java.util.List;
import java.util.regex.Pattern;

public class ClientService {
    private final ClientDAO clientDAO;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    public ClientService() {
        this.clientDAO = new ClientDAO();
    }

    public Client creerClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Le client ne peux pas être null");
        }

        validerClient(client);

        if (emailExiste(client.getEmail())) {
            throw new IllegalArgumentException("Un client avec cet email existe déjà");
        }

        return clientDAO.saveOrUpdate(client);
    }

    public Client modifierClient(Client client) {
        if (client == null || client.getId() == null) {
            throw new IllegalArgumentException("Le client et son ID ne peuvent pas être null");
        }

        Client existant = clientDAO.get(client.getId());
        if (existant == null) {
            throw new IllegalArgumentException("Client introuvable avec l'ID: " + client.getId());
        }

        validerClient(client);

        if (emailExiste(client.getEmail())) {
            Client clientAvecMemeEmail = consulterClientParEmail(client.getEmail());
            if (!clientAvecMemeEmail.getId().equals(client.getId())) {
                throw new IllegalArgumentException("Un autre client avec cet email existe déjà");
            }
        }
        return clientDAO.saveOrUpdate(client);
    }

    public boolean supprimerClient(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        Client client = clientDAO.get(id);
        if (client == null) {
            throw new IllegalArgumentException("Client introuvable avec l'ID: " + id);
        }

        if (client.getVentes() != null && !client.getVentes().isEmpty()) {
            throw new IllegalStateException("Impossible de supprimer un client qui a des ventes associées");
        }

        return clientDAO.delete(id);
    }

    public Client consulterClient(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        return clientDAO.get(id);
    }

    public Client consulterClientParEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("L'email ne peut pas être vide");
        }
        return clientDAO.findByEmail(email);
    }

    public List<Client> listerTousLesClients() {
        return clientDAO.findAll();
    }

    public List<Client> rechercherParNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de recherche ne peut pas être vide");
        }
        return clientDAO.findByNom(nom.trim());
    }

    public int getNombreAchats(Long clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("L'ID du client ne peut pas être null");
        }

        Client client = clientDAO.get(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Client introuvable avec l'ID: " + clientId);
        }

        return client.getNombreAchats();
    }

    private void validerClient(Client client) {
        if (client.getNom() == null || client.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du client est obligatoire");
        }

        if (client.getEmail() == null || client.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("L'email du client est obligatoire");
        }

        String email = client.getEmail().trim().toLowerCase();
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Format d'email invalide: " + client.getEmail());
        }

        client.setEmail(email);
        client.setNom(client.getNom().trim());
    }

    public boolean emailExiste(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return clientDAO.emailExists(email);
    }
}
