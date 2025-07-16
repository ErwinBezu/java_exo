package Exo3.DAO;

import Exo3.Entity.Product;
import Exo3.Util.DatabaseManager;
import javax.persistence.EntityManager;
import java.util.List;

public class ProductDao {
    private EntityManager em;

    public ProductDao() {
        this.em = DatabaseManager.getEntityManager();
    }

    public Product create (Product product) {
        try {
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
            return product;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        }
    }

    public Product findById(Long id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAll() {
        return em.createQuery("SELECT p from Product p", Product.class).getResultList();
    }

    public Product update(Product product, long id) {
        try {
            Product productFound = findById(id);
            if(productFound != null) {
                em.getTransaction().begin();
                Product updated = em.merge(product);
                em.getTransaction().commit();
                return updated;
            }
            return null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        }
    }

    public boolean delete(Long id) {
        try {
            Product productFound = findById(id);
            if(productFound != null) {
                em.getTransaction().begin();
                em.remove(productFound);
                em.getTransaction().commit();
                return true;
            }
        return false;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }
}
