package Exo3.Service;

import Exo3.DAO.ProductDao;
import Exo3.Entity.*;

import java.time.LocalDate;
import java.util.List;

public class ProductService {

    private ProductDao productDao;

    public ProductService() {
        this.productDao = new ProductDao();
    }

    public void createProductFood(String name, int price, LocalDate expiryDate) {
        validateProductData(name, price);
        validateExpiryDate(expiryDate);

        ProductFood product = new ProductFood(name, price, expiryDate);
        productDao.create(product);
    }

    public void createProductElectronic(String name, int price, int batteryDuration) {
        validateProductData(name, price);
        validateBatteryDuration(batteryDuration);

        ProductElectronic product = new ProductElectronic(name, price, batteryDuration);
        productDao.create(product);
    }

    public void createProductHousing(String name, int price, Double height, Double width) {
        validateProductData(name, price);
        validateDimensions(height, width);

        ProductHousing product = new ProductHousing(name, price, height, width);
        productDao.create(product);
    }

    public Product getProductById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("L'ID doit être un nombre positif");
        }
        return productDao.findById(id);
    }

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public void updateProduct(Product product, int id) {
        if (product == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }
        if (product.getId() == null) {
            throw new IllegalArgumentException("L'ID du produit est requis pour la mise à jour");
        }

        validateProductData(product.getName(), product.getPrice());

        if (product instanceof ProductFood) {
            ProductFood food = (ProductFood) product;
            validateExpiryDate(food.getExpiryDate());
        } else if (product instanceof ProductElectronic) {
            ProductElectronic electronic = (ProductElectronic) product;
            validateBatteryDuration(electronic.getBatteryDuration());
        } else if (product instanceof ProductHousing) {
            ProductHousing housing = (ProductHousing) product;
            validateDimensions(housing.getHeight(), housing.getWidth());
        }

        productDao.update(product, id);
    }

    public void deleteProduct(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("L'ID doit être un nombre positif");
        }

        Product product = productDao.findById(id);
        if (product == null) {
            throw new IllegalArgumentException("Aucun produit trouvé avec l'ID : " + id);
        }

        productDao.delete(id);
    }

    private void validateProductData(String name, int price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du produit est obligatoire");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Le nom du produit ne peut pas dépasser 100 caractères");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Le prix ne peut pas être négatif");
        }
        if (price > 1000000) {
            throw new IllegalArgumentException("Le prix ne peut pas dépasser 1 000 000€");
        }
    }

    private void validateExpiryDate(LocalDate expiryDate) {
        if (expiryDate == null) {
            throw new IllegalArgumentException("La date d'expiration est obligatoire");
        }
        if (expiryDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date d'expiration ne peut pas être dans le passé");
        }
        if (expiryDate.isAfter(LocalDate.now().plusYears(10))) {
            throw new IllegalArgumentException("La date d'expiration ne peut pas dépasser 10 ans");
        }
    }

    private void validateBatteryDuration(int batteryDuration) {
        if (batteryDuration <= 0) {
            throw new IllegalArgumentException("La durée de batterie doit être positive");
        }
        if (batteryDuration > 8760) {
            throw new IllegalArgumentException("La durée de batterie ne peut pas dépasser 8760 heures (1 an)");
        }
    }

    private void validateDimensions(Double height, Double width) {
        if (height == null || width == null) {
            throw new IllegalArgumentException("Les dimensions (hauteur et largeur) sont obligatoires");
        }
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Les dimensions doivent être positives");
        }
        if (height > 10000 || width > 10000) {
            throw new IllegalArgumentException("Les dimensions ne peuvent pas dépasser 10 000 cm");
        }
    }
}